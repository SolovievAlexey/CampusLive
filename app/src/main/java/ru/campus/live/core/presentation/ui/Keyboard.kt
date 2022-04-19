package ru.campus.live.core.presentation.ui

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

class Keyboard {

    fun hide(activity: Activity?) {
        val imm: InputMethodManager =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) view = View(activity)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}