package com.example.passwordmanager

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

object AppNavigator {
    fun goTo(context: Context, target: Class<out AppCompatActivity>) {
        context.startActivity(Intent(context, target))
    }
}
