package com.platzi.android.rickandmorty.domain

data class Character(
    val id: Int,
    val name: String,
    val image: String?,
    val gender: String,
    val species: String,
    val status: String,
    val origin: Origin,
    val location: Location,
    val episodeList: List<String>
)

data class Location(
    val name: String,
    val url: String
)

data class Origin(
    val name: String,
    val url: String
)

//TODO Paso 1: Crear entidad de episodio
//TODO Paso 1.1: Agregar como parámetro "id" de tipo Int
//TODO Paso 1.2: Agregar como parámetro "name" de tipo String
