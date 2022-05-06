package ru.campus.live.ribbon.data.repository

import ru.campus.live.core.data.source.IUserDataStore
import ru.campus.live.ribbon.data.model.RibbonStatusModel
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 06.05.2022 22:05
 */

class BaseRibbonStatusRepository @Inject constructor(
    private val userDataStore: IUserDataStore
) : RibbonStatusRepository {

    override fun get(): RibbonStatusModel {
        val locationName = userDataStore.location().locationName
        val falovers = 2234
        val karma = userDataStore.rating()
        return RibbonStatusModel(location = locationName, falovers = falovers, karma = karma)
    }

}