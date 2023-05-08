package com.chan.jetpackcompose.auth.routes


/**
 * Created by Chandrabhan Haribhau Aher on 08-05-2023.
 * chandrabhan99@gmail.com
 */
sealed class AuthRoutes(val routs: String){
    object Login: AuthRoutes("Login")
    object Register: AuthRoutes("Register")
    object ForgotPassword: AuthRoutes("ForgotPass")

}
