package com.chan.jetpackcompose.navigationdrawer

import android.graphics.drawable.Icon
import androidx.compose.ui.graphics.vector.ImageVector


/**
 * Created by Chandrabhan Haribhau Aher on 30-03-2023.
 * chandrabhan99@gmail.com
 */
data class MenuItems(

    val id: String,
    val title: String,
    val contentDescription: String,
    var icon: ImageVector,
)
