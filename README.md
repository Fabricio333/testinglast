# Android Password Manager App

This repository outlines the architecture for a native Android password manager application. The app uses BIP‑39 mnemonics for key generation, deterministic password creation, and biometric-protected encrypted storage.

## Features
- **Mnemonic Generation & Validation** using the BIP‑39 English wordlist (12 words).
- **Key Derivation** converting mnemonic words into a private key for deterministic password derivation.
- **Deterministic Passwords** of the form `PASS<hash>249+` unique for each site and user.
- **Nonce Management** to keep track of password iterations per site/user.
- **Biometric Unlock** with Android's `BiometricPrompt` and fallback to PIN/password.
- **Session Handling** with a five‑minute timeout and optional memory wipe.
- **Input Validation** for all user fields.

## App Workflow
1. **Startup** – If a private key exists in secure storage, the user is prompted for biometric unlock. Otherwise, the onboarding flow is shown.
2. **Onboarding** – Users can generate a new mnemonic or import an existing one and optionally save the derived key securely.
3. **Dashboard** – Generate or view passwords per site/email, manage nonces, and save state.
4. **Lock & Timeout** – On inactivity or when the app goes to the background, session data is cleared and biometric authentication is required again.

## Key Classes
- `BiometricHelper.kt` – Handles fingerprint or PIN authentication.
- `SecureStorage.kt` – Encrypts and stores the private key and nonce map.
- `WordlistManager.kt` – Loads the BIP‑39 wordlist from `assets/bip39_english.txt`.
- `AppNavigator.kt` – Simplifies Activity navigation.
- `InputValidator.kt` – Basic checks for user input.
- `SessionManager.kt` – Tracks session start and expiry.

## Android Configuration
- Minimum SDK: **24**
- Permissions: `android.permission.USE_BIOMETRIC`
- Dependencies:
  ```kotlin
  implementation "androidx.biometric:biometric:1.2.0-alpha04"
  implementation "androidx.security:security-crypto:1.1.0-alpha06"
  ```

The BIP‑39 English wordlist should be placed in `app/src/main/assets/bip39_english.txt` with one word per line.

## Future Enhancements
- Logout and wipe button.
- Option to export the mnemonic.
- Room database integration for structured data.
- Clipboard auto-clear timer.

## Project Status
This repository contains a minimal implementation of the password manager app. The BIP-39 wordlist in `app/src/main/assets/bip39_english.txt` is truncated for brevity.
