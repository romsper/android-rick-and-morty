package com.romsper.android_rick_and_morty.ui.features.authorization

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.romsper.android_rick_and_morty.R
import com.romsper.android_rick_and_morty.databinding.FragmentLoginBinding
import com.romsper.android_rick_and_morty.ui.base.fragment.BaseFragment
import com.romsper.android_rick_and_morty.util.appToast
import com.romsper.android_rick_and_morty.util.findNavController


class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    lateinit var signInIntent: Intent
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        setUpGoogle()

        binding.btnGoogle.setOnClickListener { signIn() }

        binding.btnLogin.setOnClickListener {
            val username = if (binding.username.text?.toString()
                    .isNullOrBlank()
            ) " " else binding.username.text.toString()
            val password = if (binding.password.text?.toString()
                    .isNullOrBlank()
            ) " " else binding.password.text.toString()

            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(
                requireActivity()
            ) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    appToast("Successfully Logged In", true)
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToCharacterListFragment()
                    )
                } else {
                    binding.errorMessage.text = "* ${task.exception?.message}"
                }
            }
        }

        binding.linkForgotPassword.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
            )
        }

        binding.linkSignUp.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            )
        }

        binding.linkSkipAuth.setOnClickListener {
            appToast("Rick and Morty greeting you!")
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToCharacterListFragment()
            )
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(
            requireActivity()
        ) { task ->
            if (task.isSuccessful) {
                val user = firebaseAuth.currentUser
                Log.d(TAG, "signInWithCredential:success")
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToCharacterListFragment()
                )
            } else {
                Log.w(TAG, "signInWithCredential:failure", task.exception)
                appToast("User ID or Password incorrect!")
            }
        }
    }

    private fun setUpGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(resources.getString(R.string.app_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun signIn() {
        signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // There are no request codes
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            appToast("Google sign in failed", true)
            Log.w(TAG, "Google sign in failed", e)
        }
    }
}