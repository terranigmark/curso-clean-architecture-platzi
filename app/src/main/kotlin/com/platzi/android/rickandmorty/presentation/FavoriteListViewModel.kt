package com.platzi.android.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.platzi.android.rickandmorty.database.CharacterDao
import com.platzi.android.rickandmorty.database.CharacterEntity
import com.platzi.android.rickandmorty.presentation.FavoriteListViewModel.FavoriteListNavigation.ShowCharacterList
import com.platzi.android.rickandmorty.presentation.FavoriteListViewModel.FavoriteListNavigation.ShowEmptyListMessage
import com.platzi.android.rickandmorty.presentation.utils.Event
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoriteListViewModel (
    private val characterDao: CharacterDao
) : ViewModel(){

    //region Fields

    private val disposable = CompositeDisposable()

    private val _events = MutableLiveData<Event<FavoriteListNavigation>>()
    val events: LiveData<Event<FavoriteListNavigation>> get() = _events

    private val _favoriteCharacterList: LiveData<List<CharacterEntity>>
        get() = LiveDataReactiveStreams.fromPublisher(
            characterDao
                .getAllFavoriteCharacters()
                .onErrorReturn { emptyList() }
                .subscribeOn(Schedulers.io())
        )
    val favoriteCharacterList: LiveData<List<CharacterEntity>>
        get() = _favoriteCharacterList

    //endregion

    //region Override Methods & Callbacks

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    //endregion

    //region Public Methods

    fun onFavoriteCharacterList(favoriteCharacterList: List<CharacterEntity>) {
        if (favoriteCharacterList.isEmpty()) {
            _events.value = Event(ShowCharacterList(emptyList()))
            _events.value = Event(ShowEmptyListMessage)
            return
        }

        _events.value = Event(ShowCharacterList(favoriteCharacterList))
    }

    //endregion

    //region Inner Classes & Interfaces

    sealed class FavoriteListNavigation {
        data class ShowCharacterList(val characterList: List<CharacterEntity>) : FavoriteListNavigation()
        object ShowEmptyListMessage : FavoriteListNavigation()
    }

    //endregion

}
