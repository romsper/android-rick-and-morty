package com.romsper.firebase_authentication.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.romsper.firebase_authentication.R
import com.romsper.firebase_authentication.ui.main.view.MainActivity

import android.content.Intent
import android.os.Handler
import com.romsper.firebase_authentication.ui.login.LoginActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2000)
    }
}