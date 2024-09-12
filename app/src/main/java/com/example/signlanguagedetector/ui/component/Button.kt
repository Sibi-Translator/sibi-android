package com.example.signlanguagedetector.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SibiButton(
    onClick: () -> Unit,
    title: String,
) {
    Button(onClick = onClick, modifier = Modifier
        .height(50.dp)
        .fillMaxWidth(0.7f)
        .defaultMinSize(minWidth = 150.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff374375)
        )
    ) {
        Text(text = title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight(700))
    }
}