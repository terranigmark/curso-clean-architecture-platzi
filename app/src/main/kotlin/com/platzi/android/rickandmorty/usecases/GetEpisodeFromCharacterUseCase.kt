package com.platzi.android.rickandmorty.usecases

import com.platzi.android.rickandmorty.data.EpisodeRepository
import com.platzi.android.rickandmorty.domain.Episode
import io.reactivex.Single

class GetEpisodeFromCharacterUseCase(
    private val episodeRepository: EpisodeRepository
) {

    fun invoke(episodeUrlList: List<String>): Single<List<Episode>> =
        episodeRepository.getEpisodeFromCharacter(episodeUrlList)
}
