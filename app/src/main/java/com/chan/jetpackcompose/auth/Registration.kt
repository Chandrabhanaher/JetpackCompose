package com.chan.jetpackcompose.auth

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.chan.jetpackcompose.component.CustomTopAppBar
import com.chan.jetpackcompose.database.LocalDBViewModel
import com.chan.jetpackcompose.database.User
import com.chan.jetpackcompose.ui.theme.Purple200
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Created by Chandrabhan Haribhau Aher on 08-05-2023.
 * chandrabhan99@gmail.com
 */

@Composable
fun RegisterScreen(navController: NavHostController, viewModel: LocalDBViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        RegisterUser(navController, viewModel)
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun RegisterUser(navController: NavHostController, viewModel: LocalDBViewModel) {

    var passwordVisible by remember { mutableStateOf(false) }

    var message by remember {
        mutableStateOf("")
    }
    var popupControl by remember {
        mutableStateOf(false)
    }

    val application = LocalContext.current.applicationContext as Application
    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    Scaffold(
        topBar = {
            CustomTopAppBar(
                navController = navController,
                title = "Register User",
                backIcon = true
            )
        },
        content = { paddingValue ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue.calculateTopPadding()),//20.dp
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                var email by remember { mutableStateOf("") }
                var name by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = email,
                    singleLine = true,
                    onValueChange = { email = it },
                    label = { Text(text = "Email") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email Icon"
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Person Icon"
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = name,
                    singleLine = true,
                    onValueChange = { name = it },
                    label = { Text(text = "Name") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Person Icon"
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
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
                            if (email.isBlank()) {
                                message = "Please enter email"
                                popupControl = true
                            } else if (name.isBlank()) {
                                message = "Please enter username"
                                popupControl = true
                            } else if (password.isBlank()) {
                                message = "Please enter password"
                                popupControl = true
                            } else {
                                val user = User(email, name, password)
//                                message = "$user"
                                GlobalScope.launch {
                                    val result = viewModel.addUser(user)
                                    message = if(result > 0){
                                        "Register successfully !"
                                    }else{
                                        "Something missing...!"
                                    }
                                }
                                popupControl = true
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp)
                    ) {
                        Text(text = "Sign Up")
                    }
                }
            }
        })

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

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = message,
                        style = TextStyle(
                            fontSize = 40.sp,
                            fontFamily = FontFamily.Cursive
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .absolutePadding(20.dp, 16.dp, 20.dp, 0.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Button(onClick = { popupControl = false }) {
                            Text(text = "OK")
                        }

                    }
                }
            }
        }
    }
}
