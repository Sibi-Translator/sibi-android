package com.example.signlanguagedetector.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

object Help : Screen {
    override val pageTitle: String = "Help"

    @Composable
    override fun show(navController: NavController) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(20.dp)
        ) {
            Text("If you have any questions, please contact sibi app team. You can check our page on the link below: \n\n")
            Text(
                text = "Our page",
                color = Color.Blue,
                modifier = Modifier.clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set("url", "https://github.com/Sibi-Translator/sibi-android/blob/main/README.md")
                    navController.navigate(WebViews.pageTitle)
                })
        }
    }
}