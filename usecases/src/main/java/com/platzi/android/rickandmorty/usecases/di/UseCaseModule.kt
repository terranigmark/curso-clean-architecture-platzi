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

    //TODO Paso 4: Crear el método para proveer el caso de uso "GetFavoriteCharacterStatusUseCase"

    //TODO Paso 5: Crear el método para proveer el caso de uso "UpdateFavoriteCharacterStatusUseCase"

    //TODO Paso 6: Crear el método para proveer el caso de uso "GetEpisodeFromCharacterUseCase"
}
