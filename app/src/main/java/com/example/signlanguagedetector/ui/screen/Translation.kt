package com.example.signlanguagedetector.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

object Translation : Screen {
    override val pageTitle: String = "Translation"

    @Composable
    override fun show(navController: NavController) {
        val selectedTab = remember {
            mutableStateOf(0)
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().height(80.dp)) {
                TabRow(selectedTabIndex = selectedTab.value, modifier = Modifier.fillMaxSize().background(
                    Color(0xffF7EDEC)
                ), containerColor = Color(0xffF7EDEC)) {
                    Tab(
                        selected = selectedTab.value == 0,
                        onClick = { selectedTab.value = 0 },
                    ) {
                        Text(text = "Sign to Text", modifier = Modifier.padding(8.dp), color = Color.Black)
                    }
                    Tab(
                        selected = selectedTab.value == 1,
                        onClick = { selectedTab.value = 1 },
                    ) {
                        Text(text = "Speech to Text", modifier = Modifier.padding(8.dp), color = Color.Black)
                    }
                }
            }
            when (selectedTab.value) {
                0 -> SignToText.show(navController)
                1 -> SpeechToText.show(navController)
            }
        }
    }
}