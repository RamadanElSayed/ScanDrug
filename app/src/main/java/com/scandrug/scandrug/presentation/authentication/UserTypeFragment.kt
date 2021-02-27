package com.scandrug.scandrug.presentation.authentication
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.scandrug.scandrug.BuildConfig
import com.scandrug.scandrug.R
import com.scandrug.scandrug.base.BaseApplication
import com.scandrug.scandrug.data.local.AppPreferences
import com.scandrug.scandrug.databinding.FragmentUserTypeBinding
import com.scandrug.scandrug.presentation.authentication.interfaces.UserTypeView

class UserTypeFragment : Fragment() , UserTypeView {

    private lateinit var userTypeBinding: FragmentUserTypeBinding
    private lateinit var navController: NavController
    private var sharedPreferences =
            BaseApplication.instance.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
    private lateinit var appPreferences: AppPreferences
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        userTypeBinding = FragmentUserTypeBinding.inflate(inflater)
        userTypeBinding.userTypeView = this
        navController =
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        appPreferences = AppPreferences(sharedPreferences)

        return userTypeBinding.root
    }

    override fun onCompanyLoginClicked() {
        if (navController.currentDestination!!.id == R.id.userTypeFragment)
            findNavController().navigate(UserTypeFragmentDirections.actionUserTypeFragmentToLoginFragment())
    }

    override fun onDeliveryLoginClicked() {
        appPreferences.setAccessToken("delivery")
        if (navController.currentDestination!!.id == R.id.userTypeFragment)
            findNavController().navigate(UserTypeFragmentDirections.actionUserTypeFragmentToDeliveryActivity())
    }

}