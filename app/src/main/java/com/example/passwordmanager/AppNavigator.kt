package com.example.passwordmanager

import android.content.Context
import android.content.Intent

object AppNavigator {
    fun navigateTo(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        context.startActivity(intent)
    }
}
