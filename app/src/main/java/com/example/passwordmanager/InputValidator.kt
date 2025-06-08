package com.example.passwordmanager

object InputValidator {
    fun isValidMnemonic(words: List<String>, wordlist: List<String>): Boolean {
        if (words.size != 12) return false
        return words.all { it in wordlist }
    }

    fun isNotBlank(str: String?) = !str.isNullOrBlank()
}
