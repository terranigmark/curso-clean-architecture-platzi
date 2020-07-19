package com.platzi.android.rickandmorty.requestmanager

import com.platzi.android.rickandmorty.requestmanager.APIConstants.ENDPOINT_CHARACTER
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET(ENDPOINT_CHARACTER)
    fun getAllCharacters(
        @Query("page") page: Int
    ): Single<CharacterResponseServer>
}

interface EpisodeService {

    @GET(".")
    fun getEpisode(): Single<EpisodeServer>
}
