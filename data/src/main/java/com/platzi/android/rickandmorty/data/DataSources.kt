package com.platzi.android.rickandmorty.data

import com.platzi.android.rickandmorty.domain.Character
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface RemoteCharacterDataSource {
    fun getAllCharacters(page: Int): Single<List<Character>>
}

interface LocalCharacterDataSource {

    fun getAllFavoriteCharacters(): Flowable<List<Character>>

    fun getFavoriteCharacterStatus(characterId: Int): Maybe<Boolean>

    fun updateFavoriteCharacterStatus(character: Character): Maybe<Boolean>
}

//TODO Paso 1: Crear interfaz para fuente de datos remoto de episodio (RemoteEpisodeDataSource)
//TODO Paso 1.1: Crear método "getEpisodeFromCharacter" que retorna un objeto de tipo Single<List<Episode>>
//TODO Paso 1.2: Pasar como parámetro "episodeUrlList" de tipo List<String>
