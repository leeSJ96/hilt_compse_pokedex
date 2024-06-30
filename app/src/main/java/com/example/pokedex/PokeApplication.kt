package com.example.pokedex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PokeApplication :Application(){
    override fun onCreate() {
        super.onCreate()
    }
}