# Security notes

Do not commit API keys, passwords, signing files, or private certificates.

For CryptoCompare, put the key in `local.properties`:

```properties
cryptocompareApiKey=YOUR_CRYPTOCOMPARE_API_KEY
```

The Gradle build also accepts the `CRYPTOCOMPARE_API_KEY` environment variable.
Keep `google-services.json` and signing files local to the development machine or provide them through CI secrets.
