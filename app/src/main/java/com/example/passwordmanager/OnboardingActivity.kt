package com.example.passwordmanager

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OnboardingActivity : AppCompatActivity() {
    private lateinit var wordlist: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        wordlist = WordlistManager.load(this)
        val mnemonic = PasswordUtils.generateMnemonic(wordlist)
        findViewById<TextView>(R.id.mnemonicView).text = mnemonic.joinToString(" ")

        findViewById<Button>(R.id.continueButton).setOnClickListener {
            val privateKey = PasswordUtils.mnemonicToPrivateKey(mnemonic, wordlist)
            SecureStorage.savePrivateKey(this, privateKey)
            AppNavigator.navigateTo(this, DashboardActivity::class.java)
            finish()
        }
    }
}
