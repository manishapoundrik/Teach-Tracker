package com.codewithkeshav.teachertrack

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class DialogEngagement(
    private val context: Context,
    private val currentStatus: String,
    private val onStatusSaved: (className: String, roomNumber: String) -> Unit
) {

    private lateinit var dialog: AlertDialog
    private lateinit var classEditText: EditText
    private lateinit var roomEditText: EditText

    fun show() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_engagement, null)

        // Initialize views
        classEditText = dialogView.findViewById(R.id.classEditText)
        roomEditText = dialogView.findViewById(R.id.roomOrLabNumberEditText)
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)

        // Pre-fill fields if editing existing engagement
        prefillFieldsIfEditing()

        // Build dialog
        dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        saveButton.setOnClickListener {
            validateAndSave()
        }

        dialog.show()
    }

    private fun prefillFieldsIfEditing() {
        if (currentStatus != context.getString(R.string.status_free)) {
            val parts = currentStatus.split(",")
            if (parts.size == 2) {
                val className = parts[0].replace(context.getString(R.string.engaged_prefix), "").trim()
                val roomNumber = parts[1].replace(context.getString(R.string.room_prefix), "").trim()
                classEditText.setText(className)
                roomEditText.setText(roomNumber)
            }
        }
    }

    private fun validateAndSave() {
        val className = classEditText.text.toString().trim()
        val roomNumber = roomEditText.text.toString().trim()

        when {
            className.isEmpty() -> {
                classEditText.error = context.getString(R.string.class_name_required)
                return
            }
            roomNumber.isEmpty() -> {
                roomEditText.error = context.getString(R.string.room_number_required)
                return
            }
            else -> {
                onStatusSaved(className, roomNumber)
                dialog.dismiss()
                Toast.makeText(
                    context,
                    context.getString(R.string.status_saved),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun dismiss() {
        if (::dialog.isInitialized) {
            dialog.dismiss()
        }
    }
}