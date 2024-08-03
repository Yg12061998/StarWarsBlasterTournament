package com.yogigupta1206.flipkart_starwarsblastertournament.utils

import androidx.compose.ui.graphics.Color
import com.yogigupta1206.flipkart_starwarsblastertournament.domain.model.MatchDataItem

fun MatchDataItem.matchResult():MatchResult {
    return if(this.player1.score > this.player2.score){
        MatchResult.PLAYER_ONE
    }else if(this.player1.score < this.player2.score){
        MatchResult.PLAYER_TWO
    }else{
        MatchResult.DRAW
    }

}

fun List<MatchDataItem>.getMatchesList(playerId: Int):List<MatchDataItem>{
    return this.filter { it.player1.id == playerId || it.player2.id == playerId }
}

fun MatchDataItem.color(id: Int): Color {
    if (this.player1.score == this.player2.score) {
        return Color.Companion.Transparent
    } else if (this.player1.score > this.player2.score) {
        return if (this.player1.id == id) {
            Color.Companion.Green
        } else {
            Color.Companion.Red
        }
    }else{
        return if (this.player2.id == id) {
            Color.Companion.Green
        } else {
            Color.Companion.Red
        }
    }


}
enum class MatchResult {
    PLAYER_ONE, PLAYER_TWO , DRAW
}