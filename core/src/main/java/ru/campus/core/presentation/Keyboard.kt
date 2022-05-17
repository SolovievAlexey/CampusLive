package ru.campus.core.presentation

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 17.05.2022 22:23
 */

class Keyboard {

    fun hide(activity: Activity?) {
        val imm: InputMethodManager =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) view = View(activity)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}