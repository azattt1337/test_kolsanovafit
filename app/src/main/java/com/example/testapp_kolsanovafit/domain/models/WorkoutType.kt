package com.example.testapp_kolsanovafit.domain.models

enum class WorkoutType(val value: Int) {
    TRAINING(1),
    LIVE(2),
    COMPLEX(3);

    companion object {
        fun fromInt(value: Int) = entries.find { it.value == value } ?: TRAINING
    }
}