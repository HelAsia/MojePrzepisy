package com.moje.przepisy.mojeprzepisy.utils

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import com.moje.przepisy.mojeprzepisy.R

class TimeSetDialog {
    private var hour: String? = null
    private var minute: String? = null
    private var hourInt: Int = 0
    private var minuteInt: Int = 0

    fun showDialog(activity: Activity, textViewToSet: TextView) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.time_set_dialog)

        val hourPicker = dialog.findViewById<NumberPicker>(R.id.hourPicker)
        val minutePicker = dialog.findViewById<NumberPicker>(R.id.minutePicker)

        hourPicker.minValue = 0
        hourPicker.maxValue = 24
        hourPicker.wrapSelectorWheel = true
        minutePicker.minValue = 0
        minutePicker.maxValue = 59
        minutePicker.wrapSelectorWheel = true

        val dialogButton = dialog.findViewById<Button>(R.id.set_time_button)
        dialogButton.setOnClickListener {
            hourInt = hourPicker.value
            if (hourInt < 10) {
                hour = "0" + Integer.toString(hourInt)
            }
            hour = Integer.toString(hourInt)

            minuteInt = minutePicker.value
            if (minuteInt < 10) {
                minute = "0" + Integer.toString(minuteInt)
            }
            minute = Integer.toString(minuteInt)
            dialog.dismiss()
            textViewToSet.text = "$hour:$minute:00"
        }
        dialog.show()
    }
}
