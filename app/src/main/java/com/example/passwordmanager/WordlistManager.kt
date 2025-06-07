package com.example.passwordmanager

import android.content.Context

object WordlistManager {
    fun loadWordlist(context: Context): List<String> =
        context.assets.open("bip39_english.txt")
            .bufferedReader()
            .useLines { it.toList() }
}
