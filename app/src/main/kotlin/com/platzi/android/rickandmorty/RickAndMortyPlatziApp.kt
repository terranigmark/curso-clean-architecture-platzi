package com.platzi.android.rickandmorty

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.platzi.android.rickandmorty.di.DaggerRickAndMortyPlatziComponent
import com.platzi.android.rickandmorty.di.RickAndMortyPlatziComponent

class RickAndMortyPlatziApp: Application() {

    //region Fields

    lateinit var component: RickAndMortyPlatziComponent
        private set

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        component = initAppComponent()
    }

    //endregion

    //region Private Methods

    private fun initAppComponent() = DaggerRickAndMortyPlatziComponent.factory().create(this)

    //endregion
}
