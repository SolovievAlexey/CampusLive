package ru.campus.live.ribbon.data.repository

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.ribbon.data.db.RibbonDBModel
import ru.campus.live.ribbon.data.model.RibbonPostModel
import ru.campus.live.ribbon.data.model.RibbonModel

interface IRibbonRepository {
    fun getCash(): ArrayList<RibbonModel>
    fun get(offset: Int): ResponseObject<ArrayList<RibbonModel>>
    fun post(params: RibbonPostModel): ResponseObject<RibbonModel>
    fun vote(params: VoteModel)
    fun complaint(id: Int)
}