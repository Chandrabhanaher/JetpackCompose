package com.chan.jetpackcompose.auth

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import androidx.navigation.NavHostController
import com.chan.jetpackcompose.Home
import com.chan.jetpackcompose.auth.routes.AuthRoutes
import com.chan.jetpackcompose.sharePreference
import com.chan.jetpackcompose.ui.theme.Purple200
import com.chan.jetpackcompose.ui.theme.Purple700

/**
 * Created by Chandrabhan Haribhau Aher on 08-05-2023.
 * chandrabhan99@gmail.com
 */

@Composable
fun LoginScreen(navController: NavHostController) {

    val context = LocalContext.current

    var popupControl by remember { mutableStateOf(false) }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        ClickableText(
            text = AnnotatedString("Sign up here"),
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(20.dp),
            onClick = { navController.navigate(AuthRoutes.Register.routs) },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = Purple700
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive))

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = username,
            singleLine = true,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done))

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            label = { Text(text = "Password") },
            value = password,
            singleLine = true,
            onValueChange = { password = it },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done),

            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val desc = if (passwordVisible) "Hide password" else "Show password"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = desc)
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    if (username.isNotBlank()) {
                        val sp = context.sharePreference()
                        sp.edit().putString("username", username).apply()
                        popupControl = true
                    } else {
                        Toast.makeText(
                            context,
                            "Please enter correct username & Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp)
            ) {
                Text(text = "Login")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        ClickableText(
            text = AnnotatedString("Forgot Password"),
            onClick = { navController.navigate(AuthRoutes.ForgotPassword.routs) },
            style = TextStyle(fontSize = 14.sp, fontFamily = FontFamily.Monospace)
        )
    }


//    Open popup window
    if (popupControl) {
        Popup(
            popupPositionProvider = WindowCenterOffset(),
            properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                excludeFromSystemGesture = true, clippingEnabled = false
            ),
            onDismissRequest = { popupControl = false }) {

            Surface(
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                shape = RoundedCornerShape(8.dp),
                color = Purple200,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(16.dp)
                    .background(Color.Cyan)) {

                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(text = "Login", style = TextStyle(fontSize = 25.sp, fontFamily = FontFamily.Cursive))

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "You went to login this app", style = TextStyle(fontSize = 25.sp, fontFamily = FontFamily.Cursive))

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .absolutePadding(20.dp, 16.dp, 20.dp, 0.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly){
                        Button(onClick = {popupControl = false }) {
                            Text(text = "Close")
                        }
                        Button(onClick = {
                            popupControl = false
                            context.startActivity(Intent(context, Home::class.java).putExtra("username", username))
                        }) {
                            Text(text = "Login")
                        }
                    }
                }
            }
        }
    }
}