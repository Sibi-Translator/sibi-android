package com.example.signlanguagedetector.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.signlanguagedetector.Global
import com.example.signlanguagedetector.R
import com.example.signlanguagedetector.ui.component.SibiButton
import com.example.signlanguagedetector.ui.component.SibiTextField
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch

object Login : Screen {
    override val pageTitle: String = "Login"

    @Composable
    override fun show(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val email = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Selamat Datang Kembali!", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xff374375), textAlign = TextAlign.Center, lineHeight = 34.sp)
            Text(text = "Isi kolom dibawah untuk melanjutkan.", fontSize = 16.sp, color = Color(0xff374375), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(32.dp))
            SibiTextField(state = email, false, "Email")
            Spacer(modifier = Modifier.height(16.dp))
            SibiTextField(state = password, true, "Kata sandi")
            Spacer(modifier = Modifier.height(24.dp))
            val context = LocalContext.current
            val coroutine = rememberCoroutineScope()
            val activity = LocalContext.current.getActivity()
            SibiButton(onClick = {
                coroutine.launch {
                    try {
                        val user = Global.db?.userDao()?.getUserLogin(email.value)
                        if(user != null) {
                            if (user.password == password.value) {
                                Global.user = user
                                with(Global.sharedPreferences?.edit()) {
                                    this?.putString("email", user.email)
                                    this?.apply()
                                }
                                navController.navigate(MainMenu.pageTitle)
                            } else {
                                Toast.makeText(context, "Login gagal", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Login gagal", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "Login gagal: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }, title = "Masuk")
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
                Divider(modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp), color = Color.LightGray)
                Text(text = "atau masuk dengan", color = Color.LightGray, fontSize = 10.sp)
                Divider(modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp), color = Color.LightGray)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff374375),
                contentColor = Color.White
            )) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(painter = painterResource(id = R.drawable.google_3), modifier = Modifier.size(24.dp),contentDescription = "",)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Google")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            val annotatedText = buildAnnotatedString {
                append("Belum punya akun? ")
                pushStringAnnotation("Daftar", "Daftar")
                withStyle(SpanStyle(color = Color(0xff374375), textDecoration = TextDecoration.Underline)) {
                    append("Daftar")
                }
            }
            ClickableText(text = annotatedText, onClick = { offset ->
                annotatedText.getStringAnnotations(start = offset, end = offset).firstOrNull()?.let {
                    if(it.item == "Daftar") navController.navigate(Register.pageTitle)
                }
            })

        }
    }
}