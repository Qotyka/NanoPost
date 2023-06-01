package com.example.nanopost.presentation.create_post

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.PickerMediaColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.core.graphics.drawable.toIcon
import androidx.core.net.toFile
import androidx.core.os.bundleOf
import androidx.core.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.Coil
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import coil.util.CoilUtils
import com.example.nanopost.R
import com.example.nanopost.data.retrofit.model.CreatePostData
import com.example.nanopost.databinding.FragmentCreatePostBinding
import com.example.nanopost.presentation.CreatePostImagesAdapter
import com.example.nanopost.presentation.ImagesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okio.use
import java.io.File

@AndroidEntryPoint
class CreatePostFragment : Fragment(R.layout.fragment_create_post) {

    private val binding by viewBinding(FragmentCreatePostBinding::bind)
    private val vm: CreatePostViewModel by viewModels()
    //another adapter
    private val adapter = CreatePostImagesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagesUris = mutableListOf<Uri>()
        val images = mutableListOf<Drawable>()

        ViewCompat.setOnApplyWindowInsetsListener(binding.appBarLayout) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = insets.top)
            WindowInsetsCompat.CONSUMED
        }

        binding.apply {
            imageRecycler.adapter = adapter

            adapter.imageOnClick = {
                println(it)
            }

            adapter.cancelOnClick = {   position ->
                val uris = adapter.currentList.toMutableList()
                uris.removeAt(position)
                vm.getImages(uris)
            }

            val pickMultipleMedia =
                registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(4)){ uris ->
                    if (uris.isNotEmpty()) {
                        Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
                        vm.getImages(uris)
                    } else {
                        Log.d("PhotoPicker", "No media selected")
                    }
                }

            vm.images.observe(viewLifecycleOwner){  uris->
                adapter.submitList(uris)
                imagesUris.clear()
                imagesUris.addAll(uris)
                lifecycleScope.launch {
                    for(uri in imagesUris){
                        requireContext().imageLoader.execute(
                            ImageRequest.Builder(requireContext())
                                .allowConversionToBitmap(true)
                                .allowHardware(true)
                                .data(uri)
                                .build()).drawable?.let { image->
                                    images.add(image)
                        }
                    }
                }
            }

            toolBar.setOnMenuItemClickListener { item->
                if(item.itemId == R.id.confirm){
                    vm.createPost(CreatePostData(
                        text = binding.textInput.text?.toString(),
                        images.map {
                            it.toBitmap()
                        }
                    )
                    )
                    true
                } else {
                    findNavController().navigateUp()
                }
            }

            vm.post.observe(viewLifecycleOwner){    post->
                findNavController().navigateUp()
            }

            addImagesButton.setOnClickListener {
                pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }

    }

}