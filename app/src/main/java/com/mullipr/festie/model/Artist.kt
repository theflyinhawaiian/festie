package com.mullipr.festie.model

import android.os.Parcel
import android.os.Parcelable

data class Artist(val genres : List<String>,
                  val id : String,
                  val name : String,
                  val popularity : Int,
                  val image : String,
                  var isSelected : Boolean = false) : Parcelable {
    val imageColor : String?

    constructor(parcel: Parcel) : this(
        parcel.createStringArrayList() ?: listOf(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
    parcel.readByte() != 0.toByte())

    init {
        val hash = name.hashCode()
        var color = "#"
        for (i in 0..2) {
            val value = Integer.toHexString((hash shr (i * 8)) and 0xFF)
            if(value.length < 2)
                color += "0"
            color += value
        }

        imageColor = color
    }

    fun hasImage() : Boolean = image.isNotEmpty()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(genres)
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(popularity)
        parcel.writeString(image)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Artist> {
        override fun createFromParcel(parcel: Parcel): Artist {
            return Artist(parcel)
        }

        override fun newArray(size: Int): Array<Artist?> {
            return arrayOfNulls(size)
        }
    }
}