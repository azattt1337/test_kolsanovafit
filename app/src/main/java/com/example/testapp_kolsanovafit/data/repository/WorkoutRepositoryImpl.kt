package com.example.testapp_kolsanovafit.data.repository

import com.example.testapp_kolsanovafit.data.api.WorkoutApiService
import com.example.testapp_kolsanovafit.data.mappers.toDomain
import com.example.testapp_kolsanovafit.domain.models.VideoWorkout
import com.example.testapp_kolsanovafit.domain.models.Workout
import com.example.testapp_kolsanovafit.domain.repository.WorkoutRepository
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepositoryImpl @Inject constructor(
    private val apiService: WorkoutApiService
) : WorkoutRepository {
    override suspend fun getWorkouts(): Result<List<Workout>> {
        return try {
            val response = apiService.getWorkouts()
            if (response.isSuccessful) {
                val workouts = response.body()?.map { it.toDomain() } ?: emptyList()
                Result.success(workouts)
            } else {
                Result.failure(Exception("API error: ${response.code()}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("API error: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }

    override suspend fun getVideo(id: Int): Result<VideoWorkout> {
        return try {
            val response = apiService.getVideo(id)
            if (response.isSuccessful) {
                val video = response.body()?.toDomain()
                if (video != null) {
                    Result.success(video)
                } else {
                    Result.failure(Exception("Empty response"))
                }
            } else {
                Result.failure(Exception("API error: ${response.code()}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Network error: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }

}