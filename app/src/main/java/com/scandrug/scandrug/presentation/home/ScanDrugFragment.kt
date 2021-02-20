package com.scandrug.scandrug.presentation.home
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.scandrug.scandrug.R
import kotlinx.android.synthetic.main.fragment_scan_drug.*

class ScanDrugFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_drug, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scanClicked()
    }

    fun scanClicked(){
        btn_scan.setOnClickListener {
            startScan()
        }

    }


    fun startScan(){
        val intentIntegrator : IntentIntegrator = IntentIntegrator(requireActivity()).apply {
            setOrientationLocked(true)
            setBeepEnabled(true)
            setBarcodeImageEnabled(true)
            setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            setPrompt("Scan Barcode")
        }
        intentIntegrator.initiateScan()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentIntegrator :IntentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if (intentIntegrator.contents != null){
            Toast.makeText(context,intentIntegrator.contents,Toast.LENGTH_SHORT).show()
        }
    }
}