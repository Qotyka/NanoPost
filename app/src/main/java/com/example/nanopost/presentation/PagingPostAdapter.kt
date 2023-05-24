package com.example.nanopost.presentation

import android.view.View
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

class PagingPostAdapter: PagingDataAdapter<Post, PagingPostAdapter.ViewHolder>(DiffCallBack) {

    lateinit var cardOnClick: (Post?) -> Unit

    class ViewHolder(
        parent: ViewGroup,
        private val cardOnClickListener: (Post?) -> Unit,
    ) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.post_card)
    ) {
        private val binding by viewBinding(PostCardBinding::bind)
        fun bind(post: Post?) {
            binding.apply {
                if (post !== null && post.images.isNotEmpty()) {
                    postImage.load(post.images[0].sizes[0].url)
                }
                profileNickName.text = post?.sender
                additionalText.text = post?.dateCreated.toString()
                if (post?.text !== null) {
                    postSupportingText.visibility = View.VISIBLE
                    postSupportingText.text = post.text
                }
                profileAvatarImage.load(post?.avatarUrl)
                favorButton.text = post?.likesCount.toString()
                postCard.setOnClickListener {
                    cardOnClickListener(post)
                }
                favorButton.setOnClickListener {
                    if(favorButton.isActivated){
                        favorButton.setIconResource(R.drawable.baseline_favorite_border_24)
                        favorButton.text = "0"
                        favorButton.isActivated = false
                    } else{
                        favorButton.setIconResource(R.drawable.baseline_favorite_24)
                        favorButton.text = "1"
                        favorButton.isActivated = true
                    }
                }
            }
        }
    }

        object DiffCallBack : DiffUtil.ItemCallback<Post>() {

            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(parent, cardOnClick)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(getItem(position))
        }
}