package com.codewithkeshav.teachertrack

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // View references
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var menuButton: ImageButton
    private lateinit var profileButton: ImageView
    private lateinit var toggleButton: Button
    private lateinit var statusText: TextView
    private lateinit var facultyListView: ListView

    // Data storage
    private lateinit var sharedPref: SharedPreferences
    private val engagedFaculties = mutableListOf<String>()

    // Constants
    companion object {
        private const val PREFS_NAME = "TeacherStatusPrefs"
        private const val KEY_STATUS = "current_status"
        private const val KEY_IS_ENGAGED = "is_engaged"
        private const val KEY_FACULTIES = "faculties_list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize views
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        menuButton = findViewById(R.id.menuButton)
        profileButton = findViewById(R.id.profileButton)
        toggleButton = findViewById(R.id.toggleButton)
        statusText = findViewById(R.id.statusText)
        facultyListView = findViewById(R.id.facultyListView)

        // Setup navigation
        navigationView.setNavigationItemSelectedListener(this)
        menuButton.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        profileButton.setOnClickListener { navigateToProfile() }

        // Initialize SharedPreferences
        sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        // Load saved state
        loadSavedState()

        // Set up toggle button
        toggleButton.setOnClickListener {
            if (isEngaged()) {
                showConfirmationDialog()
            } else {
                showEngagementDialog()
            }
        }
    }

    private fun isEngaged(): Boolean {
        return sharedPref.getBoolean(KEY_IS_ENGAGED, false)
    }

    private fun showEngagementDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_engagement, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        val classEditText = dialogView.findViewById<EditText>(R.id.classEditText)
        val roomEditText = dialogView.findViewById<EditText>(R.id.roomOrLabNumberEditText)
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)

        // Pre-fill if existing engagement
        val currentStatus = statusText.text.toString()
        if (currentStatus != getString(R.string.status_free)) {
            val parts = currentStatus.split(",")
            if (parts.size == 2) {
                classEditText.setText(parts[0].removePrefix(getString(R.string.engaged_prefix)).trim())
                roomEditText.setText(parts[1].removePrefix(getString(R.string.room_prefix)).trim())
            }
        }

        saveButton.setOnClickListener {
            val className = classEditText.text.toString().trim()
            val roomNumber = roomEditText.text.toString().trim()

            when {
                className.isEmpty() -> classEditText.error = getString(R.string.class_name_required)
                roomNumber.isEmpty() -> roomEditText.error = getString(R.string.room_number_required)
                else -> {
                    setEngagedStatus(className, roomNumber)
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.confirm_status_change))
            .setMessage(getString(R.string.are_you_sure_free))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                setFreeStatus()
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    private fun setEngagedStatus(className: String, roomNumber: String) {
        val status = "${getString(R.string.engaged_prefix)}$className, ${getString(R.string.room_prefix)}$roomNumber"
        statusText.text = status
        toggleButton.text = getString(R.string.set_free)

        // Add to faculty list if not already present
        val facultyEntry = "$className - ${getString(R.string.room_prefix)}$roomNumber"
        if (!engagedFaculties.contains(facultyEntry)) {
            engagedFaculties.add(facultyEntry)
            updateFacultyList()
        }

        saveState()
    }

    private fun setFreeStatus() {
        statusText.text = getString(R.string.status_free)
        toggleButton.text = getString(R.string.set_engaged)
        saveState()
    }

    private fun updateFacultyList() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            engagedFaculties
        )
        facultyListView.adapter = adapter
    }

    private fun saveState() {
        sharedPref.edit().apply {
            putBoolean(KEY_IS_ENGAGED, statusText.text.toString() != getString(R.string.status_free))
            putString(KEY_STATUS, statusText.text.toString())
            putStringSet(KEY_FACULTIES, engagedFaculties.toSet())
            apply()
        }
    }

    private fun loadSavedState() {
        statusText.text = sharedPref.getString(KEY_STATUS, getString(R.string.status_free))
        toggleButton.text = if (isEngaged()) getString(R.string.set_free) else getString(R.string.set_engaged)

        sharedPref.getStringSet(KEY_FACULTIES, null)?.let {
            engagedFaculties.clear()
            engagedFaculties.addAll(it)
            updateFacultyList()
        }
    }

    private fun navigateToProfile() {
        startActivity(Intent(this, ProfileActivity::class.java))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                // Already on home
            }
            R.id.nav_profile -> navigateToProfile()
            R.id.nav_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.nav_logout -> {
                // Handle logout logic
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}