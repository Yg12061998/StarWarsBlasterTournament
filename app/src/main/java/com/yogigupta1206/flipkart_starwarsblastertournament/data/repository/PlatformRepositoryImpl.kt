package com.yogigupta1206.flipkart_starwarsblastertournament.data.repository

import android.util.Log
import com.yogigupta1206.flipkart_starwarsblastertournament.data.data_source.network.PlatformApis
import com.yogigupta1206.flipkart_starwarsblastertournament.domain.model.MatchDataItem
import com.yogigupta1206.flipkart_starwarsblastertournament.domain.model.PlayerDataItem
import com.yogigupta1206.flipkart_starwarsblastertournament.domain.repository.PlatformRepository
import com.yogigupta1206.flipkart_starwarsblastertournament.utils.Resource
import com.yogigupta1206.m2ppokemon.data.data_source.network.NetworkHelper
import com.yogigupta1206.m2ppokemon.data.data_source.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PlatformRepositoryImpl @Inject constructor(
    private val platformApi: PlatformApis,
    private val networkHelper: NetworkHelper
) : PlatformRepository {

    companion object{
        val TAG: String = PlatformRepositoryImpl::class.java.simpleName
    }

    override fun getPlayersList(): Flow<Resource<List<PlayerDataItem>>> = flow {
        emit(Resource.Loading())

        when(val response = getPlayerDetailsFromNetwork()){
            is NetworkResult.Error -> {
                Log.d(TAG, "getPokemonList: Error: ${response.message}")
                emit(Resource.Error(response.message ?: "Unknown error"))

            }
            is NetworkResult.Success -> {
                val playersList = response.responseData
                emit(Resource.Success(playersList ?: emptyList()))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getMatchesList(): Flow<Resource<List<MatchDataItem>>> = flow {
        emit(Resource.Loading())

        when(val response = getMatchDetailsFromNetwork()){
            is NetworkResult.Error -> {
                Log.d(TAG, "getPokemonList: Error: ${response.message}")
                emit(Resource.Error(response.message ?: "Unknown error"))

            }
            is NetworkResult.Success -> {
                val matchList = response.responseData
                emit(Resource.Success(matchList ?: emptyList()))
            }
        }
    }.flowOn(Dispatchers.IO)


    private suspend fun getPlayerDetailsFromNetwork() = networkHelper.safeApiCall { platformApi.getPlayersList() }

    private suspend fun getMatchDetailsFromNetwork() = networkHelper.safeApiCall { platformApi.getMatchesList() }



}