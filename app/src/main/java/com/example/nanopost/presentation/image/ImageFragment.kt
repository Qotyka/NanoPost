package com.example.nanopost.presentation.image

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentImageBinding
import com.example.nanopost.databinding.FragmentImagesBinding
import com.example.nanopost.presentation.PagingImagesAdapter
import com.example.nanopost.presentation.images.ImagesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageFragment : Fragment(R.layout.fragment_image) {

    private val binding by viewBinding(FragmentImageBinding::bind)
    private val vm: ImageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUrl = requireArguments().getString("IMAGEURL")
        val dateCreated = requireArguments().getString("DATE")
        val username = requireArguments().getString("USERNAME")
        val avatarUrl = requireArguments().getString("AVATARURL")

        binding.apply {
            toolBar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            profileLayout.profileAvatarImage.load(avatarUrl)
            profileLayout.additionalText.text = dateCreated
            profileLayout.profileNickName.text = username
            imageView.load(imageUrl)
        }
    }
}