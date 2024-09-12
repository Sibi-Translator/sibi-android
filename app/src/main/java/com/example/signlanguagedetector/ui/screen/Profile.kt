package com.example.signlanguagedetector.ui.screen

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.signlanguagedetector.Global
import com.example.signlanguagedetector.R
import com.example.signlanguagedetector.ui.component.SibiTextField
import kotlinx.coroutines.launch

object Profile: Screen {
    override val pageTitle: String = "Profile"

    @Composable
    override fun show(navController: NavController) {
        val isEditing = remember { mutableStateOf(false) }
        val nama = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val gender = remember { mutableStateOf("") }
        val type = remember { mutableStateOf("") }

        LaunchedEffect(key1 = true) {
            email.value = Global.user?.email ?: ""
            nama.value = Global.user?.name ?: ""
            password.value = Global.user?.password ?: ""
            gender.value = Global.user?.gender ?: ""
            type.value = Global.user?.userType ?: ""
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.height(80.dp)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Outlined.KeyboardArrowLeft, "")
                }
                Spacer(modifier = Modifier.weight(1f))
                val coroutine = rememberCoroutineScope()
                IconButton(onClick = {
                    if(isEditing.value) {
                        val newUser = Global.user
                        newUser?.name = nama.value
                        newUser?.password = password.value
                        Global.user = newUser
                        coroutine.launch {
                            if(newUser != null) {
                                Global.db?.userDao()?.update(newUser)
                            }
                        }
                    }
                    isEditing.value = !isEditing.value
                }) {
                    Icon(imageVector = if(isEditing.value) Icons.Default.Check else Icons.Default.Edit, contentDescription = "")
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Profil")
                Image(painter = painterResource(id = R.drawable.user), contentDescription = "", modifier = Modifier.width(160.dp).height(160.dp), contentScale = ContentScale.FillHeight)
                SibiTextField(state = nama, title = "Nama", isEditable = isEditing.value)
                Spacer(modifier = Modifier.height(8.dp))
                SibiTextField(state = email, title = "Email", isEditable = false)
                Spacer(modifier = Modifier.height(8.dp))
                SibiTextField(state = password, title = "Kata sandi", isEditable = isEditing.value)
            }
        }
    }
}