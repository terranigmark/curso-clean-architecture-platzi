package com.platzi.android.rickandmorty.di

import com.platzi.android.rickandmorty.presentation.CharacterListViewModel
import com.platzi.android.rickandmorty.usecases.GetAllCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class CharacterListModule {

    @Provides
    fun characterListViewModelProvider(
        getAllCharactersUseCase: GetAllCharactersUseCase
    ) = CharacterListViewModel(
        getAllCharactersUseCase
    )
}

@Subcomponent(modules = [(CharacterListModule::class)])
interface CharacterListComponent {
    val characterListViewModel: CharacterListViewModel
}
