package com.example.testapp_kolsanovafit.data.api

import com.example.testapp_kolsanovafit.data.dto.VideoWorkoutDto
import com.example.testapp_kolsanovafit.data.dto.WorkoutDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WorkoutApiService {

    @GET("get_workouts")
    suspend fun getWorkouts(): Response<List<WorkoutDto>>

    @GET("get_video")
    suspend fun getVideo(@Query("id") id: Int): Response<VideoWorkoutDto>
}