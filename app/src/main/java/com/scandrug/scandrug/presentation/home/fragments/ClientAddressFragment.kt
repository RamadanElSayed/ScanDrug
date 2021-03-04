package com.scandrug.scandrug.presentation.home.fragments
import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputLayout
import com.scandrug.scandrug.R
import com.scandrug.scandrug.data.remotemodel.DrugDetailsModel
import com.scandrug.scandrug.data.remotemodel.UserData
import com.scandrug.scandrug.data.repository.MainRepoImp
import com.scandrug.scandrug.databinding.FragmentAddressBinding
import com.scandrug.scandrug.domain.usecases.MainUseCases
import com.scandrug.scandrug.presentation.home.interfaces.AddressScanOnClickView
import com.scandrug.scandrug.presentation.home.viewmodel.ScanViewModel
import com.scandrug.scandrug.presentation.home.viewmodelfactories.ScanViewModelFactory
import com.scandrug.scandrug.utils.TimeUtil
import java.util.*

class ClientAddressFragment : Fragment() ,AddressScanOnClickView{
    private lateinit var  progressDialog: Dialog
    private lateinit var fragmentAddressBinding: FragmentAddressBinding
    private lateinit var navController: NavController
    private lateinit var scanViewModel: ScanViewModel
    private lateinit var useCases: MainUseCases
    private lateinit var drugDetailsModel: DrugDetailsModel
    private lateinit var userData: UserData
    private lateinit var city: String
    private lateinit var street: String
    private lateinit var apartment: String
    private var isValid = true
    private lateinit var calendar: Calendar
    private var year:Int=0
    private var month:Int=0
    private var day:Int=0
    private var timeMilli:Long=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAddressBinding = FragmentAddressBinding.inflate(inflater)
        fragmentAddressBinding.addressView = this

        setDialogConfiguration()
        navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        val repository = MainRepoImp()
        useCases = MainUseCases(repository)
        scanViewModel = ViewModelProvider(requireActivity(), ScanViewModelFactory(useCases))
            .get(ScanViewModel::class.java)
        scanViewModel.getUserData()
        observer()
        return fragmentAddressBinding.root
    }

    override fun onAddressScanClicked() {
        if (checkValidateAllFields()) {
            calendar= Calendar.getInstance()
            year = calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH)
            day = calendar.get(Calendar.DAY_OF_MONTH)
            drugDetailsModel=scanViewModel.getDrugDetailsModel()
            drugDetailsModel.userName= userData.firstName
            drugDetailsModel.clientStreet=street
            drugDetailsModel.clientApartment=apartment
            drugDetailsModel.clientCity=city
            drugDetailsModel.orderStatus=1
            timeMilli = calendar.timeInMillis
            drugDetailsModel.dateOrder=TimeUtil.getDayMonthYear(timeMilli)
            drugDetailsModel.orderId=UUID.randomUUID().toString()
            scanViewModel.saveUserToDatabase(drugDetailsModel)
        }

    }
    private fun setInputLayoutError(
        inputLayout: TextInputLayout, errorMeString: String
    ) {
        inputLayout.isErrorEnabled = true
        inputLayout.error = errorMeString
    }

    private fun setInputLayoutSuccess(
        inputLayout: TextInputLayout
    ) {
        inputLayout.isErrorEnabled = false
        inputLayout.error = ""
    }

    @SuppressLint("HardwareIds")
    private fun checkValidateAllFields(): Boolean {
        isValid = true
        city = fragmentAddressBinding.teCity.text.toString()
        street = fragmentAddressBinding.teStreet.text.toString()
        apartment = fragmentAddressBinding.teApartmentNumber.text.toString()
        if (scanViewModel.validateEmptyFiled(city) )
            setInputLayoutSuccess(fragmentAddressBinding.tlCity)
        else {
            isValid = false
            setInputLayoutError(
                fragmentAddressBinding.tlCity,
                getString(R.string.required_field)
            )
        }

        if (scanViewModel.validateEmptyFiled(apartment) )
            setInputLayoutSuccess(fragmentAddressBinding.tlApartmentNumber)
        else {
            isValid = false
            setInputLayoutError(
                fragmentAddressBinding.tlApartmentNumber,
                getString(R.string.required_field)
            )
        }

        if (scanViewModel.validateEmptyFiled(street) )
            setInputLayoutSuccess(fragmentAddressBinding.tlStreet)
        else {
            isValid = false
            setInputLayoutError(
                fragmentAddressBinding.tlStreet,
                getString(R.string.required_field)
            )
        }

        return isValid
    }
    private fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    private fun observer() {
        scanViewModel.isError.observe(viewLifecycleOwner, {
            Toast.makeText(
                context,
                context?.getString(R.string.check_your_connection),
                Toast.LENGTH_SHORT
            ).show()

        })
        scanViewModel.userData.observe(viewLifecycleOwner, {
            userData= UserData()
            userData=it
        })
        scanViewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }

        })
        fragmentAddressBinding.teCity.doAfterTextChanged { s ->
            if (s.toString().isNotEmpty()) {
                if (!scanViewModel.validateEmptyFiled(s.toString())) {
                    setInputLayoutError(
                        fragmentAddressBinding.tlCity,
                        getString(R.string.required_field)
                    )
                } else {
                    setInputLayoutSuccess(fragmentAddressBinding.tlCity)
                }
            }
        }

        fragmentAddressBinding.teStreet.doAfterTextChanged { s ->
            if (s.toString().isNotEmpty()) {
                if (!scanViewModel.validateEmptyFiled(s.toString())) {
                    setInputLayoutError(
                        fragmentAddressBinding.tlStreet,
                        getString(R.string.required_field)
                    )
                } else {
                    setInputLayoutSuccess(fragmentAddressBinding.tlStreet)
                }
            }
        }

        fragmentAddressBinding.teApartmentNumber.doAfterTextChanged { s ->
            if (s.toString().isNotEmpty()) {
                if (!scanViewModel.validateEmptyFiled(s.toString())) {
                    setInputLayoutError(
                        fragmentAddressBinding.tlApartmentNumber,
                        getString(R.string.required_field)
                    )
                } else {
                    setInputLayoutSuccess(fragmentAddressBinding.tlApartmentNumber)
                }
            }
        }

        scanViewModel.navigateToMain.observe(viewLifecycleOwner, {
            if (it) {
                showToast(getString(R.string.drug_success_message))
                Thread.sleep(500)
                if (navController.currentDestination?.id == R.id.clientAddressFragment) {
                    navController.popBackStack(R.id.clientAddressFragment, true)
                    navController.popBackStack(R.id.scan_details_Fragment, true)
                    navController.popBackStack(R.id.homeFragment, true)
                    navController.navigate(R.id.homeFragment)

                }
            }
        })

    }
    private fun setDialogConfiguration() {
        progressDialog = Dialog(requireContext())
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setContentView(R.layout.custom_progress_dialog)
        Objects.requireNonNull(progressDialog.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}