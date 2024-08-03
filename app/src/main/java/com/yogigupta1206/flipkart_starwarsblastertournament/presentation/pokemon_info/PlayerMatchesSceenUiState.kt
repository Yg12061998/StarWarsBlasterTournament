package com.yogigupta1206.flipkart_starwarsblastertournament.presentation.pokemon_info

import com.yogigupta1206.flipkart_starwarsblastertournament.domain.model.MatchDataItem

data class PlayerMatchesSceenUiState(
    val playerMap: Map<Int, String> = emptyMap(),
    val matchesList: List<MatchDataItem> = emptyList(),
)
