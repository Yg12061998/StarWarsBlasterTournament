package com.yogigupta1206.flipkart_starwarsblastertournament.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogigupta1206.flipkart_starwarsblastertournament.domain.model.MatchDataItem
import com.yogigupta1206.flipkart_starwarsblastertournament.domain.repository.PlatformRepository
import com.yogigupta1206.flipkart_starwarsblastertournament.presentation.home.HomeScreenUiState
import com.yogigupta1206.flipkart_starwarsblastertournament.presentation.home.SortOption
import com.yogigupta1206.flipkart_starwarsblastertournament.presentation.pokemon_info.PlayerMatchesSceenUiState
import com.yogigupta1206.flipkart_starwarsblastertournament.utils.MatchResult
import com.yogigupta1206.flipkart_starwarsblastertournament.utils.Resource
import com.yogigupta1206.flipkart_starwarsblastertournament.utils.getMatchesList
import com.yogigupta1206.flipkart_starwarsblastertournament.utils.matchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor(
    private val repository: PlatformRepository
) : ViewModel() {

    companion object {
        private const val TAG = "CommonViewModel"
        var count = 0
    }

    private val _uiHomeState = MutableStateFlow(HomeScreenUiState())
    val uiHomeState: StateFlow<HomeScreenUiState> = _uiHomeState.asStateFlow()

    private val _uiPlayerMatchesState = MutableStateFlow(PlayerMatchesSceenUiState())
    val uiPlayerMatchesState: StateFlow<PlayerMatchesSceenUiState> =
        _uiPlayerMatchesState.asStateFlow()

    private val _playerMatches = mutableStateOf<List<MatchDataItem>>(emptyList())
    val playerMatches: State<List<MatchDataItem>> = _playerMatches

    private var job: Job? = null

    init {
        fetchData()
        Log.d(TAG, "created: ${count++}")
    }

    fun fetchData() {
        if (job?.isActive == true) {
            job?.cancel()
        }
        job = viewModelScope.launch {
            combine(
                repository.getPlayersList(), repository.getMatchesList()
            ) { playerList, matchsList ->
                when {
                    playerList is Resource.Success && matchsList is Resource.Success -> {

                        viewModelScope.launch(Dispatchers.Default) {

                            val playerListData = playerList.data ?: emptyList()
                            val matchListData = matchsList.data ?: emptyList()
                            val playerScoreMap = mutableMapOf<Int, Int>()

                            _playerMatches.value = matchListData

                            matchListData.forEach { match ->
                                when (match.matchResult()) {
                                    MatchResult.PLAYER_ONE -> {
                                        playerScoreMap[match.player1.id] =
                                            playerScoreMap.getOrDefault(match.player1.id, 0) + 3
                                        playerScoreMap[match.player2.id] =
                                            playerScoreMap.getOrDefault(match.player2.id, 0)
                                    }

                                    MatchResult.PLAYER_TWO -> {
                                        playerScoreMap[match.player2.id] =
                                            playerScoreMap.getOrDefault(match.player2.id, 0) + 3
                                        playerScoreMap[match.player1.id] =
                                            playerScoreMap.getOrDefault(match.player1.id, 0)
                                    }

                                    MatchResult.DRAW -> {
                                        playerScoreMap[match.player1.id] =
                                            playerScoreMap.getOrDefault(match.player1.id, 0) + 1
                                        playerScoreMap[match.player2.id] =
                                            playerScoreMap.getOrDefault(match.player2.id, 0) + 1
                                    }
                                }
                            }

                            val sortedPlayerList = playerListData.sortedByDescending {
                                playerScoreMap.getOrDefault(
                                    it.id,
                                    0
                                )
                            }
                            _uiHomeState.value = _uiHomeState.value.copy(
                                playerList = sortedPlayerList,
                                pointsList = playerScoreMap,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }

                    playerList is Resource.Error || matchsList is Resource.Error -> {
                        _uiHomeState.value = _uiHomeState.value.copy(
                            isLoading = false,
                            errorMessage = if (playerList.message.isNullOrBlank()) matchsList.message else playerList.message
                        )
                    }

                    else -> {
                        _uiHomeState.value = _uiHomeState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }.distinctUntilChanged().launchIn(viewModelScope)
        }
    }

    fun onSortOptionChange(newOption: SortOption) {
        val currentUiState = _uiHomeState.value

        val sortedPlayerList = when (newOption) {
            SortOption.DESC -> currentUiState.playerList.sortedByDescending {
                currentUiState.pointsList.getOrDefault(it.id, 0)
            }
            SortOption.ASC -> currentUiState.playerList.sortedBy {
                currentUiState.pointsList.getOrDefault(it.id, 0)
            }
        }

        _uiHomeState.value = currentUiState.copy(
            playerList = sortedPlayerList,
            sortOption = newOption
        )

        _uiHomeState.value = _uiHomeState.value.copy(sortOption = newOption)
    }

    fun getPlayerMatches(playerId: Int) {
        Log.d(TAG, "getPlayerMatches: $playerId")
        viewModelScope.launch(Dispatchers.Default) {
            val playerMatches = _playerMatches.value.getMatchesList(playerId)
            val playerMap = _uiHomeState.value.playerList.associateBy({ it.id }, { it.name })
            _uiPlayerMatchesState.value = _uiPlayerMatchesState.value.copy(
                playerMap = playerMap,
                matchesList = playerMatches,
            )
        }
    }
}
