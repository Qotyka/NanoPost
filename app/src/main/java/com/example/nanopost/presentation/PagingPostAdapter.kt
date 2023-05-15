package com.example.nanopost.presentation

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.PostAdapter
import com.example.nanopost.R
import com.example.nanopost.data.retrofit.model.Post
import com.example.nanopost.databinding.PostCardBinding
import com.example.nanopost.inflate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class PagingPostAdapter: PagingDataAdapter<Post, PagingPostAdapter.ViewHolder>(PostAdapter.DiffCallBack) {
    lateinit var onClick: (Post?) -> Unit
    class ViewHolder(
        parent: ViewGroup,
        private val onClickListener: (Post?) -> Unit
    ): RecyclerView.ViewHolder(
        parent.inflate(R.layout.post_card)
    ){
        private val binding by viewBinding(PostCardBinding::bind)
        fun bind(post: Post?){
            binding.apply {
                //does not work
//                if(post !== null){
//                    postImage.load(post.images[0].sizes[0].url)
//                }
                profileNickName.text = post?.sender
                additionalText.text = post?.dateCreated.toString()
                postSupportingText.text = post?.text
                postCard.setOnClickListener{
                    onClickListener(post)
                }
            }
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
        return ViewHolder(parent, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}