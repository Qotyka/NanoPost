package com.example.nanopost.presentation

import android.net.Uri
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.databinding.ImageViewCreatePostBinding
import com.example.nanopost.inflate
import java.io.File

class CreatePostImagesAdapter: ListAdapter<Uri, CreatePostImagesAdapter.ViewHolder>(DiffCallBack) {

    lateinit var imageOnClick: (Uri) -> Unit
    lateinit var cancelOnClick: (Int) -> Unit

    class ViewHolder(
        parent: ViewGroup,
        private val imageOnClickListener: (Uri) -> Unit,
        private val cancelOnClickListener: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.image_view_create_post)
    ) {
        private val binding by viewBinding(ImageViewCreatePostBinding::bind)
        fun bind(image: Uri) {
            binding.image.load(image)
            println(binding.image.drawable.toString())
            binding.image.adjustViewBounds = true
            binding.cancel.setOnClickListener{
                cancelOnClickListener(this.absoluteAdapterPosition)
            }
        }
    }

    object DiffCallBack : DiffUtil.ItemCallback<Uri>() {

        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem.path == newItem.path
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, imageOnClick, cancelOnClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}