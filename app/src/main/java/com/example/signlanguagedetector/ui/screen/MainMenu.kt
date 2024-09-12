package com.example.signlanguagedetector.ui.screen

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.signlanguagedetector.Global
import com.example.signlanguagedetector.R
import com.example.signlanguagedetector.data.component.NavigationItem
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

object MainMenu : Screen {
    override val pageTitle: String = "Main Menu"

    @Composable
    override fun show(navController: NavController) {
        val coroutine = rememberCoroutineScope()
        val menuItems = listOf(
            NavigationItem(
                "Profil",
                icon = {
                    Icon(imageVector = Icons.Outlined.Person, contentDescription = "", modifier = Modifier.size(24.dp), tint = Color(0xff374375))
                },
                onClick = {
                    navController.navigate(Profile.pageTitle)
                }
            ),
            NavigationItem(
                "Bantuan",
                icon = {
                    Icon(painter = painterResource(id = R.drawable.help), contentDescription = "", modifier = Modifier.size(24.dp), tint = Color(0xff374375))
                },
                onClick = {
                    navController.navigate(Help.pageTitle)
                }
            ),
            NavigationItem(
                "Logout",
                icon = {
                    Icon(painter = painterResource(id = R.drawable.out), contentDescription = "", modifier = Modifier.size(24.dp), tint = Color(0xff374375))
                },
                onClick = {
                    coroutine.launch {
                        Global.logout(navController)
                    }
                }
            )
        )

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        ModalNavigationDrawer(drawerContent = {
            ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.7f),
                    drawerContainerColor = Color.White,
                    drawerContentColor = Color.White
                ) {
                Column(modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()) {
                    Text(text = "Pengaturan", fontSize = 25.sp, color = Color(0xff374375))
                    Spacer(modifier = Modifier.height(12.dp))
                    menuItems.forEachIndexed { index, it ->
                        NavigationDrawerItem(
                            label = {
                                Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = it.title, color = Color(0xff374375))
                                }
                            },
                            icon = it.icon,
                            selected = false,
                            onClick = it.onClick,
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.White
                            )
                        )
                        if(index < menuItems.size - 1) {
                            Divider()
                        }
                    }
                }
            }
        }, drawerState = drawerState) {
            Box(modifier = Modifier.fillMaxSize()
            ) {
                IconButton(onClick = {
                    coroutine.launch {
                        drawerState.open()
                    }
                }, modifier = Modifier.padding(20.dp)) {
                    Icon(painter = rememberVectorPainter(image = Icons.Default.Menu), contentDescription = "")
                }
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.user), contentDescription = "", modifier = Modifier.height(200.dp))
                    val isEmptyName = Global.user?.name.isNullOrEmpty()
                    val text = if(isEmptyName) Global.user?.email?.split("@")?.get(0).toString().replaceFirstChar { it.uppercaseChar() }.plus("") else Global.user?.name
                    Text(
                        text = "Hello $text",
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