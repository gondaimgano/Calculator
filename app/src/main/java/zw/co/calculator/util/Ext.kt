package zw.co.calculator.util

import java.text.NumberFormat

fun String.toFormattedNumber(): String {
    val format = NumberFormat.getCurrencyInstance()
    return format.format(this.toBigDecimal())
}