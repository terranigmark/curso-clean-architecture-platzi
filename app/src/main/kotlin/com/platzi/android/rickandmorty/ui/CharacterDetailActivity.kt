package com.platzi.android.rickandmorty.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.platzi.android.rickandmorty.R
import com.platzi.android.rickandmorty.adapters.EpisodeListAdapter
import com.platzi.android.rickandmorty.api.APIConstants.BASE_API_URL
import com.platzi.android.rickandmorty.api.CharacterServer
import com.platzi.android.rickandmorty.api.EpisodeRequest
import com.platzi.android.rickandmorty.api.EpisodeService
import com.platzi.android.rickandmorty.api.toCharacterEntity
import com.platzi.android.rickandmorty.database.CharacterDao
import com.platzi.android.rickandmorty.database.CharacterDatabase
import com.platzi.android.rickandmorty.database.CharacterEntity
import com.platzi.android.rickandmorty.databinding.ActivityCharacterDetailBinding
import com.platzi.android.rickandmorty.presentation.CharacterDetailViewModel.CharacterDetailNavigation
import com.platzi.android.rickandmorty.presentation.CharacterDetailViewModel.CharacterDetailNavigation.*
import com.platzi.android.rickandmorty.presentation.utils.Event
import com.platzi.android.rickandmorty.utils.Constants
import com.platzi.android.rickandmorty.utils.bindCircularImageUrl
import com.platzi.android.rickandmorty.utils.showLongToast
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_character_detail.*

class CharacterDetailActivity: AppCompatActivity() {

    //region Fields

    //TODO Paso 18: Eliminar variable innecesaria en el Activity
    private val disposable = CompositeDisposable()

    private lateinit var episodeListAdapter: EpisodeListAdapter
    private lateinit var binding: ActivityCharacterDetailBinding
    //TODO Paso 19: Modificar la inicialización de la variable "episodeRequest" usando la función lazy
    private lateinit var episodeRequest: EpisodeRequest

    //TODO Paso 20: Modificar la inicialización de la variable "characterDao" usando la función lazy
    private lateinit var  characterDao: CharacterDao

    //TODO Paso 21: Crear la variable "characterDetailViewModel" de tipo CharacterDetailViewModel usando la función lazy
    //TODO Paso 22: Para como parámetro "intent.getParcelableExtra(Constants.EXTRA_CHARACTER)"
    //TODO Paso 23: Para como parámetro "characterDao"
    //TODO Paso 24: Para como parámetro "episodeRequest"

    //TODO Paso 25: Eliminar variable innecesaria en el Activity
    private var character: CharacterServer? = null

    //endregion

    //region Override Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_character_detail)
        binding.lifecycleOwner = this@CharacterDetailActivity

        episodeListAdapter = EpisodeListAdapter { episode ->
            this@CharacterDetailActivity.showLongToast("Episode -> $episode")
        }
        rvEpisodeList.adapter = episodeListAdapter

        //TODO Paso 26: Eliminar inicialización y validación de variable "character"
        character = intent.getParcelableExtra(Constants.EXTRA_CHARACTER)
        if(character == null){
            this@CharacterDetailActivity.showLongToast(R.string.error_no_character_data)
            finish()
            return
        }

        //TODO Paso 27: Eliminar inicialización de variable "episodeRequest"
        episodeRequest = EpisodeRequest(BASE_API_URL)
        //TODO Paso 28: Eliminar inicialización de variable "characterDao"
        characterDao = CharacterDatabase.getDatabase(application).characterDao()

        //TODO Paso 29: Eliminar uso de método "onValidateFavoriteCharacterStatus"
        onValidateFavoriteCharacterStatus()

        //TODO Paso 30: Mover inicialización de valores de binding al método "loadCharacter" (Nota: su uso se implementará a partir de la variable de tipo LiveData del Paso 3)
        binding.characterImage.bindCircularImageUrl(
            url = character!!.image,
            placeholder = R.drawable.ic_camera_alt_black,
            errorPlaceholder = R.drawable.ic_broken_image_black
        )
        binding.characterDataName = character!!.name
        binding.characterDataStatus = character!!.status
        binding.characterDataSpecies = character!!.species
        binding.characterDataGender = character!!.gender
        binding.characterDataOriginName = character!!.origin.name
        binding.characterDataLocationName = character!!.location.name

        //TODO Paso 31: Eliminar uso de método "onShowEpisodeList"
        onShowEpisodeList(character!!.episodeList)

        //TODO Paso 32: Usar el método "onUpdateFavoriteCharacterStatus" implementado en el view model
        characterFavorite.setOnClickListener { onUpdateFavoriteCharacterStatus() }

        //Nota: Para trabajar el resultado de cada observer se sugiere llamar al método mediante referencia (Ejemplo -> this::callMethod)
        //TODO Paso 33: Implementar el método observer del LiveData implementado en el Paso 3 y trabajar el resultado del observer en el método "loadCharacter"
        //TODO Paso 34: Implementar el método observer del LiveData implementado en el Paso 4 y trabajar el resultado del observer en el método "updateFavoriteIcon"
        //TODO Paso 35: Implementar el método observer del LiveData implementado en el Paso 5 y trabajar el resultado del observer en el método "validateEvents"
        //TODO Paso 36: Usar el método "onCharacterValidation" implementado en el view model
    }

    //TODO Paso 37: Eliminar método innecesario
    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    //endregion

    //region Private Methods

    //TODO Paso 38: Eliminar método "onValidateFavoriteCharacterStatus" innecesario en el Activity
    private fun onValidateFavoriteCharacterStatus(){
        disposable.add(
            characterDao.getCharacterById(character!!.id)
                .isEmpty
                .flatMapMaybe { isEmpty ->
                    Maybe.just(!isEmpty)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { isFavorite ->
                    updateFavoriteIcon(isFavorite)
                }
        )
    }

    //TODO Paso 39: Eliminar método "onShowEpisodeList" innecesario en el Activity
    private fun onShowEpisodeList(episodeUrlList: List<String>){
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
                    episodeProgressBar.isVisible = true
                }
                .subscribe(
                    { episodeList ->
                        episodeProgressBar.isVisible = false
                        episodeListAdapter.updateData(episodeList)
                    },
                    { error ->
                        episodeProgressBar.isVisible = false
                        this@CharacterDetailActivity.showLongToast("Error -> ${error.message}")
                    })
        )
    }

    //TODO Paso 40: Eliminar método "onUpdateFavoriteCharacterStatus" innecesario en el Activity
    private fun onUpdateFavoriteCharacterStatus() {
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
                    updateFavoriteIcon(isFavorite)
                }
        )
    }

    //TODO Paso 41: Inicializar los valores de la variable "binding" utilizando de referencia el paso 30
    private fun loadCharacter(character: CharacterServer){

    }

    private fun updateFavoriteIcon(isFavorite: Boolean?){
        characterFavorite.setImageResource(
            if (isFavorite != null && isFavorite) {
                R.drawable.ic_favorite
            } else {
                R.drawable.ic_favorite_border
            }
        )
    }

    private fun validateEvents(event: Event<CharacterDetailNavigation>?) {
        event?.getContentIfNotHandled()?.let { navigation ->
            when (navigation) {
                is ShowEpisodeError -> navigation.run {
                    this@CharacterDetailActivity.showLongToast("Error -> ${error.message}")
                }
                is ShowEpisodeList -> navigation.run {
                    episodeListAdapter.updateData(episodeList)
                }
                CloseActivity -> {
                    this@CharacterDetailActivity.showLongToast(R.string.error_no_character_data)
                    finish()
                }
                HideEpisodeListLoading -> {
                    episodeProgressBar.isVisible = false
                }
                ShowEpisodeListLoading -> {
                    episodeProgressBar.isVisible = true
                }
            }
        }
    }

    //endregion
}
