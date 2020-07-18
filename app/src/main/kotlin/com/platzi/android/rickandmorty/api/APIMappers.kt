package com.platzi.android.rickandmorty.api

import com.platzi.android.rickandmorty.database.CharacterEntity
import com.platzi.android.rickandmorty.database.LocationEntity
import com.platzi.android.rickandmorty.database.OriginEntity

fun CharacterResponseServer.toCharacterServerList(): List<CharacterServer> = results.map {
    it.run{
        CharacterServer(
            id,
            name,
            image,
            gender,
            species,
            status,
            origin,
            location,
            episodeList.map { episode -> "$episode/" }
        )
    }
}

fun CharacterServer.toCharacterEntity() = CharacterEntity(
    id,
    name,
    image,
    gender,
    species,
    status,
    origin.toOriginEntity(),
    location.toLocationEntity(),
    episodeList
)

fun OriginServer.toOriginEntity() = OriginEntity(
    name,
    url
)

fun LocationServer.toLocationEntity() = LocationEntity(
    name,
    url
)
