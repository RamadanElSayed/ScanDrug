package com.scandrug.scandrug.presentation.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scandrug.scandrug.R
import com.scandrug.scandrug.presentation.authentication.interfaces.UserTypeView

class UserTypeFragment : Fragment() , UserTypeView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_type, container, false)
    }

    override fun onCompanyLoginClicked() {
        TODO("Not yet implemented")
    }

    override fun onDeliveryLoginClicked() {
        TODO("Not yet implemented")
    }

}