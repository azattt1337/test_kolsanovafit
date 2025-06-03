package com.example.testapp_kolsanovafit.presentation.player

import android.util.Log
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
                    if (isValidVideoUrl(workout.videoUrl)) {
                        _uiState.value = WorkoutDetailsUiState.Success(workout)
                    } else {
                        _uiState.value = WorkoutDetailsUiState.Error("Некорректная ссылка")
                    }
                },
                onFailure = { error ->
                    _uiState.value = WorkoutDetailsUiState.Error(error.message ?: "Unknown error")
                }
            )
        }
    }

    private fun isValidVideoUrl(url: String): Boolean {
        return url.startsWith("http")
    }
}