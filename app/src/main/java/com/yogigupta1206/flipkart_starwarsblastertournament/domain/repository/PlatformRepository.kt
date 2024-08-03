package com.yogigupta1206.flipkart_starwarsblastertournament.domain.repository

import com.yogigupta1206.flipkart_starwarsblastertournament.domain.model.MatchDataItem
import com.yogigupta1206.flipkart_starwarsblastertournament.domain.model.PlayerDataItem
import com.yogigupta1206.flipkart_starwarsblastertournament.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PlatformRepository {
    fun getPlayersList(): Flow<Resource<List<PlayerDataItem>>>
    fun getMatchesList(): Flow<Resource<List<MatchDataItem>>>
}