package ru.campus.live.ribbon.data.repository

import ru.campus.live.core.data.source.IUserDataSource
import ru.campus.live.ribbon.data.model.RibbonStatusModel
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 06.05.2022 22:05
 */

class BaseRibbonStatusRepository @Inject constructor(
    private val userDataSource: IUserDataSource
) : RibbonStatusRepository {

    override fun get(): RibbonStatusModel {
        val locationName = userDataSource.location().locationName
        val falovers = 2234
        val karma = 543
        return RibbonStatusModel(location = locationName, falovers = falovers, karma = karma)
    }

}