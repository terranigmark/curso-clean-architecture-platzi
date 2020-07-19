package com.platzi.android.rickandmorty.usecases.di

import com.platzi.android.rickandmorty.data.CharacterRepository
import com.platzi.android.rickandmorty.usecases.GetAllCharactersUseCase
import com.platzi.android.rickandmorty.usecases.GetAllFavoriteCharactersUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun getAllCharacterUseCaseProvider(characterRepository: CharacterRepository) =
        GetAllCharactersUseCase(characterRepository)

    @Provides
    fun getAllFavoriteCharactersUseCaseProvider(characterRepository: CharacterRepository) =
        GetAllFavoriteCharactersUseCase(characterRepository)
}
