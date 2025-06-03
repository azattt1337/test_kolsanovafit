package com.example.testapp_kolsanovafit.di

import com.example.testapp_kolsanovafit.presentation.player.WorkoutDetailsFragment
import com.example.testapp_kolsanovafit.presentation.workouts.WorkoutListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(fragment: WorkoutListFragment)
    fun inject(fragment: WorkoutDetailsFragment)

}