package com.mullipr.festie.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mullipr.festie.databinding.ArtistCardBinding
import com.mullipr.festie.model.Artist
import java.util.*

class ArtistAdapter(val ctx : Context,
                    var list : List<Artist>,
                    val listener : (Artist) -> Unit) : RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ArtistCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(
            item
        )
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private var item : ArtistCardBinding) : RecyclerView.ViewHolder(item.root){
        fun bind(artist: Artist){
            if(artist.image?.url != null){
                Glide.with(ctx).load(artist.image.url).into(item.artistImage)
            }else {
                val r = Random()
                val drawable = ColorDrawable(Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256)))
                Glide.with(ctx).load(drawable).into(item.artistImage)
            }

            item.artistCheckbox.isChecked = artist.isSelected
            item.artistName.text = artist.name
        }
    }
}