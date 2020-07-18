package com.platzi.android.rickandmorty.parcelables

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterParcelable(
    val id: Int,
    val name: String,
    val image: String?,
    val gender: String,
    val species: String,
    val status: String,
    val origin: OriginParcelable,
    val location: LocationParcelable,
    val episodeList: List<String>
): Parcelable

@Parcelize
data class OriginParcelable(
    val name: String,
    val url: String
): Parcelable

@Parcelize
data class LocationParcelable(
    val name: String,
    val url: String
): Parcelable
