package com.platzi.android.rickandmorty.usecases.di

import com.platzi.android.rickandmorty.data.CharacterRepository
import com.platzi.android.rickandmorty.data.EpisodeRepository
import com.platzi.android.rickandmorty.usecases.*
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

    @Provides
    fun getFavoriteCharacterStatusUseCaseProvider(characterRepository: CharacterRepository) =
        GetFavoriteCharacterStatusUseCase(characterRepository)

    @Provides
    fun updateFavoriteCharacterStatusUseCaseProvider(characterRepository: CharacterRepository) =
        UpdateFavoriteCharacterStatusUseCase(characterRepository)

    @Provides
    fun getEpisodeFromCharacterUseCaseProvider(episodeRepository: EpisodeRepository) =
        GetEpisodeFromCharacterUseCase(episodeRepository)
}
