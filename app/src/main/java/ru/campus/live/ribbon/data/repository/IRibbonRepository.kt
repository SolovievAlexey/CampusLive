package ru.campus.live.ribbon.data.repository

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.ribbon.data.model.PublicationPostObject
import ru.campus.live.ribbon.data.model.RibbonModel

interface IRibbonRepository {
    fun get(offset: Int): ResponseObject<ArrayList<RibbonModel>>
    fun post(params: PublicationPostObject): ResponseObject<RibbonModel>
    fun vote(params: VoteModel)
    fun complaint(id: Int)
}