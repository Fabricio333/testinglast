package com.example.passwordmanager

object SessionManager {
    private const val TIMEOUT_MS = 5 * 60 * 1000L      // 5 minutes
    private var start: Long? = null

    fun startSession() {
        start = System.currentTimeMillis()
    }

    fun isExpired(): Boolean =
        start?.let { System.currentTimeMillis() - it > TIMEOUT_MS } ?: true

    fun clear() {
        start = null
    }
}
