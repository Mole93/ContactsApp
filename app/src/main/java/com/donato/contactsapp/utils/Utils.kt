package com.donato.contactsapp.utils

import android.content.Context
import android.content.DialogInterface
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
object Utils {


    fun showConfirmationDialog(context: Context, @StringRes title: Int, @StringRes message: Int, @StringRes positiveButton: Int): AlertDialog {
        return showConfirmationDialog(context, title, message, positiveButton, null)
    }


    fun showConfirmationDialog(context: Context, @StringRes title: Int, @StringRes message: Int, @StringRes positiveButton: Int, listenerPositive: DialogInterface.OnClickListener?): AlertDialog {
        return showDialog(context, title, message, positiveButton, listenerPositive, -1, null)
    }


    private fun showDialog(context: Context, @StringRes title: Int, @StringRes message: Int, @StringRes positiveButton: Int, listenerPositive: DialogInterface.OnClickListener?, @StringRes negativeButton: Int, listenerNegative: DialogInterface.OnClickListener?): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButton, listenerPositive)

        if (negativeButton != -1)
            builder.setNegativeButton(negativeButton, listenerNegative)

        return builder.show()
    }


}