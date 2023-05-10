package com.chan.jetpackcompose.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.painter.Painter


/**
 * Created by Chandrabhan Haribhau Aher on 08-05-2023.
 * chandrabhan99@gmail.com
 */

data class NavigationDrawerItem(
    val image : Painter,
    val label: String,
    val route: String,
    val showUnreadBubble:Boolean = false

)
