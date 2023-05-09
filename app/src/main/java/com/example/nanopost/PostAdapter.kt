package com.example.nanopost

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.data.retrofit.model.Post
import com.example.nanopost.databinding.PostCardBinding

class PostAdapter: ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallBack) {

    class ViewHolder(
        parent: ViewGroup
    ): RecyclerView.ViewHolder(
        parent.inflate(R.layout.post_card)
    ){
        private val binding by viewBinding(PostCardBinding::bind)
        fun bind(post: Post){
        }
    }

    object DiffCallBack: DiffUtil.ItemCallback<Post>(){

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}