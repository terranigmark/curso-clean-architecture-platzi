package com.platzi.android.rickandmorty.database

import com.platzi.android.rickandmorty.api.CharacterServer
import com.platzi.android.rickandmorty.api.LocationServer
import com.platzi.android.rickandmorty.api.OriginServer

fun CharacterEntity.toCharacterServer() = CharacterServer(
    id,
    name,
    image,
    gender,
    species,
    status,
    origin.toOriginServer(),
    location.toLocationServer(),
    episodeList
)

fun OriginEntity.toOriginServer() = OriginServer(
    originName,
    originUrl
)

fun LocationEntity.toLocationServer() = LocationServer(
    locationName,
    locationUrl
)
