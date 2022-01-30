package com.romsper.firebase_authentication.ui.features.authorization

import android.annotation.SuppressLint
import android.content.Intent
import com.romsper.firebase_authentication.databinding.ActivityForgotBinding
import com.romsper.firebase_authentication.util.BaseActivity

class ForgotActivity : BaseActivity<ActivityForgotBinding>(ActivityForgotBinding::inflate) {

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        binding.linkForgotPassword.setOnClickListener {
            val username = if (binding.username.text?.toString()
                    .isNullOrBlank()
            ) " " else binding.username.text.toString()

            firebaseAuth.sendPasswordResetEmail(username)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, LoginActivity::class.java).putExtra("emailSent", true))
                        finish()
                    } else {
                        binding.errorMessage.text = "* ${task.exception?.message}"
                    }
                }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}