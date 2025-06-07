package com.example.passwordmanager

class NonceManager(private val map: MutableMap<String, Int> = mutableMapOf()) {
    fun getNonce(user: String, site: String): Int {
        return map["$user|$site"] ?: 0
    }

    fun increment(user: String, site: String) {
        val key = "$user|$site"
        map[key] = (map[key] ?: 0) + 1
    }

    fun decrement(user: String, site: String) {
        val key = "$user|$site"
        val value = (map[key] ?: 0) - 1
        map[key] = if (value < 0) 0 else value
    }

    fun toMapString(): String = map.entries.joinToString(";") { "${it.key}=${it.value}" }

    companion object {
        fun fromMapString(str: String?): NonceManager {
            val map = mutableMapOf<String, Int>()
            str?.split(";")?.forEach {
                val parts = it.split("=")
                if (parts.size == 2) {
                    map[parts[0]] = parts[1].toIntOrNull() ?: 0
                }
            }
            return NonceManager(map)
        }
    }
}
