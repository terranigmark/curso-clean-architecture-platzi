package com.platzi.android.rickandmorty.data

import com.platzi.android.rickandmorty.domain.Character
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

class CharacterRepository(
    private val remoteCharacterDataSource: RemoteCharacterDataSource
) {

    //region Public Methods

    fun getAllCharacters(page: Int): Single<List<Character>> =
        remoteCharacterDataSource.getAllCharacters(page)

    //endregion
}
