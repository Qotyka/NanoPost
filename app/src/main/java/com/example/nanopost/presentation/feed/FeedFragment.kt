package com.example.nanopost.presentation.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentFeedBinding
import com.example.nanopost.presentation.PagingPostAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : Fragment(R.layout.fragment_feed) {

    private val binding by viewBinding(FragmentFeedBinding::bind)
    private val adapter = PagingPostAdapter()
    private val vm: FeedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getFeed()

        adapter.cardOnClick = { post ->
            findNavController().navigate(R.id.action_profileFragment2_to_postFragment, bundleOf(
                "POSTID" to post?.id
            )
            )
        }

        binding.recycler.adapter = adapter

        vm.posts.observe(viewLifecycleOwner){   posts ->
            lifecycleScope.launch {
                adapter.submitData(posts)
            }
        }

        binding.toolBar.setOnMenuItemClickListener { item->
            if(item.itemId == R.id.search){
                findNavController().navigate(R.id.action_feedFragment_to_searchFragment)
                true
            } else {
                false
            }
        }

    }
}