package zw.co.calculator.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import zw.co.calculator.screens.calculator.CalculatorScreen

sealed class Index(
    val route: String,
    val label: String,
    val screen: @Composable (NavController) -> Unit
) {
    object Home : Index("home", "Calculate", {
        CalculatorScreen(it)
    })

}
