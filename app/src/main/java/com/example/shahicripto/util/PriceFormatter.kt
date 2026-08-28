package com.example.shahicripto.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

private const val MAX_DECIMAL_PLACES = 6

/** Formats crypto values without scientific notation and never beyond 6 decimals. */
fun formatCryptoPrice(value: Double?): String {
    if (value == null || !value.isFinite()) return "—"

    val rounded = BigDecimal.valueOf(value)
        .setScale(MAX_DECIMAL_PLACES, RoundingMode.DOWN)
    val formatter = DecimalFormat(
        "#,##0.######",
        DecimalFormatSymbols(Locale.US)
    ).apply {
        roundingMode = RoundingMode.DOWN
        isGroupingUsed = true
    }
    val formatted = formatter.format(rounded)

    return if (formatted == "-0" || formatted == "-0.0") "0" else formatted
}

/** Formats general dashboard numbers with thousands separators and two decimals. */
fun formatGroupedNumber(value: Double?): String {
    if (value == null || !value.isFinite()) return "—"

    val formatter = DecimalFormat(
        "#,##0.00",
        DecimalFormatSymbols(Locale.US)
    ).apply {
        roundingMode = RoundingMode.DOWN
        isGroupingUsed = true
    }
    return formatter.format(BigDecimal.valueOf(value))
}

fun formatCryptoPriceText(value: String?): String {
    val numeric = value?.replace(",", "")?.toDoubleOrNull()
    return if (numeric == null) value ?: "—" else formatCryptoPrice(numeric)
}

fun formatGroupedNumberText(value: String?): String {
    val numeric = value?.replace(",", "")?.toDoubleOrNull()
    return if (numeric == null) value ?: "—" else formatGroupedNumber(numeric)
}
