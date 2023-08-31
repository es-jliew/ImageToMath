package com.essoft.imagetomath.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import com.essoft.imagetomath.ui.presentation.homeScreen.HomeScreen
import com.essoft.imagetomath.ui.presentation.homeScreen.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.name,
    ) {
        composable(route = AppScreens.Home.name) {
            val viewModel: HomeViewModel = koinViewModel()
            HomeScreen(viewModel)
        }
    }
}
