package com.yogigupta1206.flipkart_starwarsblastertournament.presentation.pokemon_info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.yogigupta1206.flipkart_starwarsblastertournament.presentation.pokemon_info.components.MatchesListItem
import com.yogigupta1206.flipkart_starwarsblastertournament.presentation.viewmodel.CommonViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerMatchesScreen(
    viewModel: CommonViewModel = hiltViewModel(),
    id: Int,
    onNavigateBack:() -> Unit
) {
    val uiState by viewModel.uiPlayerMatchesState.collectAsState()

    LaunchedEffect(key1 = id) {
        viewModel.getPlayerMatches(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.playerMap[id]?: "Player") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        },
        content = { padding ->
            Content(
                padding,
                uiState,
                id
            )
        }
    )
}


@Composable
fun Content(
    padding: PaddingValues,
    uiState: PlayerMatchesSceenUiState,
    id: Int
) {
    Column(
        modifier = Modifier.padding(padding)
    ) {
        Text( text = "Matches Table")
        LazyColumn {
            items(uiState.matchesList) {
                MatchesListItem(it, uiState.playerMap, id)
            }
        }
    }
}
