//package com.romsper.firebase_authentication.ui.features.authorization
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.ContentValues.TAG
//import android.content.Intent
//import android.util.Log
//import android.view.View
//import android.widget.Toast
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import com.romsper.firebase_authentication.databinding.ActivityLoginBinding
//
//import com.google.android.gms.tasks.OnCompleteListener
//import com.google.android.material.snackbar.Snackbar
//
//import com.romsper.firebase_authentication.util.BaseActivity
//import com.romsper.firebase_authentication.ui.features.characterList.view.MainActivity
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException
//
//import com.google.firebase.auth.GoogleAuthProvider
//import com.romsper.firebase_authentication.R
//
//
//class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
//    private lateinit var snackbar: Snackbar
//    lateinit var googleSignInClient: GoogleSignInClient
//    lateinit var signInIntent: Intent
//
//    @SuppressLint("SetTextI18n")
//    override fun onStart() {
//        super.onStart()
//
//        setUpGoogle()
//
//        val isEmailSent = intent.extras?.getBoolean("emailSent")
//        if (isEmailSent == true) {
//            snackbar = Snackbar.make(
//                binding.container,
//                "Reset password email has been sent",
//                Snackbar.LENGTH_INDEFINITE
//            )
//                .setAction("GOT IT", View.OnClickListener {
//                    snackbar.dismiss()
//                })
//            snackbar.show()
//        }
//
//        binding.btnGoogle!!.setOnClickListener { signIn() }
//
//        binding.btnLogin!!.setOnClickListener {
//            val username = if (binding.username.text?.toString()
//                    .isNullOrBlank()
//            ) " " else binding.username.text.toString()
//            val password = if (binding.password.text?.toString()
//                    .isNullOrBlank()
//            ) " " else binding.password.text.toString()
//
//            firebaseAuth.signInWithEmailAndPassword(username, password)
//                .addOnCompleteListener(this, OnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(this, MainActivity::class.java))
//                        finish()
//                    } else {
//                        binding.errorMessage!!.text = "* ${task.exception?.message}"
//                    }
//                })
//        }
//
//        binding.linkForgotPassword!!.setOnClickListener {
//            startActivity(Intent(this, ForgotActivity::class.java))
//            finish()
//        }
//
//        binding.linkSkipAuth!!.setOnClickListener {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
//
//        binding.linkSignUp!!.setOnClickListener {
//            startActivity(Intent(this, SignUpActivity::class.java))
//            finish()
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        firebaseAuth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithCredential:success")
//                    val user = firebaseAuth.currentUser
//                    startActivity(Intent(this, MainActivity::class.java))
//                    finish()
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                }
//            }
//    }
//
//    private fun setUpGoogle() {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(resources.getString(R.string.app_client_id))
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//    }
//
//    private fun signIn() {
//        signInIntent = googleSignInClient.signInIntent
//        resultLauncher.launch(signInIntent)
//    }
//
//    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            // There are no request codes
//            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                val account = task.getResult(ApiException::class.java)!!
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                // Google Sign In failed, update UI appropriately
//                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
//                Log.w(TAG, "Google sign in failed", e)
//            }
//        }
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        finish()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        firebaseAuth.signOut()
//    }
//}