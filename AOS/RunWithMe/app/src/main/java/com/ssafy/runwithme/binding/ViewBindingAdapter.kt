package com.ssafy.runwithme.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ssafy.runwithme.R

object ViewBindingAdapter {

    @BindingAdapter("startTime", "endTime")
    @JvmStatic
    fun TextView.setStartTime(timeStart: String, timeEnd: String){
        this.text = "$timeStart ~ $timeEnd"
    }

    @BindingAdapter("crewHorizonImage")
    @JvmStatic
    fun ImageView.setSearchImage (imageUrl: Any){
        Glide.with(this.context)
            .load(imageUrl)
            .override(80,80)
            .placeholder(R.drawable.img)
            .into(this)
        this.clipToOutline = true
    }
}