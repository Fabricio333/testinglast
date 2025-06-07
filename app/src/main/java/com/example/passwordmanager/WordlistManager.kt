package com.example.passwordmanager

import android.content.Context

object WordlistManager {
    private const val FILE_NAME = "bip39_english.txt"
    private var cached: List<String>? = null

    fun load(context: Context): List<String> {
        if (cached == null) {
            context.assets.open(FILE_NAME).bufferedReader().use {
                cached = it.readLines()
            }
        }
        return cached ?: emptyList()
    }
}
