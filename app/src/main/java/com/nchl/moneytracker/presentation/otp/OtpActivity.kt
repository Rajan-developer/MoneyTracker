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
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.nchl.moneytracker.BR
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.ActivityOtpBinding
import com.nchl.moneytracker.presentation.base.AppBaseActivity
import com.nchl.moneytracker.presentation.utils.log.Logger


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class OtpActivity : AppBaseActivity<ActivityOtpBinding, OtpViewModel>() {

    private val logger = Logger(OtpActivity::class.java.name)
    private lateinit var binding: ActivityOtpBinding
    private lateinit var editTexts: Array<EditText>


    companion object {
        fun getLaunchIntent(
            context: Context,
        ): Intent {
            val intent = Intent(context, OtpActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initObservers()
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
        }
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
            getViewModel().submitOtp(getViewModel().otp.value)
        }

    }


    override fun getBindingVariable(): Int = BR.loginViewModel

    override fun getLayout(): Int = R.layout.activity_otp

    override fun getViewModel(): OtpViewModel =
        ViewModelProviders.of(this)[OtpViewModel::class.java]
}