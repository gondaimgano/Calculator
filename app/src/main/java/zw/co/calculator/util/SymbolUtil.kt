package zw.co.calculator.util

object SymbolUtil {
    private val symbols = listOf("-", "+", "/", "x", "=", "<<","C")
    val numbers =
        listOf("C","0", "1","<<", "2", "3", "4", "+","5", "6", "7", "-","8", "9", "/", "x", "=")

    fun contains(x: String): Boolean {

        return symbols.contains(x);
    }

}