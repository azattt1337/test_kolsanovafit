package com.example.testapp_kolsanovafit.domain.usecases

import com.example.testapp_kolsanovafit.domain.models.Workout
import com.example.testapp_kolsanovafit.domain.repository.WorkoutRepository
import javax.inject.Inject

class GetWorkoutsUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(): Result<List<Workout>> {
        return repository.getWorkouts()
    }
}