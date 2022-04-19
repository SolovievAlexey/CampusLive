package ru.campus.live.ribbon.domain

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.data.model.RibbonPostModel
import ru.campus.live.ribbon.data.repository.IRibbonRepository
import javax.inject.Inject

class RibbonEditorInteractor @Inject constructor(
    private val repository: IRibbonRepository
) {

    fun post(params: RibbonPostModel): ResponseObject<RibbonModel> {
        return repository.post(params)
    }

}