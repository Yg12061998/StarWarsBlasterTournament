package com.yogigupta1206.flipkart_starwarsblastertournament

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StarWarsBlasterTournamentApp: Application() {

    companion object {
        private const val TAG = "StarWarsBlasterTournamentApp"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: StarWarsBlasterTournamentApp")
    }
}