package ru.campus.live.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorObject(
    val code: Int = 0,
    val icon: Int = 0,
    val message: String = ""
) : Parcelable
