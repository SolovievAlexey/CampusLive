package ru.campus.core.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 17.05.2022 21:59
 */

@Parcelize
data class UploadMediaModel(
    @SerializedName("id")
    var id: Int,
    @SerializedName("fullPath")
    val fullPath: String,
    @SerializedName("upload")
    var upload: Boolean,
    @SerializedName("error")
    var error: Boolean,
    @SerializedName("animation")
    var animation: Boolean
) : Parcelable