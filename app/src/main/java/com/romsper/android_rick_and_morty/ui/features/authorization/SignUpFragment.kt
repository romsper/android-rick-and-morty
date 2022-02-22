package com.romsper.android_rick_and_morty.ui.features.authorization

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.romsper.android_rick_and_morty.R
import com.romsper.android_rick_and_morty.databinding.FragmentLoginBinding
import com.romsper.android_rick_and_morty.databinding.FragmentSignUpBinding
import com.romsper.android_rick_and_morty.ui.base.fragment.BaseFragment
import com.romsper.android_rick_and_morty.util.appToast
import com.romsper.android_rick_and_morty.util.findNavController


class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {
    lateinit var username: String
    lateinit var password: String
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)

        binding.btnSignUp.setOnClickListener {
            username = binding.username.text.toString()
            password = binding.password.text.toString()

            if (username.isBlank() || password.isBlank() || binding.repeatPassword.text.toString().isBlank()) binding.errorMessage.text =
                "* Fields cannot be empty"
            else if (password != binding.repeatPassword.text.toString()) binding.errorMessage.text =
                "* Passwords don't match"
            else if (!binding.checkboxTerm.isChecked) binding.errorMessage.text =
                "* Please, accept the terms and policy"
            else firebaseAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        findNavController().navigateUp()
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        appToast("Authentication failed.")
                    }
                }
        }

        binding.linkSignIn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}