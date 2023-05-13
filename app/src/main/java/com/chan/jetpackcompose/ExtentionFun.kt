package com.chan.jetpackcompose

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


/**
 * Created by Chandrabhan Haribhau Aher on 11-05-2023.
 * chandrabhan99@gmail.com
 */

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun Context.sharePreference(): SharedPreferences {
    val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    val encryptedKey = EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
    val encryptedValue = EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM

    return EncryptedSharedPreferences.create(
        "jetpack_compose", masterKey, this,
        encryptedKey, encryptedValue
    )
}