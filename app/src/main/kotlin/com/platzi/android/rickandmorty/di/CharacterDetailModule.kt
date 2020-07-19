package com.platzi.android.rickandmorty.di

import com.platzi.android.rickandmorty.domain.Character
import com.platzi.android.rickandmorty.presentation.CharacterDetailViewModel
import com.platzi.android.rickandmorty.usecases.GetEpisodeFromCharacterUseCase
import com.platzi.android.rickandmorty.usecases.GetFavoriteCharacterStatusUseCase
import com.platzi.android.rickandmorty.usecases.UpdateFavoriteCharacterStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class CharacterDetailModule(
    private val character: Character?
) {

    @Provides
    fun characterDetailViewModelProvider(
        getEpisodeFromCharacterUseCase: GetEpisodeFromCharacterUseCase,
        getFavoriteCharacterStatusUseCase: GetFavoriteCharacterStatusUseCase,
        updateFavoriteCharacterStatusUseCase: UpdateFavoriteCharacterStatusUseCase
    ) = CharacterDetailViewModel(
        character,
        getEpisodeFromCharacterUseCase,
        getFavoriteCharacterStatusUseCase,
        updateFavoriteCharacterStatusUseCase
    )
}

@Subcomponent(modules = [(CharacterDetailModule::class)])
interface CharacterDetailComponent {
    val characterDetailViewModel: CharacterDetailViewModel
}
