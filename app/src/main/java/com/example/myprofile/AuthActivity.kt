package com.example.myprofile

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.red
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.red
import androidx.core.widget.doAfterTextChanged
import com.example.myprofile.databinding.ActivityAuthBinding
import com.google.android.material.snackbar.Snackbar

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailErrorChanges()
        passwordErrorChanges()

        binding.registerButton.setOnClickListener {
            if (!fieldsIsEmpty() &&
                binding.passwordEditText.text.toString() != "" &&
                binding.emailEditText.text.toString() != "") {

                intent = Intent(this, ProfileActivity:: class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            } else {
                val viewColor = ContextCompat.getColor(this, R.color.view_color)

                val snackBar: Snackbar = Snackbar.make(binding.root, "Not all fields are filled", Snackbar.LENGTH_LONG)
                    .setTextColor(viewColor)

                snackBar.setBackgroundTint(ContextCompat.getColor(this, R.color.white_edit_text))

                snackBar.show()
            }
        }
    }

    private fun fieldsIsEmpty(): Boolean {
        val validEmail = binding.emailContainer.error == null
        val validPassword = binding.passwordContainer.error == null

        if (validEmail && validPassword) {
            return false
        }

        return true
    }

    private fun emailErrorChanges() {
        binding.emailEditText.doAfterTextChanged {
                binding.emailContainer.error = emailIsValid()
        }
    }

    private fun emailIsValid(): String? {
        val emailText = binding.emailEditText.text.toString()

        val isValid = Patterns.EMAIL_ADDRESS.matcher(emailText).matches()
        if (!isValid) {
            return "Invalid Email Address"
        }

        return null
    }

    private fun passwordErrorChanges() {
        binding.passwordEditText.doAfterTextChanged {
            binding.passwordContainer.error = passwordIsValid() }
    }

    private fun passwordIsValid(): String? {
        val passwordText = binding.passwordEditText.text.toString()

        val upperCaseRegex = Regex("[A-Z]")
        val digitsCaseRegex = Regex("[0-9]")

        return if (passwordText.contains(" ")) {
            return "Don't use spaces"
        } else if (passwordText.length < 8) {
            "Minimum 8 character password"
        } else if (!upperCaseRegex.containsMatchIn(passwordText)) {
            "Must contain upper-case characters"
        } else if (!digitsCaseRegex.containsMatchIn(passwordText)) {
            "Must contain number characters"
        } else null
    }
}