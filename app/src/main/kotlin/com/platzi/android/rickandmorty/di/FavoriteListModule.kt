package com.platzi.android.rickandmorty.di

import com.platzi.android.rickandmorty.presentation.FavoriteListViewModel
import com.platzi.android.rickandmorty.usecases.GetAllFavoriteCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class FavoriteListModule {

    @Provides
    fun favoriteListViewModelProvider(
        getAllFavoriteCharactersUseCase: GetAllFavoriteCharactersUseCase
    ) = FavoriteListViewModel(
        getAllFavoriteCharactersUseCase
    )
}

@Subcomponent(modules = [(FavoriteListModule::class)])
interface FavoriteListComponent {
    val favoriteListViewModel: FavoriteListViewModel
}
