package com.example.vktesttask.presentation.gif_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.vktesttask.R
import com.example.vktesttask.databinding.FragmentGifListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifListFragment : Fragment(R.layout.fragment_gif_list) {

    private lateinit var binding: FragmentGifListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentGifListBinding.bind(view)

        binding.gifList.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

    }
}