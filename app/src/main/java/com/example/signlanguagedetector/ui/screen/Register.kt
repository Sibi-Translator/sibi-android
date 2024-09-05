package com.example.signlanguagedetector.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.signlanguagedetector.R

object Register :Screen {
    override val pageTitle: String = "Register"

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
            val isObscured = remember { mutableStateOf(true) }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Ayo Daftar!", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xff374375), textAlign = TextAlign.Center)
            Text(text = "Selamat datang! Silakan buat akun terlebih dahulu.", fontSize = 16.sp, color = Color(0xff374375), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(32.dp))
            TextField(value = email.value, onValueChange = {
                email.value = it
            }, label = {
                Text(text = "Email")
            }, placeholder = { Text(text = "Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Black,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = password.value, onValueChange = {
                password.value = it
            }, label = {
                Text(text = "Kata sandi")
            }, placeholder = { Text(text = "Kata sandi") },
                trailingIcon = {
                    IconButton(onClick = { if(isObscured.value) isObscured.value = false else isObscured.value = true }) {
                        Icon(painter = painterResource(id = if(isObscured.value) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24), contentDescription = "")
                    }
                },
                visualTransformation = if (isObscured.value) PasswordVisualTransformation() else VisualTransformation.None,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Black,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            val context = LocalContext.current
            Button(onClick = { Toast.makeText(context, "Not yet Implemented. Please login with sobatsibi - 12345678", Toast.LENGTH_SHORT).show() }, colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff374375)
            )) {
                Text(text = "Daftar", color = Color.White)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
                Divider(modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp), color = Color.LightGray)
                Text(text = "atau daftar dengan", color = Color.LightGray, fontSize = 10.sp)
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
                    Icon(painter = painterResource(id = R.drawable.google_3), contentDescription = "",)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Google")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            val annotatedText = buildAnnotatedString {
                append("Sudah punya akun? ")
                pushStringAnnotation("Masuk", "Masuk")
                withStyle(SpanStyle(color = Color(0xff374375), textDecoration = TextDecoration.Underline)) {
                    append("Masuk")
                }
            }
            ClickableText(text = annotatedText, onClick = { offset ->
                annotatedText.getStringAnnotations(start = offset, end = offset).firstOrNull()?.let {
                    if(it.item == "Masuk") navController.navigate(Login.pageTitle)
                }
            })

        }
    }
}