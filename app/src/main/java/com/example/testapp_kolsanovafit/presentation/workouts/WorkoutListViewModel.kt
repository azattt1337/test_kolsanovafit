package com.example.testapp_kolsanovafit.presentation.workouts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp_kolsanovafit.domain.models.Workout
import com.example.testapp_kolsanovafit.domain.models.WorkoutType
import com.example.testapp_kolsanovafit.domain.usecases.GetWorkoutsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

class WorkoutListViewModel @Inject constructor(
    private val getWorkoutsUseCase: GetWorkoutsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<WorkoutListUiState>(WorkoutListUiState.Loading)

    val uiState: StateFlow<WorkoutListUiState> = _uiState.asStateFlow()

    private var allWorkouts: List<Workout> = emptyList()
    private var searchQuery = ""
    private var currentFilter: WorkoutType? = null

    init {
        loadWorkouts()
    }

    private fun loadWorkouts() {
        _uiState.value = WorkoutListUiState.Loading

        viewModelScope.launch {
            val result = getWorkoutsUseCase()

            result.fold(
                onSuccess = { workouts ->
                    allWorkouts = workouts
                },
                onFailure = { error ->
                    _uiState.value = WorkoutListUiState.Error("Ошибка загрузки ${error.message}")
                }
            )
        }
    }

    fun searchWorkoutsByName(query: String) {
        searchQuery = query
        applyFiltersAndSearch()
    }

    fun filterByType(type: WorkoutType?) {
        currentFilter  = type
        applyFiltersAndSearch()
    }


    private fun applyFiltersAndSearch() {
        var filteredWorkouts = allWorkouts

        if (currentFilter != null) {
            filteredWorkouts = filteredWorkouts.filter { it.type == currentFilter }
        }

        if (searchQuery.isNotBlank()) {
            filteredWorkouts = filteredWorkouts.filter { it.title.contains(searchQuery, ignoreCase = true) }
        }

        _uiState.value = if (filteredWorkouts.isEmpty()) {
            WorkoutListUiState.Empty("Тренировки не найдены")
        } else {
            WorkoutListUiState.Success(filteredWorkouts)
        }
    }

    fun retry() {
        loadWorkouts()
    }
}