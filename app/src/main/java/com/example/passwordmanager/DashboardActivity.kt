package com.example.passwordmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    private lateinit var wordlist: List<String>
    private lateinit var nonceManager: NonceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        wordlist = WordlistManager.load(this)
        nonceManager = NonceManager.fromMapString(SecureStorage.loadNonceMap(this))

        findViewById<Button>(R.id.generateButton).setOnClickListener {
            val user = findViewById<EditText>(R.id.userInput).text.toString()
            val site = findViewById<EditText>(R.id.siteInput).text.toString()
            if (!InputValidator.isNotBlank(user) || !InputValidator.isNotBlank(site)) return@setOnClickListener
            val key = SecureStorage.loadPrivateKey(this) ?: return@setOnClickListener
            val nonce = nonceManager.getNonce(user, site)
            val password = PasswordUtils.deterministicPassword(key, user, site, nonce)
            findViewById<TextView>(R.id.passwordView).text = password
        }

        findViewById<Button>(R.id.incrementButton).setOnClickListener {
            val user = findViewById<EditText>(R.id.userInput).text.toString()
            val site = findViewById<EditText>(R.id.siteInput).text.toString()
            nonceManager.increment(user, site)
            SecureStorage.saveNonceMap(this, nonceManager.toMapString())
        }
    }
}
