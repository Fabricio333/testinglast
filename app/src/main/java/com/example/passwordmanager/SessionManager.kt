package com.example.passwordmanager

import android.os.SystemClock

object SessionManager {
    private const val TIMEOUT_MS = 5 * 60 * 1000L
    private var lastActive = 0L

    fun startSession() {
        lastActive = SystemClock.elapsedRealtime()
    }

    fun touch() {
        lastActive = SystemClock.elapsedRealtime()
    }

    fun isExpired(): Boolean {
        return SystemClock.elapsedRealtime() - lastActive > TIMEOUT_MS
    }
}
