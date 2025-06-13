package com.example.myandroidapplication


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myassssmentapplication.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {


    private val TAG = "LoginActivity"


    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvResult: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Initialize views
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvResult = findViewById(R.id.tvResult)


        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()


            Log.d(TAG, "Attempting login with: $username / $password")


            if (username.isEmpty() || password.isEmpty()) {
                tvResult.text = "Please fill in both fields"
                return@setOnClickListener
            }


            tvResult.text = "Logging in..."


            val request = LoginRequest(username, password)


            RetrofitClient.instance.login(request).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    Log.d(TAG, "Server responded: ${response.code()}")


                    if (response.isSuccessful) {
                        val loginResponse = response.body()


                        // ✅ Debugging lines added here
                        Log.d(TAG, "Raw Response: ${response.body()}")
                        Toast.makeText(this@MainActivity, "Token: ${loginResponse?.keypass}", Toast.LENGTH_SHORT).show()


                        val keypass = loginResponse?.keypass


                        if (keypass.isNullOrEmpty()) {
                            tvResult.text = "Login failed: Empty or invalid token"
                            Log.e(TAG, "Empty token received")
                            return
                        }


                        tvResult.text = "Login success! Token: $keypass"
                        Log.d(TAG, "Token received: $keypass")


                        val intent = Intent(this@MainActivity, DashboardActivity::class.java).apply {
                            putExtra("keypass", keypass)
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        tvResult.text = "Login failed: Check credentials"
                        Log.e(TAG, "Login failed: ${response.code()}, ${response.message()}")
                    }
                }


                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    tvResult.text = "Network error: ${t.message}"
                    Log.e(TAG, "Network error", t)
                }
            })
        }
    }
}


