package com.example.nanopost.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val vm: ProfileViewModel by viewModels()
    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getProfile("evo")
        with(binding.profileCardLayout) {
            subscribeButton.setOnClickListener {
                subscribeButton.visibility = View.INVISIBLE
                unsubscribeButton.visibility = View.VISIBLE
            }

            unsubscribeButton.setOnClickListener {
                unsubscribeButton.visibility = View.INVISIBLE
                subscribeButton.visibility = View.VISIBLE
            }
        }

        with(binding.postCardLayout) {
            favorButton.setOnClickListener {
                if(!favorButton.isActivated){
                    favorButton.isActivated = true
                    favorButton.icon = resources.getDrawable(R.drawable.baseline_favorite_24, null)
                    favorButton.text = (favorButton.text[0].digitToInt()+1).toString()
                }
                else{
                    favorButton.isActivated = false
                    favorButton.icon = resources.getDrawable(R.drawable.baseline_favorite_border_24, null)
                    favorButton.text = (favorButton.text[0].digitToInt()-1).toString()
                }
            }
        }

        binding.toolBar.setOnMenuItemClickListener {

            true
        }

        vm.profile.observe(viewLifecycleOwner){ profile ->
            binding.profileCardLayout.profileNickName.text = profile.username
            binding.profileCardLayout.additionalText.text = "I'm "+ profile.username
        }
    }

}