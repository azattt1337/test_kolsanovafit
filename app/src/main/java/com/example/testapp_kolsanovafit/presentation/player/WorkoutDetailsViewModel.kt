package com.example.testapp_kolsanovafit.presentation.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp_kolsanovafit.domain.models.VideoWorkout
import com.example.testapp_kolsanovafit.domain.usecases.GetVideoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkoutDetailsViewModel @Inject constructor(
    private val getVideoUseCase: GetVideoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<WorkoutDetailsUiState>(WorkoutDetailsUiState.Loading)
    val uiState: StateFlow<WorkoutDetailsUiState> = _uiState.asStateFlow()

    fun loadVideoWorkout(workoutId: Int) {
        viewModelScope.launch {
            getVideoUseCase(workoutId).fold(
                onSuccess = { workout ->
                    _uiState.value = WorkoutDetailsUiState.Success(workout)
                },
                onFailure = { error ->
                    _uiState.value = WorkoutDetailsUiState.Error(error.message ?: "Unknown error")
                }
            )
        }
    }
}