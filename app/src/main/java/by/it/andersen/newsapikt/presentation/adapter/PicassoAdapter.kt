package by.it.andersen.newsapikt.presentation.adapter

import android.R
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object PicassoAdapter {
    @BindingAdapter("picasso")
    fun setImage(image: ImageView, resource: String?) {
        val context = image.context
        if (resource == null || resource.isEmpty()) {
            Picasso.get().load(R.drawable.gallery_thumb).into(image)
        } else {
            Picasso.get().load(resource).into(image)
        }
    }
}