package com.mullipr.festie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mullipr.festie.databinding.ArtistCardBinding
import com.mullipr.festie.model.Artist

class ArtistAdapter(var list : List<Artist>,
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
            if(artist.imageDrawable != null){
                item.artistImage.setImageDrawable(artist.imageDrawable)
            }

            item.artistCheckbox.isChecked = artist.isSelected
            item.artistName.text = artist.name
        }
    }
}