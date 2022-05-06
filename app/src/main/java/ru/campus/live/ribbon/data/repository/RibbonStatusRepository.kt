package ru.campus.live.ribbon.data.repository

import ru.campus.live.ribbon.data.model.RibbonStatusModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 06.05.2022 22:03
 */

interface RibbonStatusRepository {
    fun get(): RibbonStatusModel
}