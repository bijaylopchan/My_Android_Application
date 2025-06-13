package com.example.myandroidapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.databinding.ActivityExerciseDetailBinding

class ExerciseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        loadExerciseData()
    }

    private fun setupUI() {
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun loadExerciseData() {
        // Get exercise data from intent
        val exercise = intent.getParcelableExtra<Exercise>("exercise")

        exercise?.let {
            binding.tvExerciseName.text = it.exerciseName
            binding.tvMuscleGroup.text = it.muscleGroup
            binding.tvEquipment.text = it.equipment
            binding.tvDifficulty.text = it.difficulty
            binding.tvCalories.text = "${it.caloriesBurnedPerHour} calories"
            binding.tvDescription.text = it.description
        }
    }
}