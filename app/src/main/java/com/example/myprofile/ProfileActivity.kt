package com.example.myprofile

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myprofile.databinding.ActivityProfileBinding
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create SharedPreferences xml file
        sharedPreferences = this.getSharedPreferences(SHARED_PREF_FILE_NAME, MODE_PRIVATE)

        val email = sharedPreferences.getString(KEY_EMAIL, "").toString()

        val parsedName = parseEmail(email)

        val name = parsedName.first().replaceFirstChar { it.titlecase(Locale.getDefault()) }
        val surname = parsedName[1].replaceFirstChar { it.titlecase(Locale.getDefault()) }

        binding.textviewProfileName.text = "$name $surname"
    }

    private fun parseEmail(email: String): List<String> {
        return REGEX_EMAIL_PARSE.toRegex().replace(email, "")
            .split(".")
    }

    companion object {
        private const val SHARED_PREF_FILE_NAME = "autoLogin"
        private const val KEY_EMAIL = "email"
        private const val REGEX_EMAIL_PARSE = "@.*\$"
    }
}