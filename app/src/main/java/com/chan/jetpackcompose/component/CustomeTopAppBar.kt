package com.chan.jetpackcompose.component

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController


/**
 * Created by Chandrabhan Haribhau Aher on 08-05-2023.
 * chandrabhan99@gmail.com
 */

@Composable
fun CustomTopAppBar(navController: NavHostController, title: String, backIcon: Boolean) {
    TopAppBar(
        title = { Text(text = title) },
        elevation = AppBarDefaults.TopAppBarElevation,
        backgroundColor = Color.Cyan,
        contentColor = contentColorFor(backgroundColor = Color.Black),
        navigationIcon = if (backIcon && navController.previousBackStackEntry != null) {
            {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        } else {
            null
        }
    )
}