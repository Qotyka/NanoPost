package com.example.nanopost.presentation.images

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentImageBinding
import com.example.nanopost.databinding.FragmentImagesBinding
import com.example.nanopost.presentation.PagingImagesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImagesFragment : Fragment(R.layout.fragment_images) {

    private val binding by viewBinding(FragmentImagesBinding::bind)
    private val vm: ImagesViewModel by viewModels()
    private val adapter = PagingImagesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileId = requireArguments().getString("PROFILEID")
        if(profileId != null){
            vm.getImages(profileId)
        }
        binding.imageRecycler.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(binding.appBarLayout) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = insets.top)
            WindowInsetsCompat.CONSUMED
        }

        binding.toolBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        adapter.imageOnClick = {image->
            if(image != null && image.sizes.isNotEmpty()){
                findNavController().navigate(R.id.action_imagesFragment_to_imageFragment, bundleOf(
                    "IMAGEURL" to image.sizes[0].url,
                    "USERNAME" to image.owner.displayName,
                    "DATE" to image.dateCreated,
                    "AVATARURL" to image.owner.avatarUrl,
                ))
            }
        }

        vm.images.observe(viewLifecycleOwner){  images->
            lifecycleScope.launch {
                adapter.submitData(images)
            }
        }

    }
}