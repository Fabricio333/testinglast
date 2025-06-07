package com.example.passwordmanager

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object SecureStorage {
    private const val FILE_NAME = "secure_storage"
    private const val KEY_PRIVATE = "private_key"
    private const val KEY_NONCES = "nonce_map"

    private fun prefs(context: Context) =
        EncryptedSharedPreferences.create(
            FILE_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun savePrivateKey(context: Context, key: String) {
        prefs(context).edit().putString(KEY_PRIVATE, key).apply()
    }

    fun loadPrivateKey(context: Context): String? =
        prefs(context).getString(KEY_PRIVATE, null)

    fun saveNonceMap(context: Context, map: String) {
        prefs(context).edit().putString(KEY_NONCES, map).apply()
    }

    fun loadNonceMap(context: Context): String? =
        prefs(context).getString(KEY_NONCES, null)
}
