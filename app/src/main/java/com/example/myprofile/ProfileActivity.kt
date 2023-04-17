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
        sharedPreferences = this.getSharedPreferences("autoLogin", MODE_PRIVATE)

        val email = sharedPreferences.getString("email", "").toString()

        val parsedName = parseEmail(email)

        val name = parsedName[0].substring(0,1).uppercase() + parsedName[0].substring(1).lowercase()
        val surname = parsedName[1].substring(0,1).uppercase() + parsedName[1].substring(1).lowercase()

//        val name = parsedName.first().replaceFirstChar { it.titlecase(Locale.getDefault()) }
//        val surname = parsedName[1].replaceFirstChar { it.titlecase(Locale.getDefault()) }
        binding.textviewProfileName.text = "$name $surname"
    }

    private fun parseEmail(email: String): List<String> {

        //TODO regex теж в константи
        return "@.*\$".toRegex().replace(email, "")
            .split(".")
    }
}