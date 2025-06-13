package com.example.myandroidapplication

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    @SerializedName("exerciseName") val exerciseName: String,

    @SerializedName("muscleGroup") val muscleGroup: String,

    val equipment: String,

    val difficulty: String,

    @SerializedName("caloriesBurnedPerHour") val caloriesBurnedPerHour: Int,

    val description: String
) : Parcelable