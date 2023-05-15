package com.chan.jetpackcompose.auth

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.chan.jetpackcompose.Home
import com.chan.jetpackcompose.auth.routes.AuthRoutes
import com.chan.jetpackcompose.database.LocalDBViewModel
import com.chan.jetpackcompose.database.User
import com.chan.jetpackcompose.sharePreference
import com.chan.jetpackcompose.ui.theme.Purple200
import com.chan.jetpackcompose.ui.theme.Purple700
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.logging.Handler

/**
 * Created by Chandrabhan Haribhau Aher on 08-05-2023.
 * chandrabhan99@gmail.com
 */


@Composable
fun LoginScreen(navController: NavHostController, viewModel: LocalDBViewModel){
    viewModel.getAllUsers()
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
        Login(navController, viewModel)
    }
}

@Composable
fun Login(navController: NavHostController, viewModel: LocalDBViewModel) {

    val context = LocalContext.current

    var popupControl by remember { mutableStateOf(false) }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val application = LocalContext.current.applicationContext as Application
    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Log.e("CHANDRA", "User details - ${Gson().toJson(viewModel.userList!!.observeAsState())}")


        Text(text = "Login", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive))

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = username,
            singleLine = true,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            label = { Text(text = "Password") },
            value = password,
            singleLine = true,
            onValueChange = { password = it },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),

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
                        viewModel.userLogin(username, password)
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
                    .background(Color.Cyan)
            ) {

                val singleUser = viewModel.singleUser!!.observeAsState()
                Log.e("SINGLE_USER", "$singleUser")

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Login",
                        style = TextStyle(fontSize = 25.sp, fontFamily = FontFamily.Cursive)
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "You went to login this app",
                        style = TextStyle(fontSize = 25.sp, fontFamily = FontFamily.Cursive)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .absolutePadding(20.dp, 16.dp, 20.dp, 0.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = { popupControl = false }) {
                            Text(text = "Close")
                        }
                        Button(onClick = {
                            popupControl = false

                            context.startActivity(
                                Intent(
                                    context,
                                    Home::class.java
                                ).putExtra("username", username)
                            )
                        }) {
                            Text(text = "Login")
                        }
                    }
                }
            }
        }
    }
}