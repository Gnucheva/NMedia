package ru.netology.nmedia.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import ru.netology.nmedia.activity.MainActivity

object AndroidUtils {
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun MainActivity.showToast(text: Int, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(
            this,
            getString(text),
            length
        ).show()
    }
}
