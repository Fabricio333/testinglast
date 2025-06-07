package com.example.passwordmanager

import java.security.MessageDigest
import kotlin.random.Random

object PasswordUtils {
    fun generateMnemonic(wordlist: List<String>): List<String> {
        return List(12) { wordlist.random(Random) }
    }

    fun mnemonicToPrivateKey(mnemonic: List<String>, wordlist: List<String>): String {
        val indices = mnemonic.map { wordlist.indexOf(it).toString() }
        val decimal = indices.joinToString(separator = "")
        return decimalStringToHex(decimal)
    }

    private fun decimalStringToHex(num: String): String {
        var decimal = num
        val sb = StringBuilder()
        while (decimal.isNotEmpty()) {
            var remainder = 0
            val newDecimal = StringBuilder()
            for (c in decimal) {
                val digit = c - '0'
                val value = remainder * 10 + digit
                val quotient = value / 16
                remainder = value % 16
                if (newDecimal.isNotEmpty() || quotient != 0) {
                    newDecimal.append(quotient)
                }
            }
            sb.append("0123456789abcdef"[remainder])
            decimal = newDecimal.toString()
        }
        return sb.reverse().toString()
    }

    fun deterministicPassword(privateKey: String, user: String, site: String, nonce: Int): String {
        val input = "$privateKey|$user|$site|$nonce"
        val hash = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        val hex = hash.joinToString("") { "%02x".format(it) }
        return "PASS" + hex.substring(0, 16) + "249+"
    }
}
