package com.nchl.moneytracker.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.nchl.moneytracker.BR
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.ActivityLoginBinding
import com.nchl.moneytracker.presentation.base.AppBaseActivity
import com.nchl.moneytracker.presentation.dashboard.DashActivity
import com.nchl.moneytracker.presentation.otp.OtpActivity
import com.nchl.moneytracker.presentation.register.RegisterActivity
import com.nchl.moneytracker.presentation.utils.log.Logger
import java.util.concurrent.TimeUnit


@RequiresApi(Build.VERSION_CODES.O)
class LoginActivity : AppBaseActivity<ActivityLoginBinding, LoginViewModel>() {

    private val logger = Logger(LoginActivity::class.java.name)
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    companion object {
        fun getLaunchIntent(
            context: Context,
        ): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initObservers()
    }

    private fun initViews() {
        auth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener {
            getViewModel().validateLoginCredential(
                binding.etPhoneNumber.text.toString(),
                binding.etPassword.text.toString()
            )
        }


        binding.tvSignUp.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun initObservers() {
        getViewModel().loginProcessStart.observe(this) {
            if (it) {
                getViewModel().isLoading.value = true
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.etPassword.windowToken, 0)
                /*getViewModel().login(
                    binding.etPassword.text.toString(),
                    binding.etPassword.text.toString()
                )*/
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber("+977${binding.etPhoneNumber.text}")       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this)                 // Activity (for callback binding)
                    .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }


        getViewModel().isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                showProgress()
            } else {
                hideProgress()
            }
        })
    }

    private fun navigateToRegister() {
        val intent = RegisterActivity.getLaunchIntent(this)
        startActivity(intent)
        finish()
    }

//    override fun onStart() {
//        super.onStart()
//        if (auth.currentUser != null) {
//            startActivity(Intent(this@LoginActivity, DashActivity::class.java))
//        }
//    }

    override fun getBindingVariable(): Int = BR.loginViewModel

    override fun getLayout(): Int = R.layout.activity_login

    override fun getViewModel(): LoginViewModel =
        ViewModelProviders.of(this)[LoginViewModel::class.java]


    /*===================Firebase Code=================================*/


    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d("TAG", "onVerificationCompleted:$credential")
            getViewModel().isLoading.value = false
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("TAG", "onVerificationFailed", e)
            getViewModel().isLoading.value = false
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("TAG", "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            showToast("Code send successfully")
            getViewModel().isLoading.value = false
            var intent = OtpActivity.getLaunchIntent(
                this@LoginActivity,
                verificationId,
                token,
                binding.etPhoneNumber.text.toString()
            )
            startActivity(intent)

        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    showToast("SignIn Successful")
                    val user = task.result?.user
                    startActivity(Intent(this@LoginActivity, DashActivity::class.java))

                } else {
                    // Sign in failed, display a message and update the UI
                    showToast("SignIn Failed")
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
}