package com.scandrug.scandrug.presentation.authentication
import android.app.Dialog
import android.content.Context
import android.content.Intent
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
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.scandrug.scandrug.BuildConfig
import com.scandrug.scandrug.R
import com.scandrug.scandrug.base.BaseApplication
import com.scandrug.scandrug.data.local.AppPreferences
import com.scandrug.scandrug.data.remotemodel.RegistrationModel
import com.scandrug.scandrug.data.repository.AuthRepoImp
import com.scandrug.scandrug.data.resources.Resource
import com.scandrug.scandrug.databinding.FragmentLoginBinding
import com.scandrug.scandrug.domain.usecases.AuthUseCases
import com.scandrug.scandrug.presentation.authentication.interfaces.LoginOnClickView
import com.scandrug.scandrug.presentation.authentication.viewmodel.LoginViewModel
import com.scandrug.scandrug.presentation.authentication.viewmodelfactories.LoginViewModelFactory
import com.scandrug.scandrug.presentation.home.viewmodel.ScanViewModel
import com.scandrug.scandrug.presentation.home.viewmodelfactories.ScanViewModelFactory
import com.scandrug.scandrug.presentation.home.MainActivity
import java.util.*

class LoginFragment : Fragment(), LoginOnClickView {
    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var navController: NavController
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var useCases: AuthUseCases
    private lateinit var email: String
    private lateinit var  progressDialog: Dialog

    private lateinit var registrationModel: RegistrationModel
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
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        fragmentLoginBinding.loginView = this
        setDialogConfiguration()
        navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        val repository = AuthRepoImp()
        useCases = AuthUseCases(repository)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(useCases))
            .get(LoginViewModel::class.java)
        observer()
        appPreferences = AppPreferences(sharedPreferences)

        return fragmentLoginBinding.root
    }

    override fun loginClicked() {
        prepareLogin()
    }

    override fun signUpClicked() {
        if (navController.currentDestination!!.id == R.id.loginFragment)
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
    }

    override fun forgetPassword() {
//        if (navController.currentDestination!!.id == R.id.loginFragment)
//            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment())
    }


    private fun prepareLogin() {
        email = fragmentLoginBinding.etxEmail.text.toString()
        val password = fragmentLoginBinding.etxPassword.text.toString()

        if (!loginViewModel.validateEmail(email) && !loginViewModel.validatePassword(password)) {
            setInputLayoutError(
                fragmentLoginBinding.emailLayout,
                getString(R.string.email_not_valid)
            )
            setInputLayoutError(
                fragmentLoginBinding.passwordLayout,
                getString(R.string.password_char)
            )
        } else if (!loginViewModel.validateEmail(email)) {
            setInputLayoutError(
                fragmentLoginBinding.emailLayout,
                getString(R.string.email_not_valid)
            )
            setInputLayoutSuccess(fragmentLoginBinding.passwordLayout)
        } else if (!loginViewModel.validatePassword(password)) {
            setInputLayoutError(
                fragmentLoginBinding.passwordLayout,
                getString(R.string.password_char)
            )
            setInputLayoutSuccess(fragmentLoginBinding.emailLayout)
        } else {
            setInputLayoutSuccess(fragmentLoginBinding.emailLayout)
            setInputLayoutSuccess(fragmentLoginBinding.passwordLayout)
            loginViewModel.loginUser(email, password).observe(viewLifecycleOwner,
                { result ->
                    when (result) {
                        is  Resource.Loading -> {
                            loginViewModel.loading.value = true
                        }
                        is Resource.Success -> {
                            loginViewModel.loading.postValue(false)
                            showToast(getString(R.string.login_success_message))
                            appPreferences.setAccessToken("company")
                            val intent = Intent(context, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or
                                        Intent.FLAG_ACTIVITY_CLEAR_TOP

                            startActivity(intent)
                            activity?.finish()
                            activity?.finishAffinity()
                        }
                        is Resource.Failure -> {
                            loginViewModel.loading.postValue(false)
                            showToast(getString(R.string.email_not_exist))
                        }
                    }
                }
            )


        }
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
        loginViewModel.isError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, context?.getString(R.string.check_your_connection), Toast.LENGTH_SHORT).show()
        })

        loginViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }

        })
        fragmentLoginBinding.etxEmail.doAfterTextChanged { s ->
            if (s.toString().isNotEmpty()) {
                if (!loginViewModel.validateEmail(s.toString())) {
                    setInputLayoutError(
                        fragmentLoginBinding.emailLayout,
                        getString(R.string.email_not_valid)
                    )
                } else {
                    setInputLayoutSuccess(fragmentLoginBinding.emailLayout)
                }
            }
        }

        fragmentLoginBinding.etxPassword.doAfterTextChanged { s ->
            if (s.toString().isNotEmpty()) {
                if (s.toString().isNotEmpty()) {
                    if (!loginViewModel.validatePassword(s.toString())) {
                        setInputLayoutError(
                            fragmentLoginBinding.passwordLayout,
                            getString(R.string.password_char)
                        )
                    } else {
                        setInputLayoutSuccess(fragmentLoginBinding.passwordLayout)
                    }
                }
            }
        }


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