package com.example.myandroidapplication

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("sydney/auth") // change this based on your class location
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("dashboard/fitness")
    fun getDashboard(@Header("Authorization") token: String): Call<DashboardResponse>
}