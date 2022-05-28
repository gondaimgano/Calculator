package zw.co.calculator.screens.calculator

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zw.co.calculator.repos.OperationRepository
import zw.co.calculator.util.CalcSymbol
import zw.co.calculator.util.SymbolUtil
import zw.co.calculator.util.toFormattedNumber
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel
@Inject
constructor(
    val repository: OperationRepository
) : ViewModel() {
    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<CalculatorState>(CalculatorState.Initial)

    // The UI collects from this StateFlow to get its state updates
    val uiState = _uiState.asStateFlow()

    private var current = CalcSymbol.ZERO.symbol
    private var store = CalcSymbol.ZERO.symbol
    private var symbol = ""
    private var job: Job? = null

    private fun cancel() {
        job?.cancel()
    }


    fun update(x: String) {
        cancel()
        job = viewModelScope.launch {
            try {
                operation(x)
                _uiState.emit(CalculatorState.Update(current))
            } catch (ex: Throwable) {

                _uiState.emit(CalculatorState.Error("0", ex.localizedMessage ?: "Unknown Error"))
            }
        }
    }

    private fun operation(x: String) {

        when (x) {
            CalcSymbol.PLUS.symbol,
            CalcSymbol.MINUS.symbol,
            CalcSymbol.DIVIDE.symbol,
            CalcSymbol.MULTIPLY.symbol -> {
                symbol = x
                store = current
                current = ""
            }

            CalcSymbol.EQUAL.symbol ->
                when (symbol) {
                    CalcSymbol.MINUS.symbol -> current =
                        store.toBigDecimal().subtract(current.toBigDecimal()).toString()
                    CalcSymbol.PLUS.symbol -> current =
                        current.toBigDecimal().add(store.toBigDecimal()).toString()
                    CalcSymbol.DIVIDE.symbol -> current =
                        store.toBigDecimal().divide(current.toBigDecimal(), 8, RoundingMode.HALF_UP)
                            .toString()
                    CalcSymbol.MULTIPLY.symbol -> current =
                        current.toBigDecimal().multiply(store.toBigDecimal()).toString()
                }

            CalcSymbol.BACKSPACE.symbol -> {
                current = current.dropLast(1)
            }

            CalcSymbol.CANCEL.symbol -> {
                current = CalcSymbol.ZERO.symbol
            }

        }



        if (!SymbolUtil.contains(x)) {
            if (current == CalcSymbol.ZERO.symbol) {
                current = x
            } else {
                current += x
            }
        }
    }

}