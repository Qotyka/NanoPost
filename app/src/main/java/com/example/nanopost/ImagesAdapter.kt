package com.example.nanopost

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.data.retrofit.model.ImageData
import com.example.nanopost.databinding.SingleImageBinding

class ImagesAdapter: ListAdapter<ImageData, ImagesAdapter.ViewHolder>(DiffCallBack) {

    class ViewHolder(
        parent: ViewGroup
    ): RecyclerView.ViewHolder(
        parent.inflate(R.layout.single_image)
    ){
        private val binding by viewBinding(SingleImageBinding::bind)
        fun bind(image: ImageData){
        }
    }

    object DiffCallBack: DiffUtil.ItemCallback<ImageData>(){

        override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}