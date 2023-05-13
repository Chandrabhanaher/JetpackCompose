package com.chan.jetpackcompose.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.chan.jetpackcompose.R


/**
 * Created by Chandrabhan Haribhau Aher on 08-05-2023.
 * chandrabhan99@gmail.com
 */
sealed class NavScreen(val route: String, @StringRes val resourceId: Int, @DrawableRes drawableId: Int){
    object MyHome: NavScreen("home", R.string.title_activity_home,R.drawable.home)
    object Profile: NavScreen("profile", R.string.profile,R.drawable.home)
    object Friends: NavScreen("friends", R.string.friend,R.drawable.friends)
    object Setting: NavScreen("setting", R.string.setting,R.drawable.settings)
    object About: NavScreen("about", R.string.about,R.drawable.about)
}
