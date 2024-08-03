package com.yogigupta1206.flipkart_starwarsblastertournament.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yogigupta1206.flipkart_starwarsblastertournament.presentation.home.HomeScreen
import com.yogigupta1206.flipkart_starwarsblastertournament.presentation.pokemon_info.PlayerMatchesScreen
import com.yogigupta1206.flipkart_starwarsblastertournament.presentation.viewmodel.CommonViewModel

@Composable
fun AppNavigationGraph() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.HomePage.route,
        modifier = Modifier
    ) {

        composable(Screens.HomePage.route) {
            HomeScreen(onNavigateToPokemonDetails = { navController.onNavigateToPokemonDetails(it) })
        }



        composable(
            route = Screens.PlayerMatchesPage.route + "?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            val backStackEntry = remember(navController) { // Make it dependent on the controller
                navController.getBackStackEntry(Screens.HomePage.route)
            }
            val viewModel: CommonViewModel = hiltViewModel(backStackEntry)
            PlayerMatchesScreen(onNavigateBack = { navController.popBackStack() }, id = it.arguments?.getInt("id") ?: 0, viewModel = viewModel)
        }


    }
}

fun NavController.onNavigateToPokemonDetails(id: Int) =
    navigate(Screens.PlayerMatchesPage.route + "?id=$id")

