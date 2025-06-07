package com.example.passwordmanager

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SecureStorage {
    private const val PREFS_NAME = "secure_prefs"
    private const val KEY_PRIVATE = "private_key"
    private const val NONCE_MAP = "nonce_map"

    private fun prefs(context: Context) =
        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun hasKey(context: Context): Boolean = prefs(context).contains(KEY_PRIVATE)

    fun saveKey(
        activity: FragmentActivity,
        hexKey: String,
        onSuccess: () -> Unit,
        onError: () -> Unit = {}
    ) {
        BiometricHelper.authenticate(activity, {
            prefs(activity).edit().putString(KEY_PRIVATE, hexKey).apply()
            onSuccess()
        }, onError)
    }

    fun loadKey(activity: FragmentActivity, onResult: (String?) -> Unit) {
        BiometricHelper.authenticate(activity, {
            onResult(prefs(activity).getString(KEY_PRIVATE, null))
        }, { onResult(null) })
    }

    fun saveNonceMap(context: Context, json: String) {
        prefs(context).edit().putString(NONCE_MAP, json).apply()
    }

    fun loadNonceMap(context: Context): String? =
        prefs(context).getString(NONCE_MAP, null)
}
