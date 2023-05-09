package com.example.nanopost.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private val vm: RegisterViewModel by viewModels()
    private val binding by viewBinding(FragmentRegisterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            registrationButton.setOnClickListener {
                vm.registerUser(userNameTextEditor.text.toString(), passwordTextEditor.text.toString())
            }
        }

        vm.responses.observe(viewLifecycleOwner){ response->
            if(response !== null){
                Snackbar.make(binding.passwordText, response, 400).show()
            }
        }
    }
}