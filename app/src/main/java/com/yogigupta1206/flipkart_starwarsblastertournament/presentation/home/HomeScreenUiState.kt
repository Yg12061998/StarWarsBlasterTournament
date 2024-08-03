package com.yogigupta1206.flipkart_starwarsblastertournament.presentation.home

import com.yogigupta1206.flipkart_starwarsblastertournament.domain.model.PlayerDataItem


data class HomeScreenUiState(
    val playerList: List<PlayerDataItem> = emptyList(),
    val pointsList: Map<Int, Int> = emptyMap(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val sortOption: SortOption = SortOption.DESC
)

enum class SortOption { ASC, DESC }
