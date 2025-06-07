package com.example.passwordmanager

import java.math.BigInteger
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

object CryptoUtils {
    fun wordsToIndices(mnemonic: String, wordlist: List<String>): String =
        mnemonic.trim()
            .split("\\s+".toRegex())
            .joinToString("") { w ->
                val idx = wordlist.indexOf(w)
                require(idx >= 0) { "Invalid word: $w" }
                idx.toString().padStart(4, '0')
            }

    fun decimalStringToHex(decimal: String): String {
        require(decimal.all { it.isDigit() }) { "Invalid decimal input" }
        return BigInteger(decimal).toString(16)
    }

    fun hash(text: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest(text.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun generatePassword(
        privateKey: String,
        user: String,
        site: String,
        nonce: Int
    ): String {
        val input = "$privateKey/$user/$site/$nonce"
        val entropy = hash(input).substring(0, 16)
        return "PASS${entropy}249+"
    }

    fun encrypt(data: String, password: String): String {
        val keySpec = SecretKeySpec(
            hash(password).substring(0, 32).toByteArray(),
            "AES"
        )
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        val encBytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encBytes, Base64.NO_WRAP)
    }

    fun decrypt(base64Data: String, password: String): String {
        val keySpec = SecretKeySpec(
            hash(password).substring(0, 32).toByteArray(),
            "AES"
        )
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        val bytes = cipher.doFinal(Base64.decode(base64Data, Base64.NO_WRAP))
        return String(bytes)
    }
}
