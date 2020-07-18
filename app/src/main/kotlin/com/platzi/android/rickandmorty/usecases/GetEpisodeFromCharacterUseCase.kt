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

class GetEpisodeFromCharacterUseCase(
    private val episodeRequest: EpisodeRequest
) {

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
