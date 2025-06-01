package com.example.testapp_kolsanovafit.data.mappers

import com.example.testapp_kolsanovafit.data.dto.VideoWorkoutDto
import com.example.testapp_kolsanovafit.data.dto.WorkoutDto
import com.example.testapp_kolsanovafit.domain.models.VideoWorkout
import com.example.testapp_kolsanovafit.domain.models.Workout
import com.example.testapp_kolsanovafit.domain.models.WorkoutType

fun WorkoutDto.toDomain(): Workout {
    return Workout(
        id = id,
        title = title,
        description = description,
        type = WorkoutType.fromInt(type),
        duration = duration
    )
}

fun VideoWorkoutDto.toDomain(): VideoWorkout {
    return VideoWorkout(
        id = id,
        duration = duration,
        videoUrl = link
    )
}