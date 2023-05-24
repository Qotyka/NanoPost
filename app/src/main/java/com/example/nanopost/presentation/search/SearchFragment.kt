package com.example.nanopost.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentProfileBinding
import com.example.nanopost.databinding.FragmentSearchBinding
import com.example.nanopost.presentation.PagingPostAdapter
import com.example.nanopost.presentation.PagingSearchAdapter
import com.example.nanopost.presentation.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val vm: SearchViewModel by viewModels()
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val adapter = PagingSearchAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchContentView.adapter = adapter

        adapter.profileOnClick = {  profile ->
            findNavController().navigate(R.id.action_searchFragment_to_profileFragment2, bundleOf(
                "USERNAME" to profile?.username
            ))
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(text: String?): Boolean {
                if(!text.isNullOrEmpty()){
                    vm.getProfiles(text)
                }
                return true
            }
            override fun onQueryTextChange(text: String?): Boolean {
                if(!text.isNullOrEmpty()){
                    vm.getProfiles(text)
                }
                return true
            }
        })

        vm.profiles.observe(viewLifecycleOwner) { profileList ->
            lifecycleScope.launch {
                adapter.submitData(profileList)
            }
        }

    }

}