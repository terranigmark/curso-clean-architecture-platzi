package com.platzi.android.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.platzi.android.rickandmorty.api.CharacterServer
import com.platzi.android.rickandmorty.api.EpisodeServer
import com.platzi.android.rickandmorty.api.toCharacterEntity
import com.platzi.android.rickandmorty.database.CharacterDao
import com.platzi.android.rickandmorty.database.CharacterEntity
import com.platzi.android.rickandmorty.presentation.CharacterDetailViewModel.CharacterDetailNavigation.*
import com.platzi.android.rickandmorty.presentation.utils.Event
import com.platzi.android.rickandmorty.usecases.GetEpisodeFromCharacterUseCase
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

//TODO Paso 7: Eliminar variable "characterDao" de tipo CharacterDao
//TODO Paso 8: Agregar variable "getFavoriteCharacterStatusUseCase" de tipo GetFavoriteCharacterStatusUseCase
//TODO Paso 9: Agregar variable "updateFavoriteCharacterStatusUseCase" de tipo UpdateFavoriteCharacterStatusUseCase
class CharacterDetailViewModel(
    private val character: CharacterServer? = null,
    private val characterDao: CharacterDao,
    private val getEpisodeFromCharacterUseCase: GetEpisodeFromCharacterUseCase
) : ViewModel() {

    //region Fields

    private val disposable = CompositeDisposable()

    private val _characterValues = MutableLiveData<CharacterServer>()
    val characterValues: LiveData<CharacterServer> get() = _characterValues

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val _events = MutableLiveData<Event<CharacterDetailNavigation>>()
    val events: LiveData<Event<CharacterDetailNavigation>> get() = _events

    //endregion

    //region Override Methods & Callbacks

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    //endregion

    //region Public Methods

    fun onCharacterValidation() {
        if (character == null) {
            _events.value = Event(CloseActivity)
            return
        }

        _characterValues.value = character

        validateFavoriteCharacterStatus(character.id)
        requestShowEpisodeList(character.episodeList)
    }

    //TODO Paso 10: Reemplazar el uso de la variable "characterDao" y reemplazarlo por su caso de uso
    //TODO Paso 10.1: Eliminar el uso de la variable "characterDao"
    //TODO Paso 10.2: Eliminar el uso del método "getCharacterById"
    //TODO Paso 10.3: Eliminar el uso del método "isEmpty"
    //TODO Paso 10.4: Eliminar el uso del método "flatMapMaybe"
    //TODO Paso 10.5: Eliminar el uso del método "observeOn"
    //TODO Paso 10.6: Eliminar el uso del método "subscribeOn"
    //TODO Paso 10.7: Implementar variable "updateFavoriteCharacterStatusUseCase"
    //TODO Paso 10.8: Pasar la variable "characterEntity" al método "invoke" del caso de uso
    fun onUpdateFavoriteCharacterStatus() {
        val characterEntity: CharacterEntity = character!!.toCharacterEntity()
        disposable.add(
            characterDao.getCharacterById(characterEntity.id)
                .isEmpty
                .flatMapMaybe { isEmpty ->
                    if(isEmpty){
                        characterDao.insertCharacter(characterEntity)
                    }else{
                        characterDao.deleteCharacter(characterEntity)
                    }
                    Maybe.just(isEmpty)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { isFavorite ->
                    _isFavorite.value = isFavorite
                }
        )
    }

    //endregion

    //region Private Methods

    //TODO Paso 11: Reemplazar el uso de la variable "characterDao" y reemplazarlo por su caso de uso
    //TODO Paso 11.1: Eliminar el uso de la variable "characterDao"
    //TODO Paso 11.2: Eliminar el uso del método "getCharacterById"
    //TODO Paso 11.3: Eliminar el uso del método "isEmpty"
    //TODO Paso 11.4: Eliminar el uso del método "flatMapMaybe"
    //TODO Paso 11.5: Eliminar el uso del método "observeOn"
    //TODO Paso 11.6: Eliminar el uso del método "subscribeOn"
    //TODO Paso 11.7: Implementar variable "getFavoriteCharacterStatusUseCase"
    //TODO Paso 11.8: Pasar la variable "characterId" al método "invoke" del caso de uso
    private fun validateFavoriteCharacterStatus(characterId: Int){
        disposable.add(
            characterDao.getCharacterById(characterId)
                .isEmpty
                .flatMapMaybe { isEmpty ->
                    Maybe.just(!isEmpty)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { isFavorite ->
                    _isFavorite.value = isFavorite
                }
        )
    }

    private fun requestShowEpisodeList(episodeUrlList: List<String>){
        disposable.add(
            getEpisodeFromCharacterUseCase
                .invoke(episodeUrlList)
                .doOnSubscribe {
                    _events.value = Event(ShowEpisodeListLoading)
                }
                .subscribe(
                    { episodeList ->
                        _events.value = Event(HideEpisodeListLoading)
                        _events.value = Event(ShowEpisodeList(episodeList))
                    },
                    { error ->
                        _events.value = Event(HideEpisodeListLoading)
                        _events.value = Event(ShowEpisodeError(error))
                    })
        )
    }

    //endregion

    //region Inner Classes & Interfaces

    sealed class CharacterDetailNavigation {
        data class ShowEpisodeError(val error: Throwable) : CharacterDetailNavigation()
        data class ShowEpisodeList(val episodeList: List<EpisodeServer>) : CharacterDetailNavigation()
        object CloseActivity : CharacterDetailNavigation()
        object HideEpisodeListLoading : CharacterDetailNavigation()
        object ShowEpisodeListLoading : CharacterDetailNavigation()
    }

    //endregion

}
