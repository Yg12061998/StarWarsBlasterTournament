package com.yogigupta1206.flipkart_starwarsblastertournament.di

import com.yogigupta1206.flipkart_starwarsblastertournament.data.data_source.network.PlatformApis
import com.yogigupta1206.flipkart_starwarsblastertournament.data.repository.PlatformRepositoryImpl
import com.yogigupta1206.flipkart_starwarsblastertournament.domain.repository.PlatformRepository
import com.yogigupta1206.m2ppokemon.data.data_source.network.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providePlatformRepository(platformApi: PlatformApis, networkHelper: NetworkHelper) : PlatformRepository {
        return PlatformRepositoryImpl(platformApi, networkHelper)
    }

}