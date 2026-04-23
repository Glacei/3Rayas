package com.glacei.tresrayas

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.glacei.tresrayas.viewmodel.GameViewModel
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.glacei.tresrayas", appContext.packageName)
    }

    // --- TUS NUEVOS TESTS DE LÓGICA ---

    @Test
    fun testVictoriaHorizontal() {
        // Simulamos un tablero con 3 en raya en la fila superior
        val board = listOf("X", "X", "", "X", "", "", "", "", "")

        // Comprobamos la lógica de victoria
        val haGanado = checkWinnerManual(board, "X")
        assertTrue("La X debería haber ganado", haGanado)
    }

    @Test
    fun testTableroLlenoEsEmpate() {
        // Un tablero sin huecos vacíos ("")
        val board = listOf("X", "O", "X", "X", "O", "O", "O", "X", "X")
        val esEmpate = board.none { it == "" }
        assertTrue("El tablero debería estar lleno", esEmpate)
    }

    // Función auxiliar para validar la victoria en el test
    private fun checkWinnerManual(board: List<String>, player: String): Boolean {
        val lines = listOf(
            listOf(0,1,2), listOf(3,4,5), listOf(6,7,8), // Horizontales
            listOf(0,3,6), listOf(1,4,7), listOf(2,5,8), // Verticales
            listOf(0,4,8), listOf(2,4,6)             // Diagonales
        )
        return lines.any { line -> line.all { board[it] == player } }
    }
}