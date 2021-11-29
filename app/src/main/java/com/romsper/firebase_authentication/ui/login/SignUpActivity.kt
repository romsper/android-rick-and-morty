package com.romsper.firebase_authentication.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.romsper.firebase_authentication.R
import com.romsper.firebase_authentication.databinding.ActivityForgotBinding
import com.romsper.firebase_authentication.databinding.ActivitySignUpBinding
import com.romsper.firebase_authentication.util.BaseActivity

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(ActivitySignUpBinding::inflate) {

    override fun onStart() {
        super.onStart()

        binding.linkSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}