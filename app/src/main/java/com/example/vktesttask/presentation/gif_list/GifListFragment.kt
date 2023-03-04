package com.example.vktesttask.presentation.gif_list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.vktesttask.R
import com.example.vktesttask.databinding.FragmentGifListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GifListFragment : Fragment(R.layout.fragment_gif_list) {

    private lateinit var binding: FragmentGifListBinding
    private val viewModel: GifListViewModel by viewModels()
    private val adapter = GifListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentGifListBinding.bind(view)

        binding.gifList.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.gifList.adapter = adapter

        lifecycleScope.launch {
            viewModel.gifFlow.collectLatest { data ->
                data.map {
                    Log.d("Item", it.url)
                    it
                }
                adapter.submitData(data)
            }
        }
    }
}