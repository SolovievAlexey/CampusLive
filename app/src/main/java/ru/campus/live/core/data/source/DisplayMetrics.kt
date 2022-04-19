package ru.campus.live.core.data.source

import android.content.Context
import android.util.DisplayMetrics
import javax.inject.Inject
import kotlin.math.roundToInt

class DisplayMetrics @Inject constructor(context: Context) {

    private val displayMetrics = context.resources.displayMetrics

    fun get(width: Int, height: Int): Array<Int> {
        val params = arrayOf(0, 0)
        val pxAndDp = (80 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
        val k = pxAndDp.toFloat() / width.toFloat()
        params[0] = pxAndDp
        params[1] = (height.toFloat() * k).roundToInt()
        return params
    }

}