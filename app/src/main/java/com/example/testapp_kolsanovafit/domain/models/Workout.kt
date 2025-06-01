package com.example.testapp_kolsanovafit.domain.models

data class Workout(
    val id: Int,
    val title: String,
    val description: String?,
    val type: WorkoutType,
    val duration: String
)
