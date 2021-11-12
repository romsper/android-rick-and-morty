package com.romsper.firebase_authentication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.romsper.firebase_authentication.R
import com.romsper.firebase_authentication.ui.main.view.MainActivity

import android.content.Intent
import android.os.Handler


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}