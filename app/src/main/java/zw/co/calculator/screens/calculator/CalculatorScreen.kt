package zw.co.calculator.screens.calculator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import zw.co.calculator.util.CalcSymbol
import zw.co.calculator.util.SymbolUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(controller: NavController, viewModel: CalculatorViewModel = hiltViewModel()) {
    val model by viewModel.uiState.collectAsState()
    val target = when (model) {
        CalculatorState.Initial -> CalcSymbol.ZERO.symbol
        is CalculatorState.Update -> (model as CalculatorState.Update).state
        is CalculatorState.Error -> (model as CalculatorState.Error).state
    }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Surface(

                modifier = Modifier
                    .padding(8.dp),
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = target,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .fillMaxWidth(0.98f)
                        .heightIn(min=(screenHeight*0.15).dp,
                            max=(screenHeight*0.15).dp),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.End,

                    )
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(8.dp)

            ) {

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = (screenWidth / 5).dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp), 
                    modifier = Modifier.heightIn(min=(screenHeight*0.80).dp,
                        max=(screenHeight*0.80).dp).fillMaxWidth(0.98f)

                ) {

                    items(SymbolUtil.numbers) { v ->
                        TextButton(onClick = { viewModel.update(v) }) {
                            Text(
                                text = v,
                                style = MaterialTheme.typography.headlineSmall.copy(color = Color.White)
                            )
                        }
                    }
                }
            }

        }
    }
}