package com.platzi.android.rickandmorty.usecases

import com.platzi.android.rickandmorty.database.CharacterDao
import com.platzi.android.rickandmorty.database.CharacterEntity
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class GetAllFavoriteCharactersUseCase(
    private val characterDao: CharacterDao
) {

    fun invoke(): Flowable<List<CharacterEntity>> = characterDao
        .getAllFavoriteCharacters()
        .onErrorReturn { emptyList() }
        .subscribeOn(Schedulers.io())
}
