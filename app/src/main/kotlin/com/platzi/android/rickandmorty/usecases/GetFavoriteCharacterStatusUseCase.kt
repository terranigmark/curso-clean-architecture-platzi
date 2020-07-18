package com.platzi.android.rickandmorty.usecases

import com.platzi.android.rickandmorty.data.CharacterRepository
import io.reactivex.Maybe

class GetFavoriteCharacterStatusUseCase(
    private val characterRepository: CharacterRepository
) {

    fun invoke(characterId: Int): Maybe<Boolean> =
        characterRepository.getFavoriteCharacterStatus(characterId)
}
