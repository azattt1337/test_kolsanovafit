package com.example.testapp_kolsanovafit.di

import com.example.testapp_kolsanovafit.data.repository.WorkoutRepositoryImpl
import com.example.testapp_kolsanovafit.domain.repository.WorkoutRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindWorkoutRepository(
        workoutRepositoryImpl: WorkoutRepositoryImpl
    ): WorkoutRepository
}