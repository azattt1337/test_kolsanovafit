package com.example.testapp_kolsanovafit.domain.usecases

import com.example.testapp_kolsanovafit.domain.models.VideoWorkout
import com.example.testapp_kolsanovafit.domain.repository.WorkoutRepository
import javax.inject.Inject

class GetVideoUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(id: Int): Result<VideoWorkout> {
        return repository.getVideo(id)
    }
}