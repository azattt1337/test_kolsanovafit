package com.example.testapp_kolsanovafit.presentation.player

import com.example.testapp_kolsanovafit.domain.models.VideoWorkout

sealed class WorkoutDetailsUiState {
    object Loading : WorkoutDetailsUiState()
    data class Success(val workout: VideoWorkout) : WorkoutDetailsUiState()
    data class Error(val message: String): WorkoutDetailsUiState()
}