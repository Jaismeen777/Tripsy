package com.igdtuw.travelbuddy

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "destinationPicker") {

        composable("destinationPicker") {
            DestinationPickerScreen(navController)
        }

        // Navigate to places list for selected category
        composable("placesLiat/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            PlacesListScreen(category,navController = navController)
        }
    }
}
