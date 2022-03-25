package com.example.parstagram

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseUser


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (ParseUser.getCurrentUser() != null) {
            goToMainActivity()
        }

        findViewById<Button>(R.id.login_button).setOnClickListener {
            val username = findViewById<EditText>(R.id.username).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()
            loginUser(username, password)
        }

        findViewById<Button>(R.id.signup_button).setOnClickListener {
            val username = findViewById<EditText>(R.id.username).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()
            signUpUser(username, password)
        }
    }

    private fun signUpUser(username: String, password: String) {
        // Create the ParseUser
        val user = ParseUser()
        // Set core properties

        user.username = username
        user.setPassword(password)
        // Invoke signUpInBackground

        user.signUpInBackground { e ->
            if (e == null) {
                goToMainActivity()
            } else {
                // Sign up didn't succeed. Look at the ParseException
                    e.printStackTrace()
                Toast.makeText(this, "Error sign up", Toast.LENGTH_SHORT).show()
                // to figure out what went wrong
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(
            username, password, ({ user, e ->
                if (user != null) {
                    Log.i(TAG, "successfully logged in user")
                    goToMainActivity()
                } else {
                    e.printStackTrace()
                    Toast.makeText(this, "Error loggin in", Toast.LENGTH_SHORT).show()
                }
            })
        )
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    companion object {
        const val TAG = "LoginActivity"
    }
}