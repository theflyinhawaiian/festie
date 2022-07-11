package com.mullipr.festie.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mullipr.festie.databinding.ArtistCardBinding
import com.mullipr.festie.model.Artist
import java.util.*

class ArtistAdapter(
    val ctx: Context,
    var list: List<Artist>,
    val listener: (Artist) -> Unit,
) : RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {
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
                val hash = artist.name.hashCode()
                var color = "#"
                for (i in 0..2) {
                    val value = Integer.toHexString((hash shr (i * 8)) and 0xFF)
                    if(value.length < 2)
                        color += "0"
                    color += value
                }
                Log.d("festie", "hashed artist: ${artist.name}, hash code: $hash, color code: $color")
                val drawable = ColorDrawable(Color.parseColor(color))
                Glide.with(ctx).load(drawable).into(item.artistImage)
            }

            item.artistCheckbox.isChecked = artist.isSelected
            item.artistName.text = artist.name
        }
    }
}