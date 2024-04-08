package com.nchl.moneytracker.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.nchl.moneytracker.R
import com.nchl.moneytracker.presentation.dashboard.DashActivity
import com.nchl.moneytracker.presentation.login.LoginActivity
import com.nchl.moneytracker.presentation.utils.view.TypeWriterTextView


@RequiresApi(Build.VERSION_CODES.O)
class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_splash)

        val typeWriterTextView = findViewById<TypeWriterTextView>(R.id.typeWriterTextView)
        typeWriterTextView.setCharacterDelay(100)
        typeWriterTextView.animateText("Navigate Your Money, Track Your Progress.")


        val handler = Handler()
        handler.postDelayed(Runnable {
            if (auth.currentUser != null) {
                startActivity(Intent(this@SplashActivity, DashActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        }, 5000)

    }

    override fun onStart() {
        super.onStart()

    }
}