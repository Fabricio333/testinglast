package com.example.passwordmanager

object InputValidator {
    fun validateNotEmpty(vararg fields: String): Boolean =
        fields.all { it.isNotBlank() }
}
