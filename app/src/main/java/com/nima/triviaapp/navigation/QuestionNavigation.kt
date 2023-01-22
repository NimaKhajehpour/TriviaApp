package com.nima.triviaapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nima.triviaapp.screens.EndScreen
import com.nima.triviaapp.screens.HomeScreen
import com.nima.triviaapp.screens.QuestionViewModel
import com.nima.triviaapp.screens.TriviaScreen

@Composable
fun QuestionNavigation(viewModel: QuestionViewModel = hiltViewModel()){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = QuestionScreens.HomeScreen.name){
        composable(QuestionScreens.HomeScreen.name){
            HomeScreen(navController)
        }

        composable(QuestionScreens.TriviaScreen.name+"/{amount}"+
                "/{category}"+
                "/{difficulty}"+
                "/{type}",
            arguments = listOf(navArgument(name = "amount"){type = NavType.StringType},
                navArgument(name = "category"){type = NavType.StringType},
                navArgument(name = "difficulty"){type = NavType.StringType},
                navArgument(name = "type"){type = NavType.StringType},
            )
        ){
            TriviaScreen(navController = navController,
                viewModel = viewModel,
                amount = it.arguments?.getString("amount"),
                category = it.arguments?.getString("category"),
                difficulty = it.arguments?.getString("difficulty"),
                type = it.arguments?.getString("type")
            )
        }

        composable(QuestionScreens.EndScreen.name+"/{amount}/{category}/{difficulty}/{type}/{score}",
            arguments = listOf(
                navArgument(name = "amount"){type = NavType.StringType},
                navArgument(name = "category"){type = NavType.StringType},
                navArgument(name = "difficulty"){type = NavType.StringType},
                navArgument(name = "type"){type = NavType.StringType},
                navArgument(name = "score"){type = NavType.StringType}
            )
        ){
            EndScreen(
                navController = navController,
                amount = it.arguments?.getString("amount"),
                category = it.arguments?.getString("category"),
                difficulty = it.arguments?.getString("difficulty"),
                type = it.arguments?.getString("type"),
                score = it.arguments?.getString("score")
            )
        }
    }

}