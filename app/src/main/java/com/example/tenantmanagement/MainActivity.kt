package com.example.tenantmanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var lastTenant: Tenant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val loggedInEmail = intent.getStringExtra("EMAIL")
        if (loggedInEmail != null) {
            Toast.makeText(this, "Logged in as $loggedInEmail", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val nameEditText = findViewById<EditText>(R.id.tenantNameEditText)
        val phoneEditText = findViewById<EditText>(R.id.phoneEditText)
        val houseEditText = findViewById<EditText>(R.id.rentEditText)
        val tenantResultTextView = findViewById<TextView>(R.id.tenantResultTextView)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val shareButton = findViewById<Button>(R.id.shareButton)


        saveButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val houseOrRent = houseEditText.text.toString().trim()

            nameEditText.error = null
            phoneEditText.error = null
            houseEditText.error = null

            var isValid = true

            if (name.isEmpty()) {
                nameEditText.error = "Required"
                isValid = false
            }
            if (phone.isEmpty()) {
                phoneEditText.error = "Required"
                isValid = false
            }
            if (houseOrRent.isEmpty()) {
                houseEditText.error = "Required"
                isValid = false
            }

            if (!isValid) return@setOnClickListener

            val tenant = Tenant(name, phone, houseOrRent)
            lastTenant = tenant
            tenantResultTextView.text = tenant.summary()
            Toast.makeText(this, "Tenant saved successfully", Toast.LENGTH_SHORT).show()
        }


        shareButton.setOnClickListener {
            val tenant = lastTenant
            if (tenant == null) {
                Toast.makeText(this, "Save a tenant first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, tenant.summary())
            startActivity(Intent.createChooser(intent, "Share tenant"))
        }
    }
}