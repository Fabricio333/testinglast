package com.example.passwordmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val key = SecureStorage.loadPrivateKey(this)
        if (key == null) {
            AppNavigator.navigateTo(this, OnboardingActivity::class.java)
        } else {
            AppNavigator.navigateTo(this, DashboardActivity::class.java)
        }
        finish()
        setContentView(R.layout.activity_main)
    }
}
