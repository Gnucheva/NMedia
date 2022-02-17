package ru.netology.nmedia.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.FeedFragment
import ru.netology.nmedia.activity.UnitPostFragment

object AndroidUtils {
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun FeedFragment.showToast(text: Int, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(
            activity,
            getString(text),
            length
        ).show()
    }

    fun UnitPostFragment.showToast(text: Int, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(
            activity,
            getString(text),
            length
        ).show()
    }

}

class MyDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(" Важное сообщение!")
                .setMessage(R.string.app_not_found)
                .setPositiveButton("ОК") { dialog, id ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}




