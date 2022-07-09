package com.mullipr.festie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mullipr.festie.databinding.ArtistCardBinding
import com.mullipr.festie.model.Artist
import com.mullipr.festie.util.DrawableUtils

class ArtistAdapter(var list : List<Artist>) : RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {
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
        holder.bind(
            list[position]
        )
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private var item : ArtistCardBinding) : RecyclerView.ViewHolder(item.root){
        fun bind(artist: Artist){
            if(artist.image != null)
                item.artistImage.setImageDrawable(DrawableUtils.fromURL(artist.image.url))

            item.artistName.text = artist.name
        }
    }
}