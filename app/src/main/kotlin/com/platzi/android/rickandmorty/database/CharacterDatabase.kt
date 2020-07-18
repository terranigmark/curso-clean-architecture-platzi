package com.platzi.android.rickandmorty.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CharacterEntity::class], version = 1)
@TypeConverters(ListStringConverters::class)
abstract class CharacterDatabase : RoomDatabase() {

    //region Abstract Methods

    abstract fun characterDao(): CharacterDao

    //endregion

    //region Companion Object

    companion object {

        private const val DATABASE_NAME = "rick_and_morty_db"

        @Synchronized
        fun getDatabase(context: Context): CharacterDatabase = Room.databaseBuilder(
            context.applicationContext,
            CharacterDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    //endregion

}
