package com.example.testapp_kolsanovafit.domain.repository

import com.example.testapp_kolsanovafit.domain.models.VideoWorkout
import com.example.testapp_kolsanovafit.domain.models.Workout

interface WorkoutRepository {

    suspend fun getWorkouts(): Result<List<Workout>>

    suspend fun getVideo(id: Int): Result<VideoWorkout>
}