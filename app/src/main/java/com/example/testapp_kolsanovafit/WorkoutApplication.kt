package com.example.testapp_kolsanovafit

import android.app.Application
import com.example.testapp_kolsanovafit.di.AppComponent
import com.example.testapp_kolsanovafit.di.DaggerAppComponent

class WorkoutApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .build()
    }
}