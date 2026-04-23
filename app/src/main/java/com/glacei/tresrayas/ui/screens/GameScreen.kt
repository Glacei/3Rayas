package com.glacei.tresrayas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glacei.tresrayas.viewmodel.GameViewModel
import kotlin.math.roundToInt

// Extensión necesaria para calcular los límites
import androidx.compose.ui.layout.boundsInRoot

@Composable
fun GameScreen(mode: String, p1: String, p2: String, vm: GameViewModel) {
    // Inicializar datos solo una vez
    LaunchedEffect(Unit) {
        vm.isCpuMode = (mode == "1P")
        vm.p1Name = p1
        vm.p2Name = p2
    }

    // Guardamos los límites (Rectángulos) de cada casilla
    val cellBounds = remember { mutableStateMapOf<Int, androidx.compose.ui.geometry.Rect>() }

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Torn de: ${if (vm.currentPlayer == "X") vm.p1Name else vm.p2Name}", fontSize = 24.sp)

        Spacer(Modifier.height(20.dp))

        // Tablero 3x3
        Box(Modifier.size(300.dp).background(Color.DarkGray)) {
            Column {
                for (row in 0..2) {
                    Row {
                        for (col in 0..2) {
                            val index = row * 3 + col
                            Box(
                                Modifier
                                    .size(100.dp)
                                    .padding(4.dp)
                                    .background(Color.White)
                                    .onGloballyPositioned { layoutCoordinates ->
                                        // Guardamos la posición exacta de la casilla en la pantalla
                                        cellBounds[index] = layoutCoordinates.boundsInRoot()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = vm.board[index],
                                    fontSize = 48.sp,
                                    color = if (vm.board[index] == "X") Color.Blue else Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(40.dp))

        // Ficha que se arrastra (solo aparece si no hay ganador)
        if (vm.winner == null && !vm.isDraw) {
            Text("Arrossega la fitxa al taulell:", fontSize = 14.sp)
            Spacer(Modifier.height(10.dp))

            DraggablePiece(vm.currentPlayer) { finalGlobalOffset ->
                // Buscamos si el punto donde soltamos está dentro de alguna casilla
                val targetIndex = cellBounds.entries.find { it.value.contains(finalGlobalOffset) }?.key

                if (targetIndex != null) {
                    vm.onDrop(targetIndex)
                }
            }
        }

        // Diálogo de victoria/empate
        if (vm.winner != null || vm.isDraw) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text(if (vm.isDraw) "Empat!" else "Guanyador: ${vm.winner}") },
                confirmButton = {
                    Button(onClick = { vm.reset() }) { Text("Reiniciar") }
                }
            )
        }
    }
}

@Composable
fun DraggablePiece(symbol: String, onDrop: (Offset) -> Unit) {
    var offset by remember { mutableStateOf(Offset.Zero) }
    var globalPosition by remember { mutableStateOf(Offset.Zero) }

    Box(
        Modifier
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .size(80.dp)
            .onGloballyPositioned { globalPosition = it.positionInRoot() } // Seguimos la posición real
            .background(if (symbol == "X") Color.Blue else Color.Red, CircleShape)
            .pointerInput(symbol) { // Cambiar el símbolo reinicia el estado
                detectDragGestures(
                    onDragEnd = {
                        onDrop(globalPosition) // Enviamos la posición global al soltar
                        offset = Offset.Zero // La ficha visual vuelve a su sitio
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offset += dragAmount
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(symbol, color = Color.White, fontSize = 40.sp)
    }
}

