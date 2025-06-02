package com.example.testapp_kolsanovafit.presentation.workouts

import com.example.testapp_kolsanovafit.domain.models.Workout

sealed class WorkoutListUiState {
    object Loading: WorkoutListUiState()
    data class Success(val workouts: List<Workout>): WorkoutListUiState()
    data class Error(val message: String): WorkoutListUiState()
    data class Empty(val message: String): WorkoutListUiState()
}