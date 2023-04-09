package com.example.myprofile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myprofile.databinding.ActivityAuthBinding
import com.example.myprofile.databinding.ActivityProfileBinding
import java.util.regex.Pattern

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arguments = intent.extras

        val email = arguments?.get("usersEmail").toString()
        val parsedName = parseEmail(email)

        val name = parsedName[0].substring(0,1).uppercase() + parsedName[0].substring(1).lowercase()
        val surname = parsedName[1].substring(0,1).uppercase() + parsedName[1].substring(1).lowercase()

        binding.profileNameTextView.text = "$name $surname"
    }

    private fun parseEmail(email: String): List<String> {

        return "@.*\$".toRegex().replace(email, "")
            .split(".")
    }
}