package com.example.nanopost.presentation

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.data.retrofit.model.MiniProfile
import com.example.nanopost.databinding.ProfileViewBinding
import com.example.nanopost.inflate

class PagingSearchAdapter: PagingDataAdapter<MiniProfile, PagingSearchAdapter.ViewHolder>(DiffCallBack) {

    lateinit var profileOnClick: (MiniProfile?) -> Unit

    class ViewHolder(
        parent: ViewGroup,
        private val profileOnClickListener: (MiniProfile?) -> Unit,
    ) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.profile_view)
    ) {
        private val binding by viewBinding(ProfileViewBinding::bind)
        fun bind(profile: MiniProfile?) {
            binding.apply {
                if(profile?.avatarUrl != null){
                    profileAvatarImage.load(profile.avatarUrl)
                    profileNickNameCharacter.visibility = View.GONE
                } else {
                    profileNickNameCharacter.text = profile?.displayName.toString()[0].toString().uppercase()
                }
                profileCard.setOnClickListener {
                    profileOnClickListener(profile)
                }
                profileNickName.text = profile?.displayName
                additionalText.visibility = View.GONE
            }
        }
    }

    object DiffCallBack : DiffUtil.ItemCallback<MiniProfile>() {

        override fun areItemsTheSame(oldItem: MiniProfile, newItem: MiniProfile): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MiniProfile, newItem: MiniProfile): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, profileOnClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}