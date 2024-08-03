package com.yogigupta1206.flipkart_starwarsblastertournament.ui.component


sealed class Screens(val route: String) {
    data object HomePage : Screens("homepage")
    data object PlayerMatchesPage : Screens("playersPage")
}