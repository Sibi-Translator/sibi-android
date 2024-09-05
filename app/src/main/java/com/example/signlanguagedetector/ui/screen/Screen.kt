package com.example.signlanguagedetector.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

interface Screen {
    abstract val pageTitle: String
    @Composable
    abstract fun show(navController: NavController)

    @Composable
    fun ShowScreen(navController: NavController) {
        show(navController)
    }

}