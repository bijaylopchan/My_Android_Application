package com.example.myandroidapplication

data class DashboardResponse(
    val entities: List<Exercise>,
    val entityTotal: Int
)