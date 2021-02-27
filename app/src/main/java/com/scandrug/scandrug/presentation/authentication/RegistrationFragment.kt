package com.scandrug.scandrug.presentation.authentication
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputLayout
import com.scandrug.scandrug.BuildConfig
import com.scandrug.scandrug.R
import com.scandrug.scandrug.base.BaseApplication
import com.scandrug.scandrug.data.local.AppPreferences
import com.scandrug.scandrug.data.remotemodel.RegistrationModel
import com.scandrug.scandrug.data.repository.AuthRepoImp
import com.scandrug.scandrug.data.resources.Resource
import com.scandrug.scandrug.databinding.FragmentRegistrationBinding
import com.scandrug.scandrug.domain.usecases.AuthUseCases
import com.scandrug.scandrug.presentation.authentication.interfaces.RegistrationOnClickView
import com.scandrug.scandrug.presentation.authentication.viewmodel.RegistrationViewModel
import com.scandrug.scandrug.presentation.authentication.viewmodelfactories.RegistrationViewModelFactory
import java.util.*

class RegistrationFragment : Fragment(), RegistrationOnClickView {
    private lateinit var fragmentRegistrationBinding: FragmentRegistrationBinding
    private lateinit var navController: NavController
    private lateinit var registrationViewModel: RegistrationViewModel
    private lateinit var useCases: AuthUseCases
    private lateinit var userName: String
    private lateinit var phoneNumber: String
    private lateinit var emailTxt: String
    private lateinit var  progressDialog: Dialog
    private var password: String = ""
    private var confirmPassword: String = ""
    private lateinit var registrationModel: RegistrationModel
    private var isValid = true

    private var sharedPreferences =
        BaseApplication.instance.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
    private lateinit var appPreferences: AppPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDialogConfiguration()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRegistrationBinding = FragmentRegistrationBinding.inflate(inflater)
        fragmentRegistrationBinding.registerView = this
        navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        val repository = AuthRepoImp()
        registrationModel = RegistrationModel()
        useCases = AuthUseCases(repository)
        registrationViewModel =
            ViewModelProvider(requireActivity(), RegistrationViewModelFactory(useCases))
                .get(RegistrationViewModel::class.java)
        observer()
        return fragmentRegistrationBinding.root
    }

    override fun onBackBtnClicked() {
        if (navController.currentDestination?.id == R.id.registrationFragment)
            navController.popBackStack(R.id.registrationFragment, true)
    }

    override fun onNextClicked() {
        if (checkValidateAllFields()) {

            registrationViewModel.registerUser(
                registrationModel.emailTxt,
                password
            )
                .observe(viewLifecycleOwner, { it ->
                    when (it) {
                        is Resource.Loading<*> -> {
                            registrationViewModel.loading.postValue(true)

                        }
                        is Resource.Success<*> -> {
                            registrationViewModel.createUserWithNewAccount(registrationModel)
                        }
                        is Resource.Failure<*> -> {
                            registrationViewModel.loading.postValue(false)
                        }
                    }
                })
        }
    }

    override fun onAlreadyHaveAccountClicked() {
        if (navController.currentDestination?.id == R.id.registrationFragment)
            navController.popBackStack(R.id.registrationFragment, true)
    }

    @SuppressLint("HardwareIds")
    private fun checkValidateAllFields(): Boolean {
        isValid = true
        userName = fragmentRegistrationBinding.etxFirstName.text.toString()
        phoneNumber = fragmentRegistrationBinding.etxPhone.text.toString()
        emailTxt = fragmentRegistrationBinding.etxEmail.text.toString()
        password = fragmentRegistrationBinding.etxPassword.text.toString()
        confirmPassword = fragmentRegistrationBinding.etxPasswordConfirm.text.toString()
        registrationModel.userName = userName
        registrationModel.emailTxt = emailTxt
        registrationModel.phoneNumber = phoneNumber

        if (registrationViewModel.validateEmail(emailTxt))
            setInputLayoutSuccess(fragmentRegistrationBinding.tlEmail)
        else {
            isValid = false
            setInputLayoutError(
                fragmentRegistrationBinding.tlEmail,
                getString(R.string.email_not_valid)
            )
        }
        if (registrationViewModel.validateName(userName))
            setInputLayoutSuccess(fragmentRegistrationBinding.tlUserName)
        else {
            isValid = false
            setInputLayoutError(
                fragmentRegistrationBinding.tlUserName,
                getString(R.string.first_name_error)
            )
        }

        if (registrationViewModel.validateMobileNumber(phoneNumber))
            setInputLayoutSuccess(fragmentRegistrationBinding.tlMobilePhone)
        else {
            isValid = false
            setInputLayoutError(
                fragmentRegistrationBinding.tlMobilePhone,
                getString(R.string.phone_error)
            )
        }

        if (registrationViewModel.validatePassword(password)){
            setInputLayoutSuccess(fragmentRegistrationBinding.tlPassword)
                if (registrationViewModel.validateConfirmPassword(password, confirmPassword))
                    setInputLayoutSuccess(fragmentRegistrationBinding.tlConfirmPassword)
                else {
                    isValid = false
                    setInputLayoutError(
                        fragmentRegistrationBinding.tlConfirmPassword,
                        getString(R.string.confirm_password_error)
                    )
                }
            }
        else {
            isValid = false
            setInputLayoutError(
                fragmentRegistrationBinding.tlPassword,
                getString(R.string.password_char)
            )
        }


        return isValid
    }

    private fun setInputLayoutError(
        inputLayout: TextInputLayout, errorMeString: String
    ) {
        inputLayout.isErrorEnabled = true
        inputLayout.error = errorMeString
    }

    private fun setInputLayoutSuccess(
        inputLayout: TextInputLayout
    ) {
        inputLayout.isErrorEnabled = false
        inputLayout.error = ""
    }

    private fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    private fun observer() {
        registrationViewModel.isError.observe(viewLifecycleOwner, {
            Toast.makeText(
                context,
                context?.getString(R.string.check_your_connection),
                Toast.LENGTH_SHORT
            ).show()

        })

        registrationViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

        fragmentRegistrationBinding.etxEmail.doAfterTextChanged { s ->
            if (s.toString().isNotEmpty()) {
                if (!registrationViewModel.validateEmail(s.toString())) {
                    setInputLayoutError(
                        fragmentRegistrationBinding.tlEmail,
                        getString(R.string.email_not_valid)
                    )
                } else {
                    setInputLayoutSuccess(fragmentRegistrationBinding.tlEmail)
                }

            }
        }
        fragmentRegistrationBinding.etxPassword.doAfterTextChanged { s ->
            if (s.toString().isNotEmpty()) {
                if (!registrationViewModel.validatePassword(s.toString())) {
                    setInputLayoutError(
                        fragmentRegistrationBinding.tlPassword,
                        getString(R.string.password_char)
                    )
                } else {
                    password = s.toString()
                    setInputLayoutSuccess(fragmentRegistrationBinding.tlPassword)
                    fragmentRegistrationBinding.etxPasswordConfirm.doAfterTextChanged {
                        if (registrationViewModel.validateConfirmPassword(
                                password,
                                it.toString()
                            )
                        ) {
                            setInputLayoutSuccess(fragmentRegistrationBinding.tlConfirmPassword)
                            confirmPassword = it.toString()
                        } else {
                            isValid = false
                            setInputLayoutError(
                                fragmentRegistrationBinding.tlConfirmPassword,
                                getString(R.string.confirm_password_error)
                            )
                        }

                    }
                }

            }
        }



        fragmentRegistrationBinding.etxFirstName.doAfterTextChanged { s ->
            if (s.toString().isNotEmpty()) {
                if (!registrationViewModel.validateName(s.toString())) {
                    setInputLayoutError(
                        fragmentRegistrationBinding.tlUserName,
                        getString(R.string.first_name_error)
                    )
                } else {
                    setInputLayoutSuccess(fragmentRegistrationBinding.tlUserName)
                }

            }
        }


        fragmentRegistrationBinding.etxPhone.doAfterTextChanged { s ->
            if (s.toString().isNotEmpty()) {
                if (!registrationViewModel.validateMobileNumber(s.toString())) {
                    setInputLayoutError(
                        fragmentRegistrationBinding.tlMobilePhone,
                        getString(R.string.phone_error)
                    )
                } else {
                    setInputLayoutSuccess(fragmentRegistrationBinding.tlMobilePhone)
                }
            }
        }


        registrationViewModel.navigateToMain.observe(viewLifecycleOwner, Observer {
            if (it) {
                showToast(getString(R.string.register_success_message))

                if (navController.currentDestination?.id == R.id.registrationFragment)
                    navController.popBackStack(R.id.registrationFragment, true)
            }
        })


    }

    private fun setDialogConfiguration() {
        progressDialog = Dialog(requireContext())
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setContentView(R.layout.custom_progress_dialog)
        Objects.requireNonNull(progressDialog.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

}