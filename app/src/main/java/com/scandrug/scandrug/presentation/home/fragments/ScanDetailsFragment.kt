package com.scandrug.scandrug.presentation.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.scandrug.scandrug.R
import com.scandrug.scandrug.data.local.AppPreferences
import com.scandrug.scandrug.data.remotemodel.DrugDetailsModel
import com.scandrug.scandrug.data.repository.MainRepoImp
import com.scandrug.scandrug.databinding.FragmentScanDetailsBinding
import com.scandrug.scandrug.databinding.FragmentScanDrugBinding
import com.scandrug.scandrug.domain.usecases.MainUseCases
import com.scandrug.scandrug.presentation.home.interfaces.DetailsScanOnClickView
import com.scandrug.scandrug.presentation.home.viewmodel.ScanViewModel
import com.scandrug.scandrug.presentation.home.viewmodelfactories.ScanViewModelFactory

class ScanDetailsFragment : Fragment() ,DetailsScanOnClickView{
    private lateinit var detailsBinding: FragmentScanDetailsBinding
    private lateinit var navController: NavController
    private lateinit var scanViewModel: ScanViewModel
    private lateinit var useCases: MainUseCases
    private lateinit var drugDetailsModel:DrugDetailsModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsBinding = FragmentScanDetailsBinding.inflate(inflater)
        detailsBinding.detailsScan = this
        navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        val repository = MainRepoImp()
        useCases = MainUseCases(repository)
        scanViewModel = ViewModelProvider(requireActivity(), ScanViewModelFactory(useCases))
            .get(ScanViewModel::class.java)
        drugDetailsModel=DrugDetailsModel()
        drugDetailsModel=scanViewModel.getDrugDetailsModel()
        setDataToViews()
        return detailsBinding.root
    }

    override fun buyClicked() {
        if (navController.currentDestination!!.id == R.id.scan_details_Fragment)
            findNavController().navigate(ScanDetailsFragmentDirections.actionScanDetailsFragmentToClientAddressFragment())

    }

    override fun setDataToViews() {
        detailsBinding.materialButton.text = drugDetailsModel.drugName
        detailsBinding.tvTable.text=drugDetailsModel.tabletNumber
        detailsBinding.tvMg.text=drugDetailsModel.tabletWeight
        detailsBinding.tvPrice.text=drugDetailsModel.drugPrice
        detailsBinding.tvDesc.text=drugDetailsModel.drugDesc
        detailsBinding.tvRelieve.text=drugDetailsModel.drugRelieve
        detailsBinding.tvSideEffect.text=drugDetailsModel.drugEffect
        detailsBinding.tvDosage.text=drugDetailsModel.drugDosageUsed
    }

}