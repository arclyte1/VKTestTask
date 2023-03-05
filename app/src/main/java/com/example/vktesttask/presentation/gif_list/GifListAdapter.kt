package com.example.vktesttask.presentation.gif_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.vktesttask.databinding.GifListItemBinding
import com.example.vktesttask.domain.model.Gif
import com.example.vktesttask.presentation.SharedObjects
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class GifListAdapter : PagingDataAdapter<Gif, GifListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Gif>() {

        override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
            return oldItem == newItem
        }
    }
) {

    var itemClickListener: (Gif, ImageView, GifDrawable?) -> Unit = { _, _, _ -> }

    inner class ViewHolder(
        private val binding: GifListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Gif) {
            binding.imageView.transitionName = "gif${item.id}"

            val shimmerDrawable = ShimmerDrawable()
            shimmerDrawable.setShimmer(
                Shimmer.AlphaHighlightBuilder()
                    .setDuration(1800)
                    .setBaseAlpha(0.7f)
                    .setHighlightAlpha(0.6f)
                    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                    .setAutoStart(true)
                    .build()
            )

            Glide.with(binding.root.context)
                .asGif()
                .load(item.downsizedImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(shimmerDrawable)
                .listener(object : RequestListener<GifDrawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: GifDrawable?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.root.setOnClickListener {
                            itemClickListener(item, binding.imageView, resource)
                        }
                        return false
                    }

                })
                .into(binding.imageView)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            GifListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}