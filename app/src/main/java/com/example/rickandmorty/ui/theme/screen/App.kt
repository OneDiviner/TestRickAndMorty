package com.example.rickandmorty.ui.theme.screen

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.ui.theme.screen.home.HomeScreen

@Composable
fun App() {

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable(
                route = "home",
                enterTransition = {
                    slideInHorizontally(initialOffsetX = { 1000 })
                },
                exitTransition = {
                    slideOutHorizontally(targetOffsetX = { -1000 })
                },
                popEnterTransition = {
                    slideInHorizontally(initialOffsetX = { -1000 })
                },
                popExitTransition = {
                    slideOutHorizontally(targetOffsetX = { 1000 })
                }
            ) {
                HomeScreen(paddingValues = paddingValues)
            }
        }
    }
}