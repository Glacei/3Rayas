package com.glacei.tresrayas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.glacei.tresrayas.ui.theme.TresRayasTheme
// IMPORTANTE: Asegúrate de importar tus pantallas si están en otra carpeta
import com.glacei.tresrayas.ui.screens.*
import com.glacei.tresrayas.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TresRayasTheme {
                val navController = rememberNavController()
                // Instanciamos el ViewModel aquí para que sea único
                val gameViewModel: GameViewModel = viewModel()

                NavHost(navController = navController, startDestination = Routes.HOME) {

                    composable(Routes.HOME) {
                        HomeScreen(navController)
                    }

                    composable(Routes.CREDITS) {
                        CreditsScreen(navController)
                    }

                    composable(Routes.SETUP) {
                        SetupScreen(navController)
                    }

                    composable(
                        Routes.GAME,
                        arguments = listOf(
                            navArgument("mode") { type = NavType.StringType },
                            navArgument("p1") { type = NavType.StringType },
                            navArgument("p2") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val mode = backStackEntry.arguments?.getString("mode") ?: "2P"
                        val p1 = backStackEntry.arguments?.getString("p1") ?: "P1"
                        val p2 = backStackEntry.arguments?.getString("p2") ?: "P2"

                        // Llamamos a la pantalla real pasando el ViewModel
                        GameScreen(mode, p1, p2, gameViewModel)
                    }
                }
            }
        }
    }
}
    fun createGameRoute(mode: String, p1: String, p2: String) = "game/$mode/$p1/$p2"
