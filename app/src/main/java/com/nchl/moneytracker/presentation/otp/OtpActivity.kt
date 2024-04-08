package com.nchl.moneytracker.presentation.otp

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.nchl.moneytracker.BR
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.ActivityOtpBinding
import com.nchl.moneytracker.presentation.base.AppBaseActivity
import com.nchl.moneytracker.presentation.dashboard.DashActivity
import com.nchl.moneytracker.presentation.utils.AppUtility.generateRandomDigits
import com.nchl.moneytracker.presentation.utils.log.Logger
import java.util.concurrent.TimeUnit


@RequiresApi(Build.VERSION_CODES.O)
class OtpActivity : AppBaseActivity<ActivityOtpBinding, OtpViewModel>() {

    private val logger = Logger(OtpActivity::class.java.name)
    private lateinit var binding: ActivityOtpBinding
    private lateinit var editTexts: Array<EditText>
    private lateinit var auth: FirebaseAuth
    private lateinit var OTP: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String


    companion object {
        const val BUNDLE_OTP = "OTP"
        const val BUNDLE_TOKEN = "resendToken"
        const val BUNDLE_PHONE_NUMBER = "phoneNumber"
        fun getLaunchIntent(
            context: Context,
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
            phoneNumber: String
        ): Intent {
            val intent = Intent(context, OtpActivity::class.java)
            intent.putExtra(BUNDLE_OTP, verificationId)
            intent.putExtra(BUNDLE_TOKEN, token)
            intent.putExtra(BUNDLE_PHONE_NUMBER, phoneNumber)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundleData()
        initViews()
        initObservers()

        auth = FirebaseAuth.getInstance()
    }

    private fun getBundleData() {
        OTP = intent.getStringExtra(BUNDLE_OTP).toString()
        resendToken = intent.getParcelableExtra(BUNDLE_TOKEN)!!
        phoneNumber = intent.getStringExtra(BUNDLE_PHONE_NUMBER)!!
    }

    private fun initViews() {
        binding.tvResendText.text = getString(R.string.resend)
        binding.tvResendText.paintFlags =
            binding.tvResendText.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        // Load animation
        val animation = AnimationUtils.loadAnimation(this, R.anim.shadow_click_animation)

        setupOtpFields()

        binding.btnVerify.setOnClickListener {
            validateOtp()
        }

        binding.ivOtpBack.setOnClickListener {
            finish()
        }

        // Set click listener
        binding.tvResendText.setOnClickListener {
            // Start animation
            binding.tvResendText.startAnimation(animation)
            resendVerificationCode()
        }
    }

    private fun resendVerificationCode() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken)// OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun initObservers() {

    }

    private fun setupOtpFields() {
        editTexts =
            arrayOf(
                binding.etInputCode1,
                binding.etInputCode2,
                binding.etInputCode3,
                binding.etInputCode4,
                binding.etInputCode5,
                binding.etInputCode6
            )

        getViewModel().otp.value = ""
        editTexts.first().isEnabled = true
        editTexts.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().isNotEmpty()) {
                        // Move focus to the next EditText field if available
                        if (index < editTexts.size - 1) {
                            //editTexts[index + 1].isEnabled = true
                            editTexts[index + 1].requestFocus()
                        }
                        getViewModel().addOtpString(s.toString())
                    } else {
                        // Move focus to the previous EditText field if backspace is pressed
                        if (index > 0) {
                            editTexts[index - 1].requestFocus()
                            //editTexts[index + 1].isEnabled = false
                        }
                        getViewModel().removeOtpString()
                    }

                    binding.etInputCode1.backgroundTintList =
                        ContextCompat.getColorStateList(
                            this@OtpActivity,
                            R.color.colorPrimary
                        )
                    binding.etInputCode2.backgroundTintList =
                        ContextCompat.getColorStateList(
                            this@OtpActivity,
                            R.color.colorPrimary
                        )
                    binding.etInputCode3.backgroundTintList =
                        ContextCompat.getColorStateList(
                            this@OtpActivity,
                            R.color.colorPrimary
                        )
                    binding.etInputCode4.backgroundTintList =
                        ContextCompat.getColorStateList(
                            this@OtpActivity,
                            R.color.colorPrimary
                        )
                    binding.etInputCode5.backgroundTintList =
                        ContextCompat.getColorStateList(
                            this@OtpActivity,
                            R.color.colorPrimary
                        )
                    binding.etInputCode6.backgroundTintList =
                        ContextCompat.getColorStateList(
                            this@OtpActivity,
                            R.color.colorPrimary
                        )
                    binding.etInputCode6.setSelection(binding.etInputCode6.text.length)
                }
            })

//            editText.setOnFocusChangeListener { _, hasFocus ->
//                if (hasFocus && editText.text.isEmpty() && index > 0) {
//                    editTexts[index - 1].requestFocus()
//                }
//            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateOtp() {
        if (TextUtils.isEmpty(getViewModel().otp.value)) {
            showToast(getString(R.string.enter_otp))
            binding.etInputCode1.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.red)
            binding.etInputCode2.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.red)
            binding.etInputCode3.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.red)
            binding.etInputCode4.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.red)
            binding.etInputCode5.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.red)
            binding.etInputCode6.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.red)
        } else if (getViewModel().otp.value?.length!! < 6) {
            showToast(getString(R.string.valid_digit))
        } else {
            //getViewModel().submitOtp(getViewModel().otp.value)
            // sendOtpByEmail("shrestharajan162@gmail.com",generateRandomDigits(6))

            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                OTP, getViewModel().otp.value.toString()
            )
            signInWithPhoneAuthCredential(credential)

            if (getViewModel().otp.value == OTP) {
                navigateToDashboard()
            }
        }

    }

    private fun navigateToDashboard() {
        val intent = DashActivity.getLaunchIntent(this)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }


    override fun getBindingVariable(): Int = BR.loginViewModel

    override fun getLayout(): Int = R.layout.activity_otp

    override fun getViewModel(): OtpViewModel =
        ViewModelProviders.of(this)[OtpViewModel::class.java]


    /*===================Firebase Code=================================*/

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            }
            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            // Save verification ID and resending token so we can use them later
            OTP = verificationId
            resendToken = token
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    Toast.makeText(this, "Authenticate Successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@OtpActivity, DashActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d("TAG", "signInWithPhoneAuthCredential: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
}