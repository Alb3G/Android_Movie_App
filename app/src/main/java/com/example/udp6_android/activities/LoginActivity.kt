package com.example.udp6_android.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.udp6_android.R
import com.example.udp6_android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var email: String? = ""
    private var pass: String? = ""
    private var isRemembered: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        this.title = getString(R.string.login_title)
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        setSharedPreferences()
        binding.loginButton.setOnClickListener {
            val emailTxt = binding.emailEdt.text.toString()
            val password = binding.passwordEdt.text.toString()

            if (login(emailTxt, password)) {
                email = emailTxt
                pass = password
                navToMain()
                savePreferences()
            }
        }
    }

    private fun login(email: String, password: String): Boolean {
        var valid = false
        if(!validEmail(email)) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
        } else if(!validPassword(password)) {
            Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show()
        } else {
            valid = true
        }

        return valid
    }

    private fun savePreferences() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("remember", binding.rememberUserSwitch.isChecked)
        if (binding.rememberUserSwitch.isChecked) {
            editor.putString("email", email)
            editor.putString("pass", pass)
            editor.putBoolean("remember", true)
            editor.apply()
        } else {
            editor.clear()
            editor.putBoolean("remember", false)
            editor.apply()
        }
    }

    private fun navToMain() {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private fun setSharedPreferences() {
        if (sharedPreferences.contains("remember")) {
            isRemembered = sharedPreferences.getBoolean("remember", false)
            binding.rememberUserSwitch.isChecked = isRemembered
        }
        if (isRemembered) {
            if (sharedPreferences.contains("email")) {
                email = sharedPreferences.getString("email", null)
                binding.emailEdt.setText(email)
            }
            if (sharedPreferences.contains("pass")) {
                pass = sharedPreferences.getString("pass", null)
                binding.passwordEdt.setText(pass)
            }
        }
    }

    private fun validEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validPassword(password: String): Boolean {
        return !TextUtils.isEmpty(password) && password.length >= 8
    }
}