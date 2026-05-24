package com.codewithkeshav.teachertrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Find views
        val firstNameEditText = findViewById<EditText>(R.id.first_name)
        val lastNameEditText = findViewById<EditText>(R.id.last_name)
        val userIdEditText = findViewById<EditText>(R.id.user_id) // ✅ Added User ID field
        val phoneNumberEditText = findViewById<EditText>(R.id.phone_number)
        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password_registration)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirm_password)
        val registerButton = findViewById<Button>(R.id.register_button)
        val loginText = findViewById<TextView>(R.id.login_redirect) // ✅ Added Login TextView

        // Handle Login Text Click
        loginText.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString().trim()
            val lastName = lastNameEditText.text.toString().trim()
            val userId = userIdEditText.text.toString().trim() // ✅ Capture User ID
            val phoneNumber = phoneNumberEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            // Validate inputs
            if (firstName.isEmpty() || lastName.isEmpty() || userId.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proceed with registration (Dummy example)
            registerUser(firstName, lastName, userId, phoneNumber, email, password)
        }
    }

    private fun registerUser(firstName: String, lastName: String, userId: String, phoneNumber: String, email: String, password: String) {
        // Dummy logic (Replace with actual database registration)
        Toast.makeText(this, "Registration Successful for $userId", Toast.LENGTH_SHORT).show()

        // Redirect to Login screen
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
