package com.glacei.tresrayas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.glacei.tresrayas.Routes

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Tres En Raya", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            // Ahora Routes.SETUP debería reconocerse
            onClick = { navController.navigate(Routes.SETUP) },
            modifier = Modifier.width(200.dp)
        ) {
            Text("Jugar")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            // Ahora Routes.CREDITS debería reconocerse
            onClick = { navController.navigate(Routes.CREDITS) },
            modifier = Modifier.width(200.dp)
        ) {
            Text("Crèdits")
        }
    }
}

// VIVA EL BETIS
