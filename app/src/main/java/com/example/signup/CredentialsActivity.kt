package com.example.signup

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.signup.databinding.ActivityCredentialsBinding

class CredentialsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCredentialsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCredentialsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.usernameEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)

        binding.saveButton.setOnClickListener {
            val newUsername = binding.usernameEditText.text.toString()
            val newPassword = binding.passwordEditText.text.toString()

            sendNewCredentials(newUsername, newPassword)
        }

        updateButtonState()
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
        binding.saveButton.isEnabled = username.isNotBlank() && password.isNotBlank()
    }

    private fun sendNewCredentials(newUsername: String, newPassword: String) {

        PreferenceManager.updateUserInfo(this, newUsername, newPassword)

        AlertDialog.Builder(this)
            .setMessage("You set a new username and password!")
            .setTitle("Success")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.cancel()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            .show()
    }
}