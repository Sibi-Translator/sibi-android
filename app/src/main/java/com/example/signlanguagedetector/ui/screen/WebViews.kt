package com.example.signlanguagedetector.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.kevinnzou.web.WebView
import com.kevinnzou.web.rememberWebViewState

object WebViews : Screen {
    override val pageTitle: String = "WebView"

    @Composable
    override fun show(navController: NavController) {

        var url = remember {
            mutableStateOf("")
        }
        LaunchedEffect(key1 = true) {
            url.value =
                navController.previousBackStackEntry?.savedStateHandle?.get<String>("url").toString()
        }

        val state = rememberWebViewState(url = url.value)

        WebView(state = state, modifier = Modifier.fillMaxSize())
    }
}