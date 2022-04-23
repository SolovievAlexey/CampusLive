package ru.campus.live.location.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationModel(
    @SerializedName(value = "id")
    val locationId: Int,
    @SerializedName(value = "name")
    val locationName: String,
    @SerializedName(value = "address")
    val locationAddress: String,
    @SerializedName(value = "type")
    val locationType: Int
) : Parcelable
