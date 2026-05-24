package com.codewithkeshav.teachertrack

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ProfileActivity : AppCompatActivity() {

    private lateinit var nameText: EditText
    private lateinit var userIdText: EditText
    private lateinit var phoneText: EditText
    private lateinit var passwordText: EditText
    private lateinit var togglePasswordButton: ImageButton
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        // Initialize UI components
        val backButton: ImageButton = findViewById(R.id.backButton)
        val profileImage: ImageView = findViewById(R.id.profileImage)
        nameText = findViewById(R.id.nameText)
        userIdText = findViewById(R.id.userIdText)
        phoneText = findViewById(R.id.phoneText)
        passwordText = findViewById(R.id.passwordText)
        togglePasswordButton = findViewById(R.id.togglePasswordVisibility)
        val saveButton: MaterialButton = findViewById(R.id.saveButton)

        val editNameButton: ImageButton = findViewById(R.id.editNameButton)
        val editUserIdButton: ImageButton = findViewById(R.id.editUserIdButton)
        val editPhoneButton: ImageButton = findViewById(R.id.editPhoneButton)
        val editPasswordButton: ImageButton = findViewById(R.id.editPasswordButton)

        // Load user details (Replace with real data from DB or SharedPreferences)
        nameText.setText("Jai Mungi")
        userIdText.setText("jai.mungi")
        phoneText.setText("7979800990")
        passwordText.setText("********")

        // Disable editing by default
        nameText.isEnabled = false
        userIdText.isEnabled = false
        phoneText.isEnabled = false
        passwordText.isEnabled = false

        // Edit button actions
        editNameButton.setOnClickListener { enableEditing(nameText) }
        editUserIdButton.setOnClickListener { enableEditing(userIdText) }
        editPhoneButton.setOnClickListener { enableEditing(phoneText) }
        editPasswordButton.setOnClickListener { enableEditing(passwordText) }

        // Toggle password visibility
        togglePasswordButton.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility()
        }

        // Back button functionality
        backButton.setOnClickListener {
            finish()
        }

        // Save button functionality
        saveButton.setOnClickListener {
            saveUserData()
        }
    }

    private fun enableEditing(editText: EditText) {
        editText.isEnabled = true
        editText.requestFocus()
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            togglePasswordButton.setImageResource(R.drawable.ic_visibility)
        } else {
            passwordText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            togglePasswordButton.setImageResource(R.drawable.ic_visibility_off)
        }
        passwordText.setSelection(passwordText.text.length) // Move cursor to end
    }

    private fun saveUserData() {
        val newName = nameText.text.toString()
        val newUserId = userIdText.text.toString()
        val newPhone = phoneText.text.toString()
        val newPassword = passwordText.text.toString()

        // TODO: Save data to database or SharedPreferences

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
    }
}
