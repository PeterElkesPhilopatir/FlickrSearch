package com.peter.flickrsearch.framework.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.RequestConfiguration
import com.peter.flickrsearch.R
import com.peter.flickrsearch.business.models.Photo
import com.peter.flickrsearch.databinding.PhotoItemBinding
import java.util.*


private const val ITEM_TYPE_PHOTO = 0
private const val ITEM_TYPE_BANNER_AD = 1
private const val LIST_AD_DELTA = 5

class PhotosAdapter(val onClickListener: OnClickListener) :
    PagingDataAdapter<Photo, PhotosViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):

            PhotosViewHolder {


        return PhotosViewHolder(
            PhotoItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )

    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> onClickListener.onClick(it1) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position > 0 && position % LIST_AD_DELTA == 0) {
            ITEM_TYPE_BANNER_AD
        } else ITEM_TYPE_PHOTO
    }
}

class PhotosViewHolder(private var binding: PhotoItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(photo: Photo) {
        binding.data = photo
        binding.executePendingBindings()
        if(itemViewType == ITEM_TYPE_BANNER_AD){
            val adRequest = AdRequest.Builder().build()
            binding.adView.loadAd(adRequest)
            binding.adView.visibility = View.VISIBLE
        }
    }
}

class OnClickListener(val clickListener: (photo: Photo) -> Unit) {
    fun onClick(photo: Photo) = clickListener(photo)
}