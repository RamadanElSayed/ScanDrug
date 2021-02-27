package com.scandrug.scandrug.presentation.authentication

import android.app.Dialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.scandrug.scandrug.domain.usecases.AuthUseCases
import com.scandrug.scandrug.presentation.authentication.viewmodel.ForgetPasswordViewModel

class ForgetPasswordFragment : Fragment(){
 //   private lateinit var forgetPasswordBinding: FragmentForgetPasswordBinding
    private lateinit var navController: NavController
    private lateinit var forgetPasswordViewModel: ForgetPasswordViewModel
    private lateinit var useCases: AuthUseCases
    private lateinit var  progressDialog: Dialog


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setDialogConfiguration()
//
//    }
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        forgetPasswordBinding = FragmentForgetPasswordBinding.inflate(inflater)
//        forgetPasswordBinding.forgetView = this
//        navController =
//            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
//        val bankitDao=BnkitDatabase.createDatabase(requireContext()).bnkitDao()
//        val repository = AuthRepoImp(bankitDao)
//        useCases = AuthUseCases(repository)
//        forgetPasswordViewModel = ViewModelProvider(this, ForgetPasswordViewModelFactory(useCases))
//            .get(ForgetPasswordViewModel::class.java)
//        observer()
//        return forgetPasswordBinding.root
//    }
//
//    override fun OnConfirmClickListener() {
//        if (navController.currentDestination?.id == R.id.forgetPasswordFragment)
//            navController.popBackStack(R.id.forgetPasswordFragment, true)
//    }
//
//    override fun OnCancelClickListener() {
//        if (navController.currentDestination?.id == R.id.forgetPasswordFragment)
//            navController.popBackStack(R.id.forgetPasswordFragment, true)
//    }
//
//    override fun onForgetPasswordClicked() {
//        prepareLogin()
//    }
//
//    override fun onBackClicked() {
//        if (navController.currentDestination?.id == R.id.forgetPasswordFragment)
//            navController.popBackStack(R.id.forgetPasswordFragment, true)
//    }
//
//    private fun prepareLogin() {
//        val email = forgetPasswordBinding.etxEmail.text.toString()
//
//        if (!forgetPasswordViewModel.validateEmail(email)) {
//            setInputLayoutError(
//                forgetPasswordBinding.emailLayout,
//                getString(R.string.email_not_valid)
//            )
//        } else {
//            setInputLayoutSuccess(forgetPasswordBinding.emailLayout)
//            forgetPasswordViewModel.resetPassword(email).observe(viewLifecycleOwner,
//                Observer { result ->
//                    when (result) {
//                        is Resource.Loading -> {
//                            forgetPasswordViewModel.loading.value = true
//                        }
//                        is Resource.Success -> {
//                            forgetPasswordViewModel.loading.postValue(false)
//                            val messageDialogFragment =
//                                MessageDialogFragment(
//                                    hasTitle = false,
//                                    hasSuccess = false,
//                                    title = "",
//                                    body = getString(R.string.message_check_email),
//                                    dialogOnClickListener = this
//                                )
//                            messageDialogFragment.show(
//                                requireActivity().supportFragmentManager,
//                                "message_dialog"
//                            )
//                        }
//                        is Resource.Failure -> {
//                            forgetPasswordViewModel.loading.postValue(false)
//                            showToast(getString(R.string.email_invalid))
//                        }
//                    }
//                }
//            )
//
//
//        }
//    }
//
//    private fun setInputLayoutError(
//        inputLayout: TextInputLayout, errorMeString: String
//    ) {
//        inputLayout.isErrorEnabled = true
//        inputLayout.error = errorMeString
//    }
//
//    private fun setInputLayoutSuccess(
//        inputLayout: TextInputLayout
//    ) {
//        inputLayout.isErrorEnabled = false
//        inputLayout.error = ""
//    }
//
//    private fun showToast(msg: String) {
//        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
//    }
//
//    private fun observer() {
//        forgetPasswordViewModel.isError.observe(viewLifecycleOwner, Observer {
//            Toast.makeText(context, context?.getString(R.string.check_your_connection), Toast.LENGTH_SHORT).show()
//        })
//
//        forgetPasswordViewModel.loading.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                progressDialog.show()
//            } else {
//                progressDialog.dismiss()
//            }
//        })
//
//        forgetPasswordBinding.etxEmail.addTextChangedListener(object : TextWatcher {
//
//            override fun afterTextChanged(s: Editable) {
//                if (s.toString().isNotEmpty()) {
//                    if (!forgetPasswordViewModel.validateEmail(s.toString())) {
//                        setInputLayoutError(
//                            forgetPasswordBinding.emailLayout,
//                            getString(R.string.email_not_valid)
//                        )
//                    } else {
//                        setInputLayoutSuccess(forgetPasswordBinding.emailLayout)
//                    }
//                }
//            }
//            override fun beforeTextChanged(s: CharSequence, start: Int,
//                                           count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int,
//                                       before: Int, count: Int) {
//
//            }
//        })
//
//
//    }
//
//    private fun setDialogConfiguration() {
//        progressDialog = Dialog(requireContext())
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        progressDialog.setCancelable(false)
//        progressDialog.setCanceledOnTouchOutside(false)
//        progressDialog.setContentView(R.layout.custom_progress_dialog)
//        Objects.requireNonNull(progressDialog.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//    }
}