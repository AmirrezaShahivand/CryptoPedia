# CryptoPedia

Android cryptocurrency market and news application built with Kotlin and XML.

## Requirements

- Android Studio with Android SDK 34
- JDK 17
- Android SDK Platform Tools
- An Android device or emulator with Android 7.0 (API 24) or newer

## Clone and open on a new computer

```bash
git clone https://github.com/AmirrezaShahivand/CryptoPedia.git
cd CryptoPedia
```

Open the project folder in Android Studio and let Gradle sync. Android Studio creates `local.properties` automatically. If it does not, create it in the project root and point it to the local Android SDK:

```properties
sdk.dir=C:\\Users\\YOUR_USER\\AppData\\Local\\Android\\Sdk
```

## Optional CryptoCompare key

The market and chart screens use public fallback providers. CryptoCompare is only used for the legacy news path when configured. Keep the key outside Git:

```properties
cryptocompareApiKey=YOUR_CRYPTOCOMPARE_API_KEY
```

You can also set the `CRYPTOCOMPARE_API_KEY` environment variable. Never commit `local.properties`, `secrets.properties`, `.env` files, or signing files.

## Run

Use Android Studio's Run button, or run the Gradle wrapper from the project root:

```bash
gradlew.bat :app:assembleDebug
```

The debug APK is written to `app/build/outputs/apk/debug/`.

## Release signing

The signing keystore is intentionally not stored in this repository. To publish updates to an existing app, keep a secure backup of the original keystore and its passwords. A new keystore cannot replace the original signing identity for normal updates.

## GitHub Actions

Pull requests and pushes to `master` run the Android Debug build using JDK 17. This catches Gradle and compile regressions without storing API keys in the repository.
