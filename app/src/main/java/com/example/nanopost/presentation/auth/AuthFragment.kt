package com.example.nanopost.presentation.auth

import android.opengl.Visibility
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.internal.findRootView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentAuthBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {
    private val vm: AuthViewModel by viewModels()
    private val binding by viewBinding(FragmentAuthBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val check = vm.checkToken()
        if(!check.isNullOrBlank()){
            findNavController().navigate(R.id.action_authFragment_to_feedFragment2, bundleOf(
                "USERNAME" to check
            ))
        }
        binding.apply {
            continueButton.setOnClickListener {
                usernameEditTextLayout.isErrorEnabled = false
                passwordEditTextLayout.isErrorEnabled = false
                confirmEditTextLayout.isErrorEnabled = false
                when{
                    passwordEditTextLayout.visibility == View.GONE -> {
                        vm.checkUsername(usernameEditText.text.toString())
                    }
                    confirmEditTextLayout.visibility == View.GONE -> {
                        vm.checkUsername(usernameEditText.text.toString())
                        vm.authUser(
                            usernameEditText.text.toString(),
                            passwordEditText.text.toString()
                        )
                    }
                    else -> {
                        if (!vm.checkPassword(passwordEditText.text.toString())){
                            passwordEditTextLayout.error = "Wrong password"
                            passwordEditTextLayout.isErrorEnabled = true
                        }
                        if(passwordEditText.text.contentEquals(confirmEditText.text)){
                            vm.registerUser(
                                usernameEditText.text.toString(),
                                passwordEditText.text.toString()
                            )
                        } else {
                            confirmEditTextLayout.error = "Passwords do not match"
                            confirmEditTextLayout.isErrorEnabled = true
                        }
                    }
                }

                usernameEditText.setOnFocusChangeListener { view, b ->
                    toCheckState()
                }
            }

            vm.checkUsernameResponses.observe(viewLifecycleOwner){  response->
                val username = vm.checkToken()
                when(response){
                    USERNAME_IS_TAKEN -> if(username !== null){
                            findNavController().navigate(R.id.action_authFragment_to_profileFragment2,
                                bundleOf("USERNAME" to username)
                            )
                        } else {
                            toAuthState()
                        }
                    USERNAME_IS_FREE -> {
                        toRegisterState()
                    }
                    else -> {
                        binding.usernameEditTextLayout.error = response
                        binding.usernameEditTextLayout.isErrorEnabled = true
                    }
                }
            }

            vm.authResponses.observe(viewLifecycleOwner){   response->
                if(!vm.checkPassword(passwordEditText.text.toString())){
                    binding.passwordEditTextLayout.error = "Wrong password"
                    binding.passwordEditTextLayout.isErrorEnabled = true
                }
                when(response){
                    null -> {
                        binding.passwordEditTextLayout.error = "Wrong password"
                        binding.passwordEditTextLayout.isErrorEnabled = true
                    }
                    else -> findNavController().navigate(R.id.action_authFragment_to_profileFragment2,
                        bundleOf("USERNAME" to response)
                    )
                }
            }

            vm.registerResponses.observe(viewLifecycleOwner){   response->
                when(response){
                    null -> binding.confirmEditTextLayout.isErrorEnabled = true
                    else -> findNavController().navigate(R.id.action_authFragment_to_profileFragment2,
                        bundleOf("USERNAME" to response)
                    )
                }
            }

        }


    }

    private fun toCheckState(){
        binding.apply {
            passwordEditTextLayout.visibility = View.GONE
            confirmEditTextLayout.visibility = View.GONE
            binding.continueButton.text = "Continue"
        }
    }

    private fun toAuthState(){
        binding.apply {
            passwordEditTextLayout.visibility = View.VISIBLE
            confirmEditTextLayout.visibility = View.GONE
            binding.continueButton.text = "Sign in"
        }
    }

    private fun toRegisterState(){
        binding.apply {
            passwordEditTextLayout.visibility = View.VISIBLE
            confirmEditTextLayout.visibility = View.VISIBLE
            binding.continueButton.text = "Register"
        }
    }

    companion object {
        const val USERNAME_IS_FREE = "Free"
        const val USERNAME_IS_TAKEN = "Taken"

    }
}