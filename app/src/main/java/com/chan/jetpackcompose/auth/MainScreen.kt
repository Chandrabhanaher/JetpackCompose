package com.chan.jetpackcompose.auth

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chan.jetpackcompose.auth.routes.AuthRoutes


/**
 * Created by Chandrabhan Haribhau Aher on 08-05-2023.
 * chandrabhan99@gmail.com
 */
class MainScreen: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            NavContent()
        }
    }
}

@Composable
fun NavContent(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AuthRoutes.Login.routs){
        composable(AuthRoutes.Login.routs){
            LoginScreen(navController = navController)
        }
        composable(AuthRoutes.Register.routs){
            RegisterScreen(navController = navController)
        }
        composable(AuthRoutes.ForgotPassword.routs){
            ForgotPasswordScreen(navController = navController)
        }
    }

}

@Preview
@Composable
fun DefaultPreview(){
    NavContent()
}