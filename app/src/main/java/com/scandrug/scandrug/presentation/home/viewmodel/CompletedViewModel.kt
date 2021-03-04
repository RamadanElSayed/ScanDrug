package com.scandrug.scandrug.presentation.home.viewmodel
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.scandrug.scandrug.BuildConfig
import com.scandrug.scandrug.base.BaseApplication
import com.scandrug.scandrug.data.local.AppPreferences
import com.scandrug.scandrug.data.remotemodel.DrugDetailsModel
import com.scandrug.scandrug.data.remotemodel.RegistrationModel
import com.scandrug.scandrug.data.resources.Resource
import com.scandrug.scandrug.domain.usecases.AuthUseCases
import com.scandrug.scandrug.domain.usecases.MainUseCases
import com.scandrug.scandrug.utils.SingleLiveEvent
import com.scandrug.scandrug.utils.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CompletedViewModel(private val mainUseCases: MainUseCases) : ViewModel() {

    companion object {
        const val TAG = "LoginViewModel"
    }
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val isError: SingleLiveEvent<String> = SingleLiveEvent()
    val loading: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val baseUrlResponse: SingleLiveEvent<String> = SingleLiveEvent()
    private var sharedPreferences =
        BaseApplication.instance.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
    private var appPreferences: AppPreferences = AppPreferences(sharedPreferences)
    private var token: String = appPreferences.getAccessToken().toString()
    private lateinit var drugDetailsModel:DrugDetailsModel
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseFirestore.getInstance()
    val listOfDrugs: SingleLiveEvent<List<DrugDetailsModel>> = SingleLiveEvent()

    private lateinit var  drugDetailsItemList: MutableList<DrugDetailsModel>
    val navigateToMain: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val userId = auth.currentUser!!.uid
    fun setDrugDetailsModel(drugDetailsModel: DrugDetailsModel) {
        this.drugDetailsModel = drugDetailsModel
    }

    fun getDrugDetailsModel(): DrugDetailsModel {
        return this.drugDetailsModel
    }

    fun validateEmptyFiled(message: String): Boolean {
        return mainUseCases.validateEmptyFiled(message)
    }

    fun getProcessingOrders() {
        loading.value = true
        database.collection("Orders").
        whereEqualTo("userId",userId)
            .whereIn("orderStatus", listOf(1,2,3)).
       get().addOnSuccessListener {
                drugDetailsItemList= mutableListOf()
                drugDetailsItemList.clear()
            for (document in it) {
                drugDetailsItemList.add(document.toObject(DrugDetailsModel::class.java))
            }
                listOfDrugs.value=drugDetailsItemList
                loading.value = false

            }

    }
}