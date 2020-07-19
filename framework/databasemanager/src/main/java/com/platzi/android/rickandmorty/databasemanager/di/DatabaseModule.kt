package com.platzi.android.rickandmorty.databasemanager.di

import android.app.Application
import com.platzi.android.rickandmorty.data.LocalCharacterDataSource
import com.platzi.android.rickandmorty.databasemanager.CharacterDatabase
import com.platzi.android.rickandmorty.databasemanager.CharacterRoomDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application): CharacterDatabase = CharacterDatabase.getDatabase(app)

    @Provides
    fun localCharacterDataSourceProvider(
        database: CharacterDatabase
    ): LocalCharacterDataSource = CharacterRoomDataSource(database)
}
