package com.example.nanopost.presentation

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.data.retrofit.model.ImageData
import com.example.nanopost.data.retrofit.model.Post
import com.example.nanopost.databinding.ImageViewBinding
import com.example.nanopost.databinding.PostCardBinding
import com.example.nanopost.inflate

class PagingImagesAdapter: PagingDataAdapter<ImageData, PagingImagesAdapter.ViewHolder>(DiffCallBack) {

    lateinit var imageOnClick: (ImageData?) -> Unit

    class ViewHolder(
        parent: ViewGroup,
        private val imageOnClickListener: (ImageData?) -> Unit,
    ) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.image_view)
    ) {
        private val binding by viewBinding(ImageViewBinding::bind)
        fun bind(image: ImageData?) {
            if(image != null && image.sizes.isNotEmpty()){
                binding.image.load(image.sizes[0].url)
                binding.image.adjustViewBounds = true
                binding.image.setOnClickListener {
                    imageOnClickListener(image)
                }
            }
        }
    }

    object DiffCallBack : DiffUtil.ItemCallback<ImageData>() {

        override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, imageOnClick)
    }

}