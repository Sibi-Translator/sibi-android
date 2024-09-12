package com.example.signlanguagedetector.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.signlanguagedetector.Global
import com.example.signlanguagedetector.R

object Intro : Screen {
    override val pageTitle: String = "Intro"

    @Composable
    override fun show(navController: NavController) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(200.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if(Global.user != null) {
                            navController.navigate(MainMenu.pageTitle)
                        } else {
                            navController.navigate(Login.pageTitle)
                        }
                    },
                    modifier = Modifier.height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff374375))
                ) {
                    Text(text = "Ayo Mulai",
                        modifier = Modifier.fillMaxWidth(0.8f),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
                Text(text = "Dengan menekan tombol di atas, anda menyetujui", textAlign = TextAlign.Center, fontSize = 10.sp)
                Text(
                    text = "Syarat dan Ketentuan",
                    color = Color.Blue,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .clickable {
                            navController.currentBackStackEntry?.savedStateHandle?.set("url", "https://github.com/Sibi-Translator/sibi-android/blob/main/TnC.md")
                            navController.navigate(WebViews.pageTitle)
                        }
                )
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}