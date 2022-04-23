package ru.campus.live.ribbon.data.model

data class ResponseRibbon(
    val data: ArrayList<RibbonModel> = ArrayList(),
    val statusCode: Int = 200
)