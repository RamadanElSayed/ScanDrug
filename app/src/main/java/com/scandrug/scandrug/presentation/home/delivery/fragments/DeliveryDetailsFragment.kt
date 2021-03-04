package com.scandrug.scandrug.presentation.home.delivery.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.scandrug.scandrug.R
import kotlinx.android.synthetic.main.fragment_delivery_details.*


class DeliveryDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusAdapter()
    }


    fun setStatusAdapter(){
        val arrayList = ArrayList<String>()
        arrayList.add("Shipping")
        arrayList.add("Pay Order")
        arrayList.add("Packing")
        arrayList.add("Delivery")

        val arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line, arrayList)

        ac_status.setAdapter(arrayAdapter)
        ac_status.threshold = 1
        ac_status.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }

        })



    }
}