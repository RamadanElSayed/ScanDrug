package com.scandrug.scandrug.presentation.home.delivery.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputLayout
import com.scandrug.scandrug.BuildConfig
import com.scandrug.scandrug.R
import com.scandrug.scandrug.base.BaseApplication
import com.scandrug.scandrug.data.local.AppPreferences
import com.scandrug.scandrug.data.local.CacheInMemory
import com.scandrug.scandrug.data.remotemodel.DrugDetailsModel
import com.scandrug.scandrug.data.repository.MainRepoImp
import com.scandrug.scandrug.databinding.FragmentDeliveryDetailsBinding
import com.scandrug.scandrug.domain.usecases.MainUseCases
import com.scandrug.scandrug.presentation.home.delivery.viewmodel.DeliveryViewModel
import com.scandrug.scandrug.presentation.home.delivery.viewmodelfactories.DeliveryViewModelFactory
import kotlinx.android.synthetic.main.fragment_delivery_details.*
import java.util.*
import kotlin.collections.ArrayList


class DeliveryDetailsFragment : Fragment() ,DeliveryOnClickView{
    private lateinit var deliveryDetailsBinding: FragmentDeliveryDetailsBinding
    private lateinit var navController: NavController
    private lateinit var deliveryViewModel: DeliveryViewModel
    private lateinit var useCases: MainUseCases
    private lateinit var progressDialog: Dialog
    private lateinit var drugDetailsModel: DrugDetailsModel
    private var drugTypeSelectedId = -1
    private var drugTypeName: String? = null
    private var sharedPreferences =
        BaseApplication.instance.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
    private lateinit var appPreferences: AppPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        deliveryDetailsBinding = FragmentDeliveryDetailsBinding.inflate(inflater)
        deliveryDetailsBinding.deliveryOnClickView = this
        setHasOptionsMenu(true)
        setDialogConfiguration()
        navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        val repository = MainRepoImp()
        useCases = MainUseCases(repository)
        deliveryViewModel = ViewModelProvider(this, DeliveryViewModelFactory(useCases))
            .get(DeliveryViewModel::class.java)
        observer()
        appPreferences = AppPreferences(sharedPreferences)
        setHasOptionsMenu(true)
        drugDetailsModel = DrugDetailsModel()
        drugDetailsModel = CacheInMemory.getDrugDetailsModel()!!
        setDataView()
        return deliveryDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusAdapter()
    }


    private fun setStatusAdapter() {
        val arrayList = ArrayList<String>()
        arrayList.add("Shipped")
        arrayList.add("In Transit")
        arrayList.add("Delivered")
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayList)
        ac_status.setAdapter(arrayAdapter)
        ac_status.setOnItemClickListener { parent, view, position, id ->
            ac_status.error = null
            drugTypeName = arrayList[position]
            drugTypeSelectedId = position
        }


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
        deliveryViewModel.navigateToMain.observe(this, {
            navController.popBackStack(R.id.fragmentDeliveryDetails, true)

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

    private fun setDataView()
    {
        deliveryDetailsBinding.tvProductName.text=drugDetailsModel.drugName
        deliveryDetailsBinding.tvPrice.text=drugDetailsModel.drugPrice
        deliveryDetailsBinding.tvCompanyName.text=drugDetailsModel.tabletNumber


        when (drugDetailsModel.orderStatus) {
            1 -> {
                deliveryDetailsBinding.divider.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray_selected
                    )
                )
                deliveryDetailsBinding.divider2.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray_selected
                    )
                )
                deliveryDetailsBinding.imageView2.setBackgroundResource(R.drawable.ic_baseline_check_circle_24)
                deliveryDetailsBinding.imageView3.setBackgroundResource(R.drawable.ic_right)
                deliveryDetailsBinding.imageView4.setBackgroundResource(R.drawable.ic_right)



            }
            2 -> {
                deliveryDetailsBinding.divider.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.color_primary
                    )
                )
                deliveryDetailsBinding.divider2.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray_selected
                    )
                )

                deliveryDetailsBinding.imageView2.setBackgroundResource(R.drawable.ic_baseline_check_circle_24)
                deliveryDetailsBinding.imageView3.setBackgroundResource(R.drawable.ic_baseline_check_circle_24)
                deliveryDetailsBinding.imageView4.setBackgroundResource(R.drawable.ic_right)
            }
            3 -> {
                deliveryDetailsBinding.divider.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.color_primary
                    )
                )
                deliveryDetailsBinding.divider2.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.color_primary
                    )
                )
                deliveryDetailsBinding.imageView2.setBackgroundResource(R.drawable.ic_baseline_check_circle_24)
                deliveryDetailsBinding.imageView3.setBackgroundResource(R.drawable.ic_baseline_check_circle_24)
                deliveryDetailsBinding.imageView4.setBackgroundResource(R.drawable.ic_baseline_check_circle_24)
            }
        }
    }

    override fun confirmClicked() {
        setInputNumberLayout(deliveryDetailsBinding.tlStatus,"Required")
        if(drugTypeSelectedId>-1) {
            var status = 0
            when (drugTypeSelectedId) {
                0 -> status = 1
                1 -> status = 2
                2 -> status = 3
            }
            deliveryViewModel.saveUserToDatabase(drugDetailsModel, status)
        }

    }

    private fun setInputNumberLayout(
        inputLayout: TextInputLayout,
        errorMessage: String
    ) {
        if (drugTypeSelectedId==-1) {
            inputLayout.isErrorEnabled = true
            inputLayout.error = errorMessage
        } else {
            inputLayout.isErrorEnabled = false
            inputLayout.error = ""
        }
    }
}