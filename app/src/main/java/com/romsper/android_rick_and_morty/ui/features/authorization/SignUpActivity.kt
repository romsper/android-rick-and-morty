//package com.romsper.firebase_authentication.ui.features.authorization
//
//import android.content.ContentValues.TAG
//import android.content.Intent
//import android.util.Log
//import android.widget.Toast
//import com.romsper.firebase_authentication.databinding.ActivitySignUpBinding
//import com.romsper.firebase_authentication.ui.features.characterList.view.MainActivity
//import com.romsper.firebase_authentication.util.BaseActivity
//
//class SignUpActivity : BaseActivity<ActivitySignUpBinding>(ActivitySignUpBinding::inflate) {
//    lateinit var username: String
//    lateinit var password: String
//
//    override fun onStart() {
//        super.onStart()
//
//        binding.linkSignIn.setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }
//
//        binding.btnSignUp.setOnClickListener {
//            username = binding.username.text.toString()
//            password = binding.password.text.toString()
//
//            if (username.isBlank() || password.isBlank() || binding.repeatPassword.text.toString().isBlank()) binding.errorMessage.text =
//                "* Fields cannot be empty"
//            else if (password != binding.repeatPassword.text.toString()) binding.errorMessage.text =
//                "* Passwords don't match"
//            else if (!binding.checkboxTerm.isChecked) binding.errorMessage.text =
//                "* Please, accept the terms and policy"
//            else firebaseAuth.createUserWithEmailAndPassword(username, password)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "createUserWithEmail:success")
//                        startActivity(Intent(this, MainActivity::class.java))
//                        finish()
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                }
//        }
//    }
//}