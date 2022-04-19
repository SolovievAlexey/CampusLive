package ru.campus.live.location.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationModel(
    val id: Int,
    val name: String,
    val address: String,
    val type: Int
) : Parcelable
