package com.scandrug.scandrug.presentation.home.delivery.fragments
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
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
import com.scandrug.scandrug.databinding.FragmentDeliveryOrdersBinding
import com.scandrug.scandrug.domain.usecases.MainUseCases
import com.scandrug.scandrug.presentation.home.delivery.viewmodel.DeliveryViewModel
import com.scandrug.scandrug.presentation.home.delivery.viewmodelfactories.DeliveryViewModelFactory
import com.scandrug.scandrug.presentation.home.fragments.CompletedRequestFragmentDirections
import com.scandrug.scandrug.presentation.home.fragments.adapters.CompletedDrugAdapter
import com.scandrug.scandrug.presentation.home.viewmodel.CompletedViewModel
import com.scandrug.scandrug.presentation.home.viewmodelfactories.CompletedViewModelFactory
import java.util.*

class DeliveryOrdersFragment : Fragment() {
    private lateinit var deliveryOrdersBinding: FragmentDeliveryOrdersBinding
    private lateinit var navController: NavController
    private lateinit var deliveryViewModel: DeliveryViewModel
    private lateinit var useCases: MainUseCases
    private lateinit var progressDialog: Dialog
    private lateinit var toolbar: Toolbar
    private var deliveryDrugAdapter: DeliveryDrugAdapter? = null
    private var sharedPreferences =
        BaseApplication.instance.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
    private lateinit var appPreferences: AppPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        deliveryOrdersBinding = FragmentDeliveryOrdersBinding.inflate(inflater)
        setHasOptionsMenu(true)
        setDialogConfiguration()
        navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        val repository = MainRepoImp()
        useCases = MainUseCases(repository)
        deliveryViewModel = ViewModelProvider(this, DeliveryViewModelFactory(useCases))
            .get(DeliveryViewModel::class.java)
        observer()
        initialRecycleresData()
        appPreferences = AppPreferences(sharedPreferences)
        requireActivity()
        deliveryViewModel.getProcessingOrders()
        return deliveryOrdersBinding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()

    }

    private fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    private fun observer() {
        deliveryViewModel.isError.observe(viewLifecycleOwner, {
            Toast.makeText(
                context,
                context?.getString(R.string.check_your_connection),
                Toast.LENGTH_SHORT
            ).show()
        })

        deliveryViewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }

        })
        deliveryViewModel.listOfDrugs.observe(this, {
            deliveryDrugAdapter?.addDrugs(it)
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
        deliveryOrdersBinding.recyclerView.layoutManager = linearLayoutManager
        deliveryDrugAdapter = DeliveryDrugAdapter(
            onItemClicked = { completedObject, position ->
                deliveryViewModel.setDrugDetailsModel(completedObject)
                CacheInMemory.clearProfileData()
                CacheInMemory.setDrugDetailsModel(completedObject)
                if (navController.currentDestination!!.id == R.id.deliveryOrdersFragment) {
                    findNavController().navigate(DeliveryOrdersFragmentDirections.actionDeliveryOrdersFragmentToFragmentDeliveryDetails2())

                }

            })
        deliveryOrdersBinding.recyclerView.adapter = deliveryDrugAdapter
    }
}