package com.yogigupta1206.flipkart_starwarsblastertournament.domain.model

data class MatchDataItem(
    val match: Int,
    val player1: Player,
    val player2: Player
)

data class Player(
    val id: Int,
    val score: Int
)