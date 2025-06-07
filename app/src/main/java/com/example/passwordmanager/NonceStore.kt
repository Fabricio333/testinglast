package com.example.passwordmanager

import android.content.Context
import org.json.JSONObject

data class UserData(val sites: MutableMap<String, Int> = mutableMapOf())

object NonceStore {
    private val data: MutableMap<String, UserData> = mutableMapOf()

    fun getNonce(user: String, site: String): Int =
        data[user]?.sites?.get(site) ?: 0

    fun incrementNonce(user: String, site: String): Int {
        val userData = data.getOrPut(user) { UserData() }
        val nonce = (userData.sites[site] ?: 0) + 1
        userData.sites[site] = nonce
        return nonce
    }

    fun decrementNonce(user: String, site: String): Int {
        val userData = data.getOrPut(user) { UserData() }
        val current = userData.sites[site] ?: 0
        val newNonce = if (current > 0) current - 1 else 0
        userData.sites[site] = newNonce
        return newNonce
    }

    fun save(context: Context) {
        val json = JSONObject()
        for ((user, userData) in data) {
            val siteObj = JSONObject()
            for ((site, nonce) in userData.sites) {
                siteObj.put(site, nonce)
            }
            json.put(user, siteObj)
        }
        SecureStorage.saveNonceMap(context, json.toString())
    }

    fun load(context: Context) {
        SecureStorage.loadNonceMap(context)?.let { jsonStr ->
            val json = JSONObject(jsonStr)
            for (userKey in json.keys()) {
                val siteObj = json.getJSONObject(userKey)
                val map = mutableMapOf<String, Int>()
                for (siteKey in siteObj.keys()) {
                    map[siteKey] = siteObj.getInt(siteKey)
                }
                data[userKey] = UserData(map)
            }
        }
    }
}
