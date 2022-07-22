package ru.campus.feature_location.data.model

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
    val id: Int,
    @SerializedName(value = "name")
    val name: String,
    @SerializedName(value = "address")
    val address: String,
    @SerializedName(value = "type")
    val type: Int
) : Parcelable
