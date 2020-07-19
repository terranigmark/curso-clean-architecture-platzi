package com.platzi.android.rickandmorty.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.platzi.android.rickandmorty.R
import com.platzi.android.rickandmorty.adapters.EpisodeListAdapter
import com.platzi.android.rickandmorty.data.*
import com.platzi.android.rickandmorty.databasemanager.CharacterDatabase
import com.platzi.android.rickandmorty.databasemanager.CharacterRoomDataSource
import com.platzi.android.rickandmorty.databinding.ActivityCharacterDetailBinding
import com.platzi.android.rickandmorty.domain.Character
import com.platzi.android.rickandmorty.imagemanager.bindCircularImageUrl
import com.platzi.android.rickandmorty.parcelables.CharacterParcelable
import com.platzi.android.rickandmorty.parcelables.toCharacterDomain
import com.platzi.android.rickandmorty.presentation.CharacterDetailViewModel
import com.platzi.android.rickandmorty.presentation.CharacterDetailViewModel.CharacterDetailNavigation
import com.platzi.android.rickandmorty.presentation.CharacterDetailViewModel.CharacterDetailNavigation.*
import com.platzi.android.rickandmorty.presentation.utils.Event
import com.platzi.android.rickandmorty.requestmanager.APIConstants.BASE_API_URL
import com.platzi.android.rickandmorty.requestmanager.CharacterRequest
import com.platzi.android.rickandmorty.requestmanager.CharacterRetrofitDataSource
import com.platzi.android.rickandmorty.requestmanager.EpisodeRequest
import com.platzi.android.rickandmorty.requestmanager.EpisodeRetrofitDataSource
import com.platzi.android.rickandmorty.usecases.GetEpisodeFromCharacterUseCase
import com.platzi.android.rickandmorty.usecases.GetFavoriteCharacterStatusUseCase
import com.platzi.android.rickandmorty.usecases.UpdateFavoriteCharacterStatusUseCase
import com.platzi.android.rickandmorty.utils.Constants
import com.platzi.android.rickandmorty.utils.getViewModel
import com.platzi.android.rickandmorty.utils.showLongToast
import kotlinx.android.synthetic.main.activity_character_detail.*

class CharacterDetailActivity: AppCompatActivity() {

    //region Fields

    private lateinit var episodeListAdapter: EpisodeListAdapter
    private lateinit var binding: ActivityCharacterDetailBinding
    //TODO Paso 11: Crear variable "characterDetailComponent" de tipo CharacterDetailComponent

    //TODO Paso 12: Eliminar variable "episodeRequest"
    private val episodeRequest: EpisodeRequest by lazy {
        EpisodeRequest(BASE_API_URL)
    }

    //TODO Paso 13: Eliminar variable "characterRequest"
    private val characterRequest: CharacterRequest by lazy {
        CharacterRequest(BASE_API_URL)
    }

    //TODO Paso 14: Eliminar variable "localCharacterDataSource"
    private val localCharacterDataSource: LocalCharacterDataSource by lazy {
        CharacterRoomDataSource(CharacterDatabase.getDatabase(applicationContext))
    }

    //TODO Paso 15: Eliminar variable "remoteCharacterDataSource"
    private val remoteCharacterDataSource: RemoteCharacterDataSource by lazy {
        CharacterRetrofitDataSource(characterRequest)
    }

    //TODO Paso 16: Eliminar variable "characterRepository"
    private val characterRepository: CharacterRepository by lazy {
        CharacterRepository(remoteCharacterDataSource, localCharacterDataSource)
    }

    //TODO Paso 17: Eliminar variable "remoteEpisodeDataSource"
    private val remoteEpisodeDataSource: RemoteEpisodeDataSource by lazy {
        EpisodeRetrofitDataSource(episodeRequest)
    }

    //TODO Paso 18: Eliminar variable "episodeRepository"
    private val episodeRepository: EpisodeRepository by lazy {
        EpisodeRepository(remoteEpisodeDataSource)
    }

    //TODO Paso 19: Eliminar variable "getEpisodeFromCharacterUseCase"
    private val getEpisodeFromCharacterUseCase: GetEpisodeFromCharacterUseCase by lazy {
        GetEpisodeFromCharacterUseCase(episodeRepository)
    }

    //TODO Paso 20: Eliminar variable "getFavoriteCharacterStatusUseCase"
    private val getFavoriteCharacterStatusUseCase: GetFavoriteCharacterStatusUseCase by lazy {
        GetFavoriteCharacterStatusUseCase(characterRepository)
    }

    //TODO Paso 21: Eliminar variable "updateFavoriteCharacterStatusUseCase"
    private val updateFavoriteCharacterStatusUseCase: UpdateFavoriteCharacterStatusUseCase by lazy {
        UpdateFavoriteCharacterStatusUseCase(characterRepository)
    }

    //TODO Paso 22: Reemplazar la lógica del método "getViewModel" utilizando el componente "characterDetailComponent"
    private val characterDetailViewModel: CharacterDetailViewModel by lazy {
        getViewModel { CharacterDetailViewModel(
            intent.getParcelableExtra<CharacterParcelable>(Constants.EXTRA_CHARACTER)?.toCharacterDomain(),
            getEpisodeFromCharacterUseCase,
            getFavoriteCharacterStatusUseCase,
            updateFavoriteCharacterStatusUseCase
        ) }
    }

    //endregion

    //region Override Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO Paso 23: Reemplazar la lógica del método "getViewModel" utilizando el componente "characterDetailComponent"
        //TODO Paso 23.1: Pasar como parámetro de "CharacterDetailModule" la implementación del parcelable que fue reemplazado en el Paso 21

        binding = DataBindingUtil.setContentView(this, R.layout.activity_character_detail)
        binding.lifecycleOwner = this@CharacterDetailActivity

        episodeListAdapter = EpisodeListAdapter { episode ->
            this@CharacterDetailActivity.showLongToast("Episode -> $episode")
        }
        rvEpisodeList.adapter = episodeListAdapter

        characterFavorite.setOnClickListener { characterDetailViewModel.onUpdateFavoriteCharacterStatus() }

        characterDetailViewModel.characterValues.observe(this, Observer(this::loadCharacter))
        characterDetailViewModel.isFavorite.observe(this, Observer(this::updateFavoriteIcon))
        characterDetailViewModel.events.observe(this, Observer(this::validateEvents))

        characterDetailViewModel.onCharacterValidation()
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

    private fun loadCharacter(character: Character){
        binding.characterImage.bindCircularImageUrl(
            url = character.image,
            placeholder = R.drawable.ic_camera_alt_black,
            errorPlaceholder = R.drawable.ic_broken_image_black
        )
        binding.characterDataName = character.name
        binding.characterDataStatus = character.status
        binding.characterDataSpecies = character.species
        binding.characterDataGender = character.gender
        binding.characterDataOriginName = character.origin.name
        binding.characterDataLocationName = character.location.name
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
