package com.scandrug.scandrug.presentation.home.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scandrug.scandrug.BuildConfig
import com.scandrug.scandrug.R
import com.scandrug.scandrug.base.BaseApplication
import com.scandrug.scandrug.data.local.AppPreferences
import com.scandrug.scandrug.data.local.CacheInMemory
import com.scandrug.scandrug.data.repository.MainRepoImp
import com.scandrug.scandrug.databinding.FragmentCompletedRequestBinding
import com.scandrug.scandrug.domain.usecases.MainUseCases
import com.scandrug.scandrug.presentation.home.fragments.adapters.CompletedDrugAdapter
import com.scandrug.scandrug.presentation.home.viewmodel.CompletedViewModel
import com.scandrug.scandrug.presentation.home.viewmodelfactories.CompletedViewModelFactory
import java.util.*

class CompletedRequestFragment : Fragment() {
    private lateinit var completedRequestBinding: FragmentCompletedRequestBinding
    private lateinit var navController: NavController
    private lateinit var completedViewModel: CompletedViewModel
    private lateinit var useCases: MainUseCases
    private lateinit var progressDialog: Dialog
    private lateinit var toolbar: Toolbar
    private var completedDrugAdapter: CompletedDrugAdapter? = null
    private var sharedPreferences =
        BaseApplication.instance.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
    private lateinit var appPreferences: AppPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        completedRequestBinding = FragmentCompletedRequestBinding.inflate(inflater)
//        toolbar=requireActivity().findViewById(R.id.activity_main_toolbar)
//        toolbar.subtitle="mnhj"
//        toolbar.title="mnhj"
//        toolbar.subtitle="mnhj"
//        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "zknmn,n"
//        requireActivity().title = "Your title"
        setHasOptionsMenu(true)
        setDialogConfiguration()
        navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        val repository = MainRepoImp()
        useCases = MainUseCases(repository)
        completedViewModel = ViewModelProvider(this, CompletedViewModelFactory(useCases))
            .get(CompletedViewModel::class.java)
        observer()
        initialRecycleresData()
        appPreferences = AppPreferences(sharedPreferences)
        val activity = requireActivity()
        completedViewModel.getProcessingOrders()

        return completedRequestBinding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()

    }

    private fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    private fun observer() {
        completedViewModel.isError.observe(viewLifecycleOwner, {
            Toast.makeText(
                context,
                context?.getString(R.string.check_your_connection),
                Toast.LENGTH_SHORT
            ).show()
        })

        completedViewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }

        })
        completedViewModel.listOfDrugs.observe(this, {
            completedDrugAdapter?.addDrugs(it)
        })
    }

    private fun setDialogConfiguration() {
        progressDialog = Dialog(requireContext())
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setContentView(R.layout.custom_progress_dialog)
        Objects.requireNonNull(progressDialog.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    private fun initialRecycleresData() {
        val linearLayoutManager = LinearLayoutManager(activity)
        completedRequestBinding.recyclerView.layoutManager = linearLayoutManager
        completedDrugAdapter = CompletedDrugAdapter(
            onItemClicked = { completedObject, position ->
                completedViewModel.setDrugDetailsModel(completedObject)
                CacheInMemory.clearProfileData()
                CacheInMemory.setDrugDetailsModel(completedObject)
                if (navController.currentDestination!!.id == R.id.completedRequestFragment) {
                    findNavController().navigate(CompletedRequestFragmentDirections.actionCompletedRequestFragmentToCompletedDrugDetailsFragment())

                }

            })
        completedRequestBinding.recyclerView.adapter = completedDrugAdapter
    }
}
