package com.example.nanopost.presentation.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.data.retrofit.model.ImageData
import com.example.nanopost.databinding.FragmentPostBinding
import com.example.nanopost.presentation.ImagesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment(R.layout.fragment_post) {

    private val binding by viewBinding(FragmentPostBinding::bind)
    private val vm: PostViewModel by viewModels()
    private val adapter = ImagesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postId = requireArguments().getString("POSTID")

        ViewCompat.setOnApplyWindowInsetsListener(binding.appBarLayout) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = insets.top)
            WindowInsetsCompat.CONSUMED
        }

        binding.postImageRecycler.adapter = adapter

        adapter.imageOnClick = {    image->
            findNavController().navigate(R.id.action_postFragment_to_imageFragment,
            bundleOf(
                "IMAGEURL" to image.sizes[0].url,
                "USERNAME" to image.owner.displayName,
                "DATE" to image.dateCreated,
                "AVATARURL" to image.owner.avatarUrl,
            )
            )
        }
        binding.favorButton.setOnClickListener {
            if(binding.favorButton.isActivated){
                binding.favorButton.setIconResource(R.drawable.baseline_favorite_border_24)
                binding.favorButton.text = "0"
                binding.favorButton.isActivated = false
            } else{
                binding.favorButton.setIconResource(R.drawable.baseline_favorite_24)
                binding.favorButton.text = "1"
                binding.favorButton.isActivated = true
            }
        }

        if(postId != null){
            vm.getPost(postId)
        }

        binding.toolBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        vm.post.observe(viewLifecycleOwner){    post->
            binding.apply {
                profileAvatarImage.load(post.avatarUrl)
                profileNickName.text = post.sender
                additionalText.text = post.dateCreated.toString()
                if(post.text != null){
                    postSupportingText.visibility = View.VISIBLE
                    postSupportingText.text = post.text
                }
                adapter.submitList(post.images)
            }
        }

    }

}