package com.glacei.tresrayas.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    var board by mutableStateOf(List(9) { "" })
    var currentPlayer by mutableStateOf("X")
    var winner by mutableStateOf<String?>(null)
    var isDraw by mutableStateOf(false)

    var p1Name by mutableStateOf("Jugador 1")
    var p2Name by mutableStateOf("Jugador 2")
    var isCpuMode by mutableStateOf(false)

    // Lógica al soltar la ficha
    fun onDrop(index: Int) {
        if (board[index] == "" && winner == null) {
            val newBoard = board.toMutableList()
            newBoard[index] = currentPlayer
            board = newBoard

            if (checkWinner(currentPlayer)) {
                winner = if (currentPlayer == "X") p1Name else p2Name
            } else if (board.none { it == "" }) {
                isDraw = true
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                if (isCpuMode && currentPlayer == "O") cpuMove()
            }
        }
    }

    private fun cpuMove() {
        viewModelScope.launch {
            delay(600)
            val emptyIndices = board.indices.filter { board[it] == "" }
            if (emptyIndices.isNotEmpty() && winner == null) {
                onDrop(emptyIndices.random())
            }
        }
    }

    private fun checkWinner(p: String): Boolean {
        val lines = listOf(
            listOf(0,1,2), listOf(3,4,5), listOf(6,7,8),
            listOf(0,3,6), listOf(1,4,7), listOf(2,5,8),
            listOf(0,4,8), listOf(2,4,6)
        )
        return lines.any { it.all { i -> board[i] == p } }
    }

    fun reset() {
        board = List(9) { "" }
        currentPlayer = "X"
        winner = null
        isDraw = false
    }
}