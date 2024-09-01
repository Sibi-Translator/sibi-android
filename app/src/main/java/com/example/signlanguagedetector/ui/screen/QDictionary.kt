package com.example.signlanguagedetector.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.signlanguagedetector.R

object QDictionary : Screen {
    override val pageTitle: String = "QDictionary"

    @Composable
    override fun show(navController: NavController) {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "")
                }
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Q - Dictionary", fontSize = 16.sp, textAlign = TextAlign.Center)
                    Text(text = "Mari belajar bahasa isyarat", fontSize = 10.sp, textAlign = TextAlign.Center)
                }
                Image(painter = painterResource(id = R.drawable.user), contentDescription = "", Modifier.size(40.dp).scale(1.5f))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    ButtonToWeb(title = "Abjad", url = "https://pmpk.kemdikbud.go.id/sibi/kosakata", navController = navController, modifier = Modifier.padding(10.dp).weight(1f))
                    ButtonToWeb(title = "Angka", url = "https://pmpk.kemdikbud.go.id/sibi/kosakata/angka", navController = navController, modifier = Modifier.padding(10.dp).weight(1f))
                }
                Row(horizontalArrangement = Arrangement.Center) {
                    ButtonToWeb(title = "Imbuhan", url = "https://pmpk.kemdikbud.go.id/sibi/kosakata/imbuhan", navController = navController, modifier = Modifier.padding(10.dp).weight(1f))
                    ButtonToWeb(title = "Kata", url = "https://pmpk.kemdikbud.go.id/sibi/pencarian", navController = navController, modifier = Modifier.padding(10.dp).weight(1f))
                }
            }
        }
    }

    @Composable
    fun ButtonToWeb(
        title: String,
        url: String,
        navController: NavController,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .aspectRatio(1f)
                .background(Color(0xff374375), shape = RoundedCornerShape(12.dp))
                .clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set("url", url)
                    navController.navigate(WebViews.pageTitle)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}