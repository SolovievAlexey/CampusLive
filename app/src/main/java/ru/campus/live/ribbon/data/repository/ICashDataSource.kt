package ru.campus.live.ribbon.data.repository

import ru.campus.live.ribbon.data.model.RibbonModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 23.04.2022 14:37
 */

interface ICashDataSource {
    fun get()
    fun post(model: ArrayList<RibbonModel>)
}