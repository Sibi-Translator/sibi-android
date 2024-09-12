package com.example.signlanguagedetector.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.signlanguagedetector.R

@Composable
fun SibiTextField(state: MutableState<String>, isPassword: Boolean = false, title: String = "") {
    val isObscured = remember {
        mutableStateOf(true)
    }
    TextField(value = state.value, onValueChange = {
        state.value = it
    }, label = {
        Text(text = title)
    }, placeholder = { Text(text = title) },
        trailingIcon = {
            if(isPassword) {
                IconButton(onClick = { if(isObscured.value) isObscured.value = false else isObscured.value = true }) {
                    Icon(painter = painterResource(id = if(isObscured.value) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24), contentDescription = "")
                }
            }
        },
        visualTransformation = if (isObscured.value && isPassword) PasswordVisualTransformation() else VisualTransformation.None,
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
            unfocusedPlaceholderColor = Color.LightGray,
            focusedPlaceholderColor = Color.LightGray,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Black,
            focusedTrailingIconColor = Color.Black,
            unfocusedTrailingIconColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}