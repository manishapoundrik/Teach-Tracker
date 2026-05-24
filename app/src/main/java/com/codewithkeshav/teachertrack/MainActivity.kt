package com.codewithkeshav.teachertrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Find viewspas
        val userIdEditText = findViewById<EditText>(R.id.user_id)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)
        val registerRedirect = findViewById<TextView>(R.id.register_text) // Ensure ID matches in XML

        loginButton.setOnClickListener {
            val userId = userIdEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (userId.isNotEmpty() && password.isNotEmpty()) {
                loginUser(userId, password)
            } else {
                Toast.makeText(this, "Please enter User ID and Password", Toast.LENGTH_SHORT).show()
            }
        }


        registerRedirect.setOnClickListener {
            Toast.makeText(this, "Redirecting to Registration...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(userId: String, password: String) {
        // Dummy authentication (replace with actual logic)
        if (userId == "teacher123" && password == "password") {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DashboardActivity::class.java))
            finish() // Close login activity
        } else {
            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
        }
    }
}
