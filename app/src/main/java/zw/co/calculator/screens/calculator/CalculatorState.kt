package zw.co.calculator.screens.calculator

sealed class CalculatorState {
    object Initial : CalculatorState()

    class Update(val state: String) : CalculatorState()

    class Error(val state:String,val message:String):CalculatorState()
}