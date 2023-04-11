package com.example.myprofile

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myprofile.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create SharedPreferences xml file
        sharedPreferences = this.getSharedPreferences("autoLogin", MODE_PRIVATE)

        val email = sharedPreferences.getString("email", "").toString()

        val parsedName = parseEmail(email)

        val name = parsedName[0].substring(0,1).uppercase() + parsedName[0].substring(1).lowercase()
        val surname = parsedName[1].substring(0,1).uppercase() + parsedName[1].substring(1).lowercase()

        binding.textviewProfileName.text = "$name $surname"
    }

    private fun parseEmail(email: String): List<String> {

        return "@.*\$".toRegex().replace(email, "")
            .split(".")
    }
}