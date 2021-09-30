package com.romsper.firebase_authentication.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.romsper.firebase_authentication.databinding.ActivityLoginBinding

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar

import com.romsper.firebase_authentication.util.BaseActivity
import com.romsper.firebase_authentication.ui.main.view.MainActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    private lateinit var snackbar: Snackbar

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        val isEmailSent = intent.extras?.getBoolean("emailSent")
        if (isEmailSent == true) {
            snackbar = Snackbar.make(
                binding.container,
                "Reset password email has been sent",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction("GOT IT", View.OnClickListener {
                    snackbar.dismiss()
                })
            snackbar.show()
        }

        binding.btnLogin!!.setOnClickListener {

            val username = if (binding.username.text?.toString()
                    .isNullOrBlank()
            ) " " else binding.username.text.toString()
            val password = if (binding.password.text?.toString()
                    .isNullOrBlank()
            ) " " else binding.password.text.toString()

            mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        binding.errorMessage!!.text = "* ${task.exception?.message}"
                    }
                })
        }

        binding.btnForgotPassword!!.setOnClickListener {
            startActivity(Intent(this, ForgotActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onStop() {
        super.onStop()
        mAuth.signOut()
    }
}