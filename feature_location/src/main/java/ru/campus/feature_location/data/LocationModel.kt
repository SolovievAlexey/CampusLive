package ru.campus.feature_location.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 16:41
 */

@Parcelize
data class LocationModel(
    @SerializedName(value = "id")
    val locationId: Int,
    @SerializedName(value = "name")
    val locationName: String,
    @SerializedName(value = "address")
    val locationAddress: String,
    @SerializedName(value = "type")
    val locationType: Int,
) : Parcelable
