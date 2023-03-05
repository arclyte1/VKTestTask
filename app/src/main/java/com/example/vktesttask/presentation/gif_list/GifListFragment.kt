package com.example.vktesttask.presentation.gif_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.example.vktesttask.R
import com.example.vktesttask.databinding.FragmentGifListBinding
import com.example.vktesttask.domain.model.Gif
import com.example.vktesttask.presentation.SharedObjects
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GifListFragment : Fragment(R.layout.fragment_gif_list) {

    private lateinit var binding: FragmentGifListBinding
    private val viewModel: GifListViewModel by viewModels()
    private val adapter = GifListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGifListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: relative span count
        binding.gifList.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.gifList.adapter = adapter
        postponeEnterTransition()
        binding.gifList.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
        adapter.addOnPagesUpdatedListener {
            Log.d("Fragment", adapter.itemCount.toString())
            binding.nothingFoundImage.isVisible = adapter.itemCount == 0
            binding.nothingFoundText.isVisible = adapter.itemCount == 0
            if (adapter.itemCount == 0) {
                Glide.with(this@GifListFragment)
                    .asGif()
                    .load(R.drawable.nothing_found_lens)
                    .into(binding.nothingFoundImage)
            }
        }
        adapter.itemClickListener = { gif, imageView, gifDrawable ->
            navigateToDetails(gif, imageView, gifDrawable)
        }

        lifecycleScope.launch {
            viewModel.gifFlowFlow.collectLatest { gifFlow ->
                gifFlow.collectLatest { data ->
                    adapter.submitData(data)
                }
            }
        }

        binding.searchEditText.addTextChangedListener { text ->
            viewModel.searchTextChanged(text.toString())
        }
    }

    private fun navigateToDetails(gif: Gif, imageView: ImageView, gifDrawable: GifDrawable?) {
        SharedObjects.selectedGifDrawable = gifDrawable
        val directions = GifListFragmentDirections.actionGifListFragmentToGifDetailsFragment(
            gif = gif,
            downsizedBitmap = imageView.drawToBitmap(),
        )
        Log.d("FragmentList", imageView.transitionName)
        findNavController().navigate(
            directions = directions,
            navigatorExtras = FragmentNavigatorExtras(imageView to imageView.transitionName)
        )
    }
}