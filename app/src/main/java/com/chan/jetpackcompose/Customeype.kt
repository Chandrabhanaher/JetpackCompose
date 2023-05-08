package com.chan.jetpackcompose

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Created by Chandrabhan Haribhau Aher on 27-03-2023.
 * chandrabhan99@gmail.com
 */


val Typography.subTitle: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Monospace,
            color = Color.Black
        )
    }


val Typography.title: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Monospace
        )
    }