package com.platzi.android.rickandmorty.database

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface CharacterDao {

    @Query("SELECT * FROM Character")
    fun getAllFavoriteCharacters(): Flowable<List<CharacterEntity>>

    @Query("SELECT * FROM Character WHERE character_id = :id")
    fun getCharacterById(id: Int): Maybe<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(characterEntity: CharacterEntity)

    @Delete
    fun deleteCharacter(characterEntity: CharacterEntity)
}
