package ru.campus.feature_start.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 19:40
 */

data class LoginModel(
    @SerializedName("uid") val uid: Int,
    @SerializedName("token") val token: String,
    @SerializedName("rating") val rating: Int
)
