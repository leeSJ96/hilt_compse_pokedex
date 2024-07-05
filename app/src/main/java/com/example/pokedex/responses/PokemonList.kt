package com.plcoding.jetpackcomposepokedex.data.remote.responses

import com.google.gson.annotations.SerializedName


data class PokemonList(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<Result>
)

//    : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readInt(),
//        parcel.readString(),
//        parcel.readValue(Result::class.java.classLoader),
//        parcel.readArrayList(Result::class.java.classLoader) as ArrayList<Result>,
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(pokemonName)
//        parcel.writeInt(count)
//        parcel.writeString(next)
//        parcel.writeValue(previous)
//
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<PokeData> {
//        override fun createFromParcel(parcel: Parcel): PokeData {
//            return PokeData(parcel)
//        }
//
//        override fun newArray(size: Int): Array<PokeData?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//}