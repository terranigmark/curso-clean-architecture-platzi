package com.platzi.android.rickandmorty.presentation

import androidx.lifecycle.ViewModel
import com.platzi.android.rickandmorty.api.EpisodeServer
import com.platzi.android.rickandmorty.api.EpisodeService
import com.platzi.android.rickandmorty.database.CharacterEntity
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

//TODO Paso 1: Pasar como parámetros "character" de tipo CharacterServer?, "characterDao" de tipo CharacterDao y "episodeRequest" de tipo EpisodeRequest
class CharacterDetailViewModel(

) : ViewModel() {

    //region Fields

    //TODO Paso 2: Declarar la variable "disposable" de tipo CompositeDisposable

    //TODO Paso 3: Crear las variables de tipo MutableLiveData y LiveData para manejar los valores del personaje (tipo sugerido: CharacterServer)

    //TODO Paso 4: Crear las variables de tipo MutableLiveData y LiveData para manejar el estado de favorito de un personaje (tipo sugerido: Boolean)

    //TODO Paso 5: Crear las variables de tipo MutableLiveData y LiveData para manejar los eventos del view model (tipo sugerido: CharacterDetailNavigation)

    //endregion

    //region Override Methods & Callbacks

    //TODO Paso 6: Limpiar la variable "disposable" cuando el view model borre los recursos
    override fun onCleared() {
        super.onCleared()
    }

    //endregion

    //region Public Methods

    fun onCharacterValidation() {
        //TODO Paso 7: Si el parámetro "character" es nulo, se debe disparar el evento de cerrar actividad y finalizar la ejecución de este método (Usar CharacterDetailNavigation)
        //TODO Paso 8: Si el parámetro "character" no es nulo, se debe disparar el evento de cargar el personaje (Usar CharacterDetailNavigation)
        //TODO Paso 9: Si el parámetro "character" no es nulo, se debe llamar al método "validateFavoriteCharacterStatus" el cual valida el estatus del personaje favorito
        //TODO Paso 10: Si el parámetro "character" no es nulo, se debe llamar al método "requestShowEpisodeList" el cual hace una petición para devolver el listado de episodios del personaje
    }

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
                    //TODO Paso 11: Disparar la variable de tipo MutableLiveData que se implementó en el Paso 3 con la variable "isFavorite"
                }
        )
    }

    //endregion

    //region Private Methods

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
                    //TODO Paso 12: Disparar la variable de tipo MutableLiveData que se implementó en el Paso 3 con la variable "isFavorite"
                }
        )
    }

    private fun requestShowEpisodeList(episodeUrlList: List<String>){
        disposable.add(
            Observable.fromIterable(episodeUrlList)
                .flatMap { episode: String ->
                    episodeRequest.baseUrl = episode
                    episodeRequest
                        .getService<EpisodeService>()
                        .getEpisode()
                        .toObservable()
                }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    //TODO Paso 13: Disparar el evento de mostrar el progreso de carga de la lista de episodio (Usar CharacterDetailNavigation)
                }
                .subscribe(
                    { episodeList ->
                        //TODO Paso 14: Disparar el evento de ocultar el progreso de carga de la lista de episodio (Usar CharacterDetailNavigation)
                        //TODO Paso 15: Disparar el evento de mostrar la lista de episodio (Usar CharacterDetailNavigation)
                    },
                    { error ->
                        //TODO Paso 16: Disparar el evento de ocultar el progreso de carga de la lista de episodio (Usar CharacterDetailNavigation)
                        //TODO Paso 17: Disparar el evento de mostrar error al cargar los episodios (Usar CharacterDetailNavigation)
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
