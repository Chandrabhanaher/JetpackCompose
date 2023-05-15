package com.chan.jetpackcompose.auth

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chan.jetpackcompose.auth.routes.AuthRoutes
import com.chan.jetpackcompose.database.LocalDBFactory
import com.chan.jetpackcompose.database.LocalDBViewModel


/**
 * Created by Chandrabhan Haribhau Aher on 08-05-2023.
 * chandrabhan99@gmail.com
 */
class MainScreen: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            val owner = LocalViewModelStoreOwner.current
            val application = LocalContext.current.applicationContext as Application
            val navController: NavHostController = rememberNavController()
            owner?.let {
                val viewModel: LocalDBViewModel = viewModel(it, "LocalDBViewModel", LocalDBFactory(application))

                NavContent(navController, viewModel)

            }
        }
    }
}

@Composable
fun NavContent(navController:NavHostController, viewModel: LocalDBViewModel) {


    NavHost(navController = navController, startDestination = AuthRoutes.Login.routs){
        composable(AuthRoutes.Login.routs){
            LoginScreen(navController,viewModel)
        }
        composable(AuthRoutes.Register.routs){
            RegisterScreen(navController,viewModel)
        }
        composable(AuthRoutes.ForgotPassword.routs){
            ForgotPasswordScreen(navController,viewModel)
        }
    }

}

@Preview
@Composable
fun DefaultPreview(){
    val owner = LocalViewModelStoreOwner.current
    val application = LocalContext.current.applicationContext as Application
    val navController: NavHostController = rememberNavController()

    owner?.let {
        val viewModel: LocalDBViewModel = viewModel(it, "LocalDBViewModel", LocalDBFactory(application))

        NavContent(navController,viewModel)

    }
}