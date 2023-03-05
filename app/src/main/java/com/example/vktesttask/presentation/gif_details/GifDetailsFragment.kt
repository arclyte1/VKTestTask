package com.example.vktesttask.presentation.gif_details

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.vktesttask.R
import com.example.vktesttask.databinding.FragmentGifDetailsBinding
import com.example.vktesttask.domain.model.Gif
import com.example.vktesttask.presentation.SharedObjects
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class GifDetailsFragment : Fragment(R.layout.fragment_gif_details) {

    private val args by navArgs<GifDetailsFragmentArgs>()
    private lateinit var binding: FragmentGifDetailsBinding
    private val setBackgroundColorRequestListener = object : RequestListener<GifDrawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<GifDrawable>?,
            isFirstResource: Boolean
        ): Boolean {
            binding.root.setBackgroundColor(Color.WHITE)
            return false
        }

        override fun onResourceReady(
            resource: GifDrawable?,
            model: Any?,
            target: Target<GifDrawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            resource?.toBitmap()?.let { bitmap ->
                setBackgroundColorFromBitmap(bitmap)
            }
            return false
        }
    }

    private fun setBackgroundColorFromBitmap(bitmap: Bitmap) {
        Palette.from(bitmap).generate { palette ->
            binding.root.setBackgroundColor(
                palette?.getDominantColor(Color.WHITE) ?: Color.WHITE
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGifDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gif.transitionName = "gif${args.gif.id}"

        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation

        setContent(args.gif, SharedObjects.selectedGifDrawable)

        binding.share.setOnClickListener { share(args.gif.url) }
    }

    private fun share(url: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent, "Share gif")
        startActivity(shareIntent)
    }

    private fun setContent(gif: Gif, previewDrawable: GifDrawable?) {
        if (gif.user != null) {
            binding.name.text = gif.user.displayName
            binding.username.text = getString(R.string.username, gif.user.username)
            Glide.with(this)
                .asGif()
                .placeholder(R.drawable.profile_avatar_placeholder)
                .load(gif.user.avatarUrl)
                .circleCrop()
                .into(binding.authorAvatar)
            binding.authorAvatar.isVisible = true
            binding.name.isVisible = true
            binding.username.isVisible = true
        } else {
            binding.authorAvatar.isVisible = false
            binding.name.isVisible = false
            binding.username.isVisible = false
        }

        gif.title?.let { title ->
            binding.title.text = title
        }

        previewDrawable?.let { gifDrawable ->
            setBackgroundColorFromBitmap(gifDrawable.toBitmap())
        }

        Glide.with(this@GifDetailsFragment)
            .asGif()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(previewDrawable)
            .load(gif.originalImageUrl)
            .listener(setBackgroundColorRequestListener)
            .into(binding.gif)
    }
}