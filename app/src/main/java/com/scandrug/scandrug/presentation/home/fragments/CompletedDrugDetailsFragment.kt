package com.scandrug.scandrug.presentation.home.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.scandrug.scandrug.R
import com.scandrug.scandrug.data.local.CacheInMemory
import com.scandrug.scandrug.data.remotemodel.DrugDetailsModel
import com.scandrug.scandrug.data.repository.MainRepoImp
import com.scandrug.scandrug.databinding.FragmentCompletedDetailsBinding
import com.scandrug.scandrug.databinding.FragmentScanDetailsBinding
import com.scandrug.scandrug.domain.usecases.MainUseCases
import com.scandrug.scandrug.presentation.home.viewmodel.CompletedViewModel
import com.scandrug.scandrug.presentation.home.viewmodel.ScanViewModel
import com.scandrug.scandrug.presentation.home.viewmodelfactories.CompletedViewModelFactory
import com.scandrug.scandrug.presentation.home.viewmodelfactories.ScanViewModelFactory

class CompletedDrugDetailsFragment : Fragment() {
    private lateinit var fragmentCompletedDetailsBinding: FragmentCompletedDetailsBinding
    private lateinit var navController: NavController
    private lateinit var drugDetailsModel: DrugDetailsModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCompletedDetailsBinding = FragmentCompletedDetailsBinding.inflate(inflater)
        navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        setHasOptionsMenu(true)

        drugDetailsModel = DrugDetailsModel()
        drugDetailsModel = CacheInMemory.getDrugDetailsModel()!!
        setDataToViews()
        return fragmentCompletedDetailsBinding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()

    }

     fun setDataToViews() {
        fragmentCompletedDetailsBinding.tvTable.text = drugDetailsModel.tabletNumber
        fragmentCompletedDetailsBinding.tvMg.text = drugDetailsModel.tabletWeight
        fragmentCompletedDetailsBinding.tvPrice.text = drugDetailsModel.drugPrice
        fragmentCompletedDetailsBinding.tvDesc.text = drugDetailsModel.drugDesc
        fragmentCompletedDetailsBinding.tvRelieve.text = drugDetailsModel.drugRelieve
        fragmentCompletedDetailsBinding.tvSideEffect.text = drugDetailsModel.drugEffect
        fragmentCompletedDetailsBinding.tvDosage.text = drugDetailsModel.drugDosageUsed
    }
}