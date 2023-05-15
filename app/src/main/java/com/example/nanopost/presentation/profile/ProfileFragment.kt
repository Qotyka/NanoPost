package com.example.nanopost.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.data.retrofit.model.Profile
import com.example.nanopost.databinding.FragmentProfileBinding
import com.example.nanopost.presentation.PagingPostAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val vm: ProfileViewModel by viewModels()
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val adapter = PagingPostAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.cardOnClick = {post ->
            println(post.toString())
        }
        adapter.favorOnClick = {post ->
            println(post.toString())
        }
        binding.recycler.adapter = adapter
        vm.getProfile("evo")
        vm.profile.observe(viewLifecycleOwner){ profile ->
            binding.apply {
                profileCardLayout.profileNickName.text = profile.displayName
                profileCardLayout.additionalText.text = profile.bio
                profileCardLayout.subscribersCount.text = profile.subscribersCount.toString()
                profileCardLayout.postsCount.text = profile.postsCount.toString()
                profileCardLayout.imagesCount.text = profile.imagesCount.toString()
                imagesCardLayout.shapeableImageView1.load(profile.images[0].sizes[0].url)
                imagesCardLayout.shapeableImageView2.load(profile.images[1].sizes[0].url)
                imagesCardLayout.shapeableImageView3.load(profile.images[2].sizes[0].url)
                imagesCardLayout.shapeableImageView4.load(profile.images[3].sizes[0].url)
            }

            if(profile.avatar !== null){
                vm.getAvatar(
                    profile.avatar
                )
            }

            vm.getProfilePosts(profileId = profile.id)

        }

        vm.posts.observe(viewLifecycleOwner){posts ->
            lifecycleScope.launch {
                adapter.submitData(posts)
            }
        }

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

        binding.toolBar.setOnMenuItemClickListener {

            true
        }
        vm.avatar.observe(viewLifecycleOwner){ avatar ->
            binding.apply {
                profileCardLayout.profileAvatarImage.load(avatar.sizes[0].url)
            }
        }
    }

}