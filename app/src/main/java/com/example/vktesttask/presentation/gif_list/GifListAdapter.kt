package com.example.vktesttask.presentation.gif_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.vktesttask.databinding.GifListItemBinding
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class GifListAdapter : PagingDataAdapter<GifListItemVo, GifListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<GifListItemVo>() {

        override fun areItemsTheSame(oldItem: GifListItemVo, newItem: GifListItemVo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GifListItemVo, newItem: GifListItemVo): Boolean {
            return oldItem == newItem
        }
    }
) {

    inner class ViewHolder(
        private val binding: GifListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GifListItemVo) {
            val shimmerDrawable = ShimmerDrawable()
            val width = binding.root.width
            shimmerDrawable.setShimmer(
                Shimmer.AlphaHighlightBuilder()
                    .setDuration(1800)
                    .setFixedWidth(width)
                    .setHeightRatio(item.heightRatio.toFloat()) // TODO: not working(( will try something else later
                    .setBaseAlpha(0.7f)
                    .setHighlightAlpha(0.6f)
                    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                    .setAutoStart(true)
                    .build()
            )

            Glide.with(binding.root.context)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(shimmerDrawable)
                .load(item.url)
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