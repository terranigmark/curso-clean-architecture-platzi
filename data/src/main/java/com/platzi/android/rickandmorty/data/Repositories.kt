package com.platzi.android.rickandmorty.data

import com.platzi.android.rickandmorty.domain.Character
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

class CharacterRepository(
    private val remoteCharacterDataSource: RemoteCharacterDataSource,
    private val localCharacterDataSource: LocalCharacterDataSource
) {

    //region Public Methods

    fun getAllCharacters(page: Int): Single<List<Character>> =
        remoteCharacterDataSource.getAllCharacters(page)

    fun getAllFavoriteCharacters(): Flowable<List<Character>> =
        localCharacterDataSource.getAllFavoriteCharacters()

    fun getFavoriteCharacterStatus(characterId: Int): Maybe<Boolean> =
        localCharacterDataSource.getFavoriteCharacterStatus(characterId)

    fun updateFavoriteCharacterStatus(character: Character): Maybe<Boolean> =
        localCharacterDataSource.updateFavoriteCharacterStatus(character)

    //endregion
}
