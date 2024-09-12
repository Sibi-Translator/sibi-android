package com.example.signlanguagedetector.data.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: @Composable () -> Unit = {},
    val onClick: () -> Unit = {},
)