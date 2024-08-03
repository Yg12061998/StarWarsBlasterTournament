package com.yogigupta1206.flipkart_starwarsblastertournament.presentation.pokemon_info.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yogigupta1206.flipkart_starwarsblastertournament.domain.model.MatchDataItem
import com.yogigupta1206.flipkart_starwarsblastertournament.utils.color

@Composable
fun MatchesListItem(matcheData: MatchDataItem, playerMap: Map<Int, String>, id: Int) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .background(matcheData.color(id))
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically

        ) {
            CenterAlignedRowWithCornerTexts(
                leftText = playerMap[matcheData.player1.id] ?: "Player1",
                centerText = "${matcheData.player1.score} - ${matcheData.player2.score}",
                rightText = playerMap[matcheData.player2.id] ?: "Player2"
            )
        }
    }
}

@Composable
fun CenterAlignedRowWithCornerTexts(
    leftText: String,
    centerText: String,
    rightText: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth() // Make the row occupy the full width
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = leftText, modifier = Modifier.weight(1f)) // Left text, taking available space

        Text(
            text = centerText,
            modifier = Modifier.weight(1f), // Center text, taking available space
            textAlign = TextAlign.Center // Align the center text
        )

        Text(text = rightText, modifier = Modifier.weight(1f)) // Right text, taking available space
    }
}