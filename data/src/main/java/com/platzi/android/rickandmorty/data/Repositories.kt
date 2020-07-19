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

//TODO Paso 2: Pasar como parámetro "remoteEpisodeDataSource" de tipo RemoteEpisodeDataSource
class EpisodeRepository(

) {

    //region Public Methods

    //TODO Paso 3: Crear método "getEpisodeFromCharacter" que retorna un objeto de tipo Single<List<Episode>>
    //TODO Paso 3.1: Pasar como parámetro "episodeUrlList" de tipo List<String>
    //TODO Paso 3.2: Devolver el método "getEpisodeFromCharacter" del parámetro "remoteEpisodeDataSource"

    //endregion
}
