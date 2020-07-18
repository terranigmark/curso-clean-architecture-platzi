package com.platzi.android.rickandmorty.usecases

import io.reactivex.Maybe

//TODO Paso 1: Pasar como parámetros "characterDao" de tipo CharacterDao
class GetFavoriteCharacterStatusUseCase(

) {

    //TODO Paso 2: Crear método "invoke"
    //TODO Paso 2.1: Pasar como parámetro "characterId" de tipo Int
    //TODO Paso 2.2: Indicar que el método devuelve un valor de tipo Maybe<Boolean>
    //TODO Paso 3: Migrar a este método la implementación del método "validateFavoriteCharacterStatus" en la clase CharacterDetailViewModel
    //TODO Paso 3.1: Migrar el uso de la variable "characterDao"
    //TODO Paso 3.2: Migrar el uso del método "getCharacterById"
    //TODO Paso 3.3: Migrar el uso del método "isEmpty"
    //TODO Paso 3.4: Migrar el uso del método "flatMapMaybe"
    //TODO Paso 3.5: Migrar el uso del método "observeOn"
    //TODO Paso 3.6: Migrar el uso del método "subscribeOn"
    fun invoke(characterId: Int): Maybe<Boolean> {
        TODO()
    }
}
