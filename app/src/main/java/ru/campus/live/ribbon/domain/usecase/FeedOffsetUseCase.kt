package ru.campus.live.ribbon.domain.usecase

import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.data.model.RibbonViewType

class FeedOffsetUseCase {

    fun execute(model: ArrayList<RibbonModel>): Int {
        var offset = 0
        model.forEach { item -> if (item.viewType == RibbonViewType.PUBLICATION) offset++ }
        return offset
    }

}