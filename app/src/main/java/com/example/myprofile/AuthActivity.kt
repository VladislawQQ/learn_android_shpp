package com.example.myprofile

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.myprofile.databinding.ActivityAuthBinding
import com.google.android.material.snackbar.Snackbar

private const val KEY_EMAIL = "email"
private const val KEY_PASSWORD = "password"
private const val KEY_REMEMBERME = "rememberMe"

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create SharedPreferences xml file
        sharedPreferences = this.getSharedPreferences("autoLogin", MODE_PRIVATE)

        // Check if "remember me" is true
        // then autologin to profile and start "my profile" intent
        if (sharedPreferences.getBoolean("rememberMe", false)) {

            intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Email and password validation
        emailErrorChanges()
        passwordErrorChanges()

        // Check if fields with email or password is valid
        // then start "my profile" intent
        // else show Snackbar
        binding.registerButton.setOnClickListener {
            if (!fieldsIsEmpty() &&
                binding.passwordEditText.text.toString() != "" &&
                binding.emailEditText.text.toString() != "") {

                if (binding.rememberMeCheckbox.isChecked) {
                    putDataToSharedPreferences()
                }

                // init and start profile activity with anim
                intent = Intent(this, ProfileActivity::class.java)

                intent.putExtra("usersEmail", binding.emailEditText.text.toString())

                startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            } else {
                val viewColor = ContextCompat.getColor(this, R.color.view_color)

                val snackBar: Snackbar = Snackbar.make(
                    binding.root,
                    "Not all fields are filled",
                    Snackbar.LENGTH_LONG
                ).setTextColor(viewColor)

                snackBar.setBackgroundTint(
                    ContextCompat.getColor(
                        this,
                        R.color.white_edit_text
                    )
                )

                snackBar.show()
            }
        }
    }


    /**
     * Put email, password and boolean rememberMe to sharedPreferences xml file
     */
    private fun putDataToSharedPreferences() {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, binding.emailEditText.text.toString())
        editor.putString(KEY_PASSWORD, binding.passwordEditText.text.toString())
        editor.putBoolean(KEY_REMEMBERME, true)
        editor.apply()
    }

    /**
     * Check email and password container.error
     * if null  return false
     * else true
     */
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

    /**
     * Check if email is valid with Patterns.EMAIL_ADDRESS
     * if !isValid return "Invalid Email Address"
     * else return null
     */
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
            binding.passwordContainer.error = passwordIsValid()
        }
    }

    /**
     * Check if password is valid with regex
     * if !isValid return String
     * else return null
     */
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


