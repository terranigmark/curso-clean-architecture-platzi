package com.platzi.android.rickandmorty.usecases

import com.platzi.android.rickandmorty.api.EpisodeRequest
import com.platzi.android.rickandmorty.api.EpisodeServer
import com.platzi.android.rickandmorty.api.EpisodeService
import com.platzi.android.rickandmorty.api.toEpisodeDomain
import com.platzi.android.rickandmorty.domain.Episode
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

//TODO Paso 6: Cambiar parámetro "episodeRequest" de tipo EpisodeRequest por parámetro "episodeRepository" de tipo EpisodeRepository
class GetEpisodeFromCharacterUseCase(
    private val episodeRequest: EpisodeRequest
) {

    //TODO Paso 7: Devolver el método "getEpisodeFromCharacter" del parámetro "episodeRepository"
    fun invoke(episodeUrlList: List<String>): Single<List<Episode>> {
        return Observable.fromIterable(episodeUrlList)
            .flatMap { episode: String ->
                episodeRequest.baseUrl = episode
                episodeRequest
                    .getService<EpisodeService>()
                    .getEpisode()
                    .map(EpisodeServer::toEpisodeDomain)
                    .toObservable()
            }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}
