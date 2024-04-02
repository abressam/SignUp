package com.example.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import com.example.signup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner = binding.spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.user_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.usernameEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)
        binding.confirmPasswordEditText.addTextChangedListener(textWatcher)

        binding.registerButton.setOnClickListener {
            registerUser()
        }

        updateButtonState()
    }

    private fun registerUser() {
        val username = binding.usernameEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString()
        val userType = binding.spinner.selectedItem.toString()

        val existingUser = PreferenceManager.getUser(this)
        if (existingUser != null && existingUser.username == username) {
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("The user already exists!")
                .setPositiveButton("OK", null)
                .show()
        } else {
            PreferenceManager.saveUser(this, username, password, userType)

            AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("The user has been registered successfully")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.cancel()

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                .show()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            updateButtonState()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun updateButtonState() {
        val username = binding.usernameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        val allFieldsFilled = username.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
        val passwordsMatch = password == confirmPassword

        binding.registerButton.isEnabled = allFieldsFilled && passwordsMatch
    }
}