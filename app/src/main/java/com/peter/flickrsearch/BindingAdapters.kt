package com.peter.awesomenews

import android.content.ActivityNotFoundException
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.peter.flickrsearch.R
import com.peter.flickrsearch.business.models.ApiStatus
import com.peter.flickrsearch.business.models.Photo
import com.peter.flickrsearch.framework.ui.search.PhotosAdapter


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    try {
        imgView.clipToOutline = true
        var imgUri = imgUrl!!.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.mipmap.ic_launcher)
            )
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgView)

    } catch (e: Exception) {
        Log.e("BindingAdapter", e.message.toString())
        imgView.setImageResource(R.mipmap.ic_launcher)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Photo>?) {
//    val adapter = recyclerView.adapter as PhotosAdapter
//    adapter.submit
}


@BindingAdapter("apiStatus")
fun bindStatus(statusView: LottieAnimationView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusView.visibility = View.VISIBLE
            statusView.setAnimation(R.raw.loading)
            statusView.playAnimation()
        }
        ApiStatus.ERROR -> {
            statusView.visibility = View.VISIBLE
            statusView.setAnimation(R.raw.error)
            statusView.playAnimation()
        }
        ApiStatus.DONE -> {
            statusView.visibility = View.GONE
            statusView.cancelAnimation()
        }
        ApiStatus.EMPTY -> {
            statusView.visibility = View.VISIBLE
            statusView.setAnimation(R.raw.empty)
            statusView.playAnimation()
        }

    }
}

