package com.example.pokedex

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
data class PokedexListEntry(var pokemonName:String,
                            var number:Int,
                            var imageUrl:String,

                            ): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readInt()!!,
            parcel.readString()!!,
    )
    {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pokemonName)
        parcel.writeInt(number)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokedexListEntry> {
        override fun createFromParcel(parcel: Parcel): PokedexListEntry {
            return PokedexListEntry(parcel)
        }

        override fun newArray(size: Int): Array<PokedexListEntry?> {
            return arrayOfNulls(size)
        }
    }

}