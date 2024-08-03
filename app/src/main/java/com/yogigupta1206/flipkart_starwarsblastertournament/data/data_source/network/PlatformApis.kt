package com.yogigupta1206.flipkart_starwarsblastertournament.data.data_source.network

import com.yogigupta1206.flipkart_starwarsblastertournament.domain.model.MatchDataItem
import com.yogigupta1206.flipkart_starwarsblastertournament.domain.model.PlayerDataItem
import retrofit2.Response
import retrofit2.http.GET

interface PlatformApis {

    @GET("b/IKQQ")
    suspend fun getPlayersList(): Response<List<PlayerDataItem>>

    @GET("b/JNYL")
    suspend fun getMatchesList(): Response<List<MatchDataItem>>
}