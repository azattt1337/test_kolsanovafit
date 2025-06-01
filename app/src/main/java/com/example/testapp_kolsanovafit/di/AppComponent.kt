package com.example.testapp_kolsanovafit.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

}