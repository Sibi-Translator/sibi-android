package com.example.signlanguagedetector.ui.screen

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.signlanguagedetector.R
import com.example.signlanguagedetector.data.component.NavigationItem

object MainMenu : Screen {
    override val pageTitle: String = "Main Menu"

    @Composable
    override fun show(navController: NavController) {
        val menuItems = listOf(
            NavigationItem(
                "Profil",
                icon = {
                    Icon(imageVector = Icons.Outlined.Person, contentDescription = "", modifier = Modifier.size(24.dp))
                },
                onClick = {
                    navController.navigate(Profile.pageTitle)
                }
            ),
            NavigationItem(
                "Bantuan",
                icon = {
                    Icon(painter = painterResource(id = R.drawable.help), contentDescription = "", modifier = Modifier.size(24.dp))
                }
            ),
            NavigationItem(
                "Bantuan",
                icon = {
                    Icon(painter = painterResource(id = R.drawable.out), contentDescription = "", modifier = Modifier.size(24.dp))
                }
            )
        )

        ModalNavigationDrawer(drawerContent = {
            ModalDrawerSheet {
                menuItems.forEach {
                    NavigationDrawerItem(
                        label = {  },
                        selected = ,
                        onClick = { /*TODO*/ }
                    )
                }
            }
        }) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { /*TODO open side menu*/ }, modifier = Modifier.padding(20.dp)) {
                    Icon(painter = rememberVectorPainter(image = Icons.Default.Menu), contentDescription = "")
                }
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(id = R.drawable.user), contentDescription = "", modifier = Modifier.height(200.dp))
                    Text(
                        text = "Hello Sobat SIBI!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { navController.navigate(Translation.pageTitle) },
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(0.7f)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff374375))
                    ) {
                        Text(
                            text = "Translate",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { navController.navigate(QDictionary.pageTitle) },
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(0.7f)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xffD48975))
                    ) {
                        Text(
                            text = "Q-dictionary",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}