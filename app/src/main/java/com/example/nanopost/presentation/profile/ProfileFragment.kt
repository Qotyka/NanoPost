package com.example.nanopost.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentProfileBinding
import com.example.nanopost.presentation.PagingPostAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val vm: ProfileViewModel by viewModels()
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val adapter = PagingPostAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sendedUsername = arguments?.getString("USERNAME")
        val savedUsername = vm.getSavedUsername()
        val username = sendedUsername ?: savedUsername
        if (username != null) {
            vm.getProfile(username)
        }
        binding.recycler.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(binding.appBarLayout) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = insets.top)
            WindowInsetsCompat.CONSUMED
        }

        val imageViews = listOf<ImageView>(
            binding.imagesCardLayout.shapeableImageView1,
            binding.imagesCardLayout.shapeableImageView2,
            binding.imagesCardLayout.shapeableImageView3,
            binding.imagesCardLayout.shapeableImageView4
        )

        adapter.cardOnClick = { post ->
            findNavController().navigate(
                R.id.action_profileFragment2_to_postFragment, bundleOf(
                    "POSTID" to post?.id
                )
            )
        }

        adapter.cardOnLongClick = { post ->
            if(post != null){
                vm.deletePost(post.id)
            }
        }

        vm.profile.observe(viewLifecycleOwner) { profile ->
            binding.apply {
                imagesCardLayout.imagesCard.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_profileFragment2_to_imagesFragment, bundleOf(
                            "PROFILEID" to profile.id
                        )
                    )
                }

                profileCardLayout.profileNickName.text = profile.displayName
                profileCardLayout.additionalText.text = profile.bio
                profileCardLayout.subscribersCount.text = profile.subscribersCount.toString()
                profileCardLayout.postsCount.text = profile.postsCount.toString()
                profileCardLayout.imagesCount.text = profile.imagesCount.toString()

                for (i in 0 until profile.images.size) {
                    imageViews[i].load(profile.images[i].sizes[0].url)
                    imageViews[i].setOnClickListener {
                        findNavController().navigate(
                            R.id.action_profileFragment2_to_imageFragment,
                            bundleOf(
                                "IMAGEURL" to profile.images[i].sizes[0].url,
                                "USERNAME" to profile.displayName,
                                "DATE" to profile.images[i].dateCreated,
                                "AVATARURL" to profile.images[i].owner.avatarUrl,
                            )
                        )
                    }
                }

                if (profile?.avatarUrlLarge != null) {
                    binding.profileCardLayout.profileAvatarImage.load(profile.avatarUrlLarge)
                    binding.profileCardLayout.profileNickNameCharacter.visibility = View.GONE
                } else {
                    binding.profileCardLayout.profileNickNameCharacter.text =
                        binding.profileCardLayout.profileNickName.text[0].uppercase()
                }

                if (profile.subscribed) {
                    binding.profileCardLayout.subscribeButton.visibility = View.GONE
                    binding.profileCardLayout.unsubscribeButton.visibility = View.VISIBLE
                }else{
                    binding.profileCardLayout.unsubscribeButton.visibility = View.GONE
                    binding.profileCardLayout.subscribeButton.visibility = View.VISIBLE
                }
            }

            vm.getProfilePosts(profile.id)

            binding.profileCardLayout.apply{
                if(profile.username != vm.getSavedUsername()){
                    subscribeButton.setOnClickListener {
                            vm.subscribe(profile.username)
                    }
                    unsubscribeButton.setOnClickListener {
                            vm.unsubscribe(profile.username)
                    }
                } else {
                    subscribeButton.text = "Edit"
                    subscribeButton.setBackgroundColor(resources.getColor(R.color.on_secondary_container_day))
                    subscribeButton.setOnClickListener {
                        //
                    }
                }
            }

        }

        vm.posts.observe(viewLifecycleOwner) { posts ->
            lifecycleScope.launch {
                adapter.submitData(posts)
            }
        }

        binding.toolBar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.logout) {
                vm.logout()
                findNavController().navigate(R.id.action_profileFragment2_to_authFragment)
                true
            } else {
                false
            }
        }

        binding.addPostButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment2_to_createPostFragment)
        }

        vm.subscribe.observe(viewLifecycleOwner) { response ->
            if (response) {
                binding.profileCardLayout.subscribeButton.visibility = View.GONE
                binding.profileCardLayout.unsubscribeButton.visibility = View.VISIBLE
                if (username != null) {
                    vm.getProfile(username)
                }
            }
        }

        vm.unsubscribe.observe(viewLifecycleOwner) { response ->
            if (response) {
                binding.profileCardLayout.unsubscribeButton.visibility = View.GONE
                binding.profileCardLayout.subscribeButton.visibility = View.VISIBLE
                if (username != null) {
                    vm.getProfile(username)
                }
            }
        }
    }

}