package com.example.nanopost.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.data.retrofit.model.Profile
import com.example.nanopost.databinding.FragmentProfileBinding
import com.example.nanopost.presentation.PagingPostAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Duration

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val vm: ProfileViewModel by viewModels()
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val adapter = PagingPostAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val username = requireArguments().getString("USERNAME")
        val username = "evo"
        vm.getProfile(username)
//        if (username != null) {
//            vm.getProfile(username)
//        } else {
//            vm.getProfile("evo")
//        }

        adapter.cardOnClick = { post ->
            findNavController().navigate(R.id.action_profileFragment2_to_postFragment, bundleOf(
                "POSTID" to post?.id
            ))
        }

        binding.recycler.adapter = adapter

        vm.profile.observe(viewLifecycleOwner){ profile ->
            binding.apply {
                val imageViews = listOf<ImageView>(
                    imagesCardLayout.shapeableImageView1,
                    imagesCardLayout.shapeableImageView2,
                    imagesCardLayout.shapeableImageView3,
                    imagesCardLayout.shapeableImageView4
                )

                imagesCardLayout.imagesCard.setOnClickListener {
                    findNavController().navigate(R.id.action_profileFragment2_to_imagesFragment, bundleOf(
                        "PROFILEID" to profile.id
                    )
                    )
                }

                profileCardLayout.profileNickName.text = profile.displayName
                profileCardLayout.additionalText.text = profile.bio
                profileCardLayout.subscribersCount.text = profile.subscribersCount.toString()
                profileCardLayout.postsCount.text = profile.postsCount.toString()
                profileCardLayout.imagesCount.text = profile.imagesCount.toString()

                for(i in 0 until profile.images.size){
                    imageViews[i].load(profile.images[i].sizes[0].url)
                    imageViews[i].setOnClickListener {
                        findNavController().navigate(R.id.action_profileFragment2_to_imageFragment,
                            bundleOf(
                                "IMAGEURL" to profile.images[i].sizes[0].url,
                                "USERNAME" to profile.displayName,
                                "DATE" to profile.images[i].dateCreated,
                                "AVATARURL" to profile.images[i].owner.avatarUrl,
                            ))
                    }
                }

                if(profile.avatarUrlLarge!=null){
                    binding.profileCardLayout.profileAvatarImage.load(profile.avatarUrlLarge)
                    binding.profileCardLayout.profileNickNameCharacter.visibility = View.GONE
                } else {
                    binding.profileCardLayout.profileNickNameCharacter.text =
                        binding.profileCardLayout.profileNickName.text[0].toString()
                }

                binding.profileCardLayout.profileAvatarImage.load(profile.avatarUrlLarge)

                if(profile.subscribed){
                    binding.profileCardLayout.subscribeButton.visibility = View.GONE
                    binding.profileCardLayout.unsubscribeButton.visibility = View.VISIBLE
                }
            }

            vm.getProfilePosts(profile.id)

        }

        vm.posts.observe(viewLifecycleOwner){   posts ->
            lifecycleScope.launch {
                adapter.submitData(posts)
            }
        }

        with(binding.profileCardLayout) {
            subscribeButton.setOnClickListener {
                if (username != null) {
                    vm.subscribe(username)
                }
            }
            unsubscribeButton.setOnClickListener {
                if (username != null) {
                    vm.unsubscribe(username)
                }
            }
        }

        binding.toolBar.setOnMenuItemClickListener { item->
            println("setOnMenuItemClickListener")
            if(item.itemId == R.id.logout){
                vm.logout()
                findNavController().navigate(R.id.action_profileFragment2_to_authFragment)
                true
            } else {
                false
            }
        }

        vm.subscribe.observe(viewLifecycleOwner){   response->
            if(response){
                binding.profileCardLayout.subscribeButton.visibility = View.GONE
                binding.profileCardLayout.unsubscribeButton.visibility = View.VISIBLE
                vm.getProfile(username)
            }
        }

        vm.unsubscribe.observe(viewLifecycleOwner){   response->
            if(response){
                binding.profileCardLayout.unsubscribeButton.visibility = View.GONE
                binding.profileCardLayout.subscribeButton.visibility = View.VISIBLE
                vm.getProfile(username)
            }
        }
    }

}