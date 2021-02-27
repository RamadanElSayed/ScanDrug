package com.scandrug.scandrug.presentation.home.fragments
import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.google.zxing.Result
import com.scandrug.scandrug.BuildConfig
import com.scandrug.scandrug.R
import com.scandrug.scandrug.base.BaseApplication
import com.scandrug.scandrug.data.local.AppPreferences
import com.scandrug.scandrug.data.remotemodel.DrugDetailsModel
import com.scandrug.scandrug.data.repository.MainRepoImp
import com.scandrug.scandrug.databinding.FragmentScanDrugBinding
import com.scandrug.scandrug.domain.usecases.MainUseCases
import com.scandrug.scandrug.presentation.authentication.LoginFragmentDirections
import com.scandrug.scandrug.presentation.home.interfaces.ScanOnClickView
import com.scandrug.scandrug.presentation.home.viewmodel.ScanViewModel
import com.scandrug.scandrug.presentation.home.viewmodelfactories.ScanViewModelFactory
import com.scandrug.scandrug.utils.PermissionsUtils


class ScanDrugFragment : Fragment(),ScanOnClickView {
    private lateinit var codeScanner: CodeScanner
    private lateinit var fragmentScanDrugBinding: FragmentScanDrugBinding
    private lateinit var navController: NavController
    private lateinit var scanViewModel: ScanViewModel
    private lateinit var useCases: MainUseCases
    private lateinit var email: String
    private lateinit var  progressDialog: Dialog
    private var sharedPreferences =
        BaseApplication.instance.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
    private lateinit var appPreferences: AppPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentScanDrugBinding = FragmentScanDrugBinding.inflate(inflater)
        fragmentScanDrugBinding.scanView = this
        navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        val repository = MainRepoImp()
        useCases = MainUseCases(repository)
        scanViewModel = ViewModelProvider(requireActivity(), ScanViewModelFactory(useCases))
            .get(ScanViewModel::class.java)
        observer()
        appPreferences = AppPreferences(sharedPreferences)
        val activity = requireActivity()
        defaultScanClicked()
        return fragmentScanDrugBinding.root
    }


    override fun scanClicked() {
        fragmentScanDrugBinding.btnScan.visibility=View.GONE
        fragmentScanDrugBinding.tvNotes.visibility=View.GONE
        fragmentScanDrugBinding.imageView.visibility=View.GONE
        fragmentScanDrugBinding.scannerView.visibility=View.VISIBLE
        checkPermission()
    }


    override fun defaultScanClicked() {
        fragmentScanDrugBinding.btnScan.visibility=View.VISIBLE
        fragmentScanDrugBinding.tvNotes.visibility=View.VISIBLE
        fragmentScanDrugBinding.imageView.visibility=View.VISIBLE
        fragmentScanDrugBinding.scannerView.visibility=View.GONE


    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setDataAfterPermission()
            } else {
                Toast.makeText(
                    context,
                    resources.getString(R.string.permission_denied),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    private fun observer() {
        scanViewModel.isError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(
                context,
                context?.getString(R.string.check_your_connection),
                Toast.LENGTH_SHORT
            ).show()
        })

        scanViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }

        })

    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val perms = arrayOf(Manifest.permission.CAMERA)
            if (!PermissionsUtils.hasPermissions(context, *perms)) {
                requestPermissions(perms, 1)
                return
            }
            setDataAfterPermission()
        }
    }
    private fun setDataAfterPermission() {
        codeScanner = CodeScanner(requireActivity(), fragmentScanDrugBinding.scannerView)
        codeScanner.formats = CodeScanner.ALL_FORMATS

        val thread = Thread {
            try {
                Thread.sleep(700)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            codeScanner.startPreview()
        }
        thread.start()
        codeScanner.decodeCallback = DecodeCallback { result: Result ->
            requireActivity().runOnUiThread {
              //  codeScanner.startPreview()
                val barCodeValue = result.text
                if (!barCodeValue.isNullOrBlank()) {
                    val barCodeValueArray = barCodeValue.split("/").toTypedArray()
                    val drugDetailsModel = DrugDetailsModel()
                    if (barCodeValueArray.isNotEmpty() && barCodeValueArray[0].isNotBlank())
                        drugDetailsModel.drugName = barCodeValueArray[0]
                    if (barCodeValueArray.size > 1 && barCodeValueArray[1].isNotBlank())
                        drugDetailsModel.tabletNumber = barCodeValueArray[1]
                    if (barCodeValueArray.size > 2 && barCodeValueArray[2].isNotBlank())
                        drugDetailsModel.tabletWeight = barCodeValueArray[2]
                    if (barCodeValueArray.size > 3 && barCodeValueArray[3].isNotBlank())
                        drugDetailsModel.drugPrice = barCodeValueArray[3]
                    if (barCodeValueArray.size > 4 && barCodeValueArray[4].isNotBlank())
                        drugDetailsModel.drugDesc = barCodeValueArray[4]
                    if (barCodeValueArray.size > 5 && barCodeValueArray[5].isNotBlank())
                        drugDetailsModel.drugRelieve = barCodeValueArray[5]
                    if (barCodeValueArray.size > 6 && barCodeValueArray[6].isNotBlank())
                        drugDetailsModel.drugEffect = barCodeValueArray[6]
                    if (barCodeValueArray.size > 7 && barCodeValueArray[7].isNotBlank())
                        drugDetailsModel.drugDosageUsed = barCodeValueArray[7]
                    scanViewModel.setDrugDetailsModel(drugDetailsModel)
                    showToast("barcode$barCodeValue")
                   if (navController.currentDestination!!.id == R.id.homeFragment)
                        findNavController().navigate(ScanDrugFragmentDirections.actionHomeFragmentToScanDetailsFragment())
                }
            }
        }
    }
//    override fun onStop() {
//        super.onStop()
//        codeScanner.releaseResources()
//    }
}