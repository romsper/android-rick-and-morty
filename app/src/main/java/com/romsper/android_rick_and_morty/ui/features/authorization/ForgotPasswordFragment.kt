package com.romsper.android_rick_and_morty.ui.features.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.romsper.android_rick_and_morty.R
import com.romsper.android_rick_and_morty.databinding.FragmentForgotPasswordBinding
import com.romsper.android_rick_and_morty.databinding.FragmentLoginBinding
import com.romsper.android_rick_and_morty.ui.base.fragment.BaseFragment
import com.romsper.android_rick_and_morty.util.appToast
import com.romsper.android_rick_and_morty.util.findNavController


class ForgotPasswordFragment : BaseFragment(R.layout.fragment_forgot_password) {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentForgotPasswordBinding.bind(view)

        binding.linkForgotPassword.setOnClickListener {
            val username = if (binding.username.text?.toString()
                    .isNullOrBlank()
            ) " " else binding.username.text.toString()

            firebaseAuth.sendPasswordResetEmail(username)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        appToast("Recovery email was sent")
                        findNavController().navigateUp()
                    } else {
                        binding.errorMessage.text = "* ${task.exception?.message}"
                    }
                }
        }
    }
}