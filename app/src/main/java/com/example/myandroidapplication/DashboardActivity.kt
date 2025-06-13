package com.example.myandroidapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myandroidapplication.databinding.ActivityDashboardBinding
import com.example.myassssmentapplication.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var exerciseAdapter: ExerciseAdapter
    private var keypass: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get keypass from intent
        keypass = intent.getStringExtra("keypass") ?: ""

        setupRecyclerView()
        setupUI()
        fetchDashboardData()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewExercises.layoutManager = LinearLayoutManager(this)
    }

    private fun setupUI() {
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.swipeRefreshLayout.setOnRefreshListener { fetchDashboardData() }

        binding.btnLogout.setOnClickListener {
            // Clear the activity stack and navigate to MainActivity (login screen)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun fetchDashboardData() {
        binding.progressBar.visibility = View.VISIBLE
        binding.swipeRefreshLayout.isRefreshing = true

        // Make API call using Retrofit
        RetrofitClient.instance.getDashboard("Bearer $keypass")
            .enqueue(object : Callback<DashboardResponse> {
                override fun onResponse(call: Call<DashboardResponse>, response: Response<DashboardResponse>) {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefreshLayout.isRefreshing = false

                    if (response.isSuccessful) {
                        response.body()?.let { dashboardResponse ->
                            updateUI(dashboardResponse.entities)
                            binding.tvTotalExercises.text = "Total: ${dashboardResponse.entityTotal} exercises"
                        }
                    } else {
                        showError("Failed to load exercises: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefreshLayout.isRefreshing = false
                    showError("Network error: ${t.message}")
                }
            })
    }

    private fun updateUI(exercises: List<Exercise>) {
        if (exercises.isEmpty()) {
            binding.recyclerViewExercises.visibility = View.GONE
            binding.tvEmptyState.visibility = View.VISIBLE
        } else {
            binding.recyclerViewExercises.visibility = View.VISIBLE
            binding.tvEmptyState.visibility = View.GONE

            exerciseAdapter = ExerciseAdapter(exercises) { exercise ->
                navigateToDetails(exercise)
            }
            binding.recyclerViewExercises.adapter = exerciseAdapter
        }
    }

    private fun navigateToDetails(exercise: Exercise) {
        val intent = Intent(this, ExerciseDetailActivity::class.java).apply {
            putExtra("exercise", exercise)
        }
        startActivity(intent)
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}