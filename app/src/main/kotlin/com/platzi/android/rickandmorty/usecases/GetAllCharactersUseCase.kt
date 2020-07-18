package com.platzi.android.rickandmorty.usecases

import com.platzi.android.rickandmorty.api.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetAllCharactersUseCase(
    private val characterRequest: CharacterRequest
) {

    fun invoke(currentPage: Int): Single<List<CharacterServer>> = characterRequest
        .getService<CharacterService>()
        .getAllCharacters(currentPage)
        .map(CharacterResponseServer::toCharacterServerList)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}
