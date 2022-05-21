package ru.campus.core.presentation

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 17.05.2022 22:23
 */

class Keyboard(private val activity: Activity?) {

    private val imm: InputMethodManager =
        activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    fun hide() {
        var view = activity?.currentFocus
        if (view == null) view = View(activity)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun isVisible(): Boolean {
        val result = imm.isAcceptingText
        Log.d("MyLog", "back reuslt = "+result)
        return result
    }

}