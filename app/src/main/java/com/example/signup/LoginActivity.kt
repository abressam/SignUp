package com.example.signup

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.signup.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.usernameEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            sendDataToCredentials(username, password)
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
            binding.loginButton.isEnabled = username.isNotBlank() && password.isNotBlank()
        }

        private fun sendDataToCredentials(username: String, password: String) {
            val savedUser = PreferenceManager.getUser(this)

            if(savedUser != null && savedUser.username == username && savedUser.password == password) {
                val bundle = Bundle()
                bundle.putString("username", username)
                bundle.putString("password", password)

                val intent = Intent(this, CredentialsActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            } else {
                AlertDialog.Builder(this)
                    .setMessage("The username and/or password are incorrect!")
                    .setTitle("Error: invalid credentials")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.cancel()
                    }
                .show()
            }
        }
    }
