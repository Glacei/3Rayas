package com.glacei.tresrayas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.glacei.tresrayas.Routes

@Composable
fun SetupScreen(navController: NavController) {
    var isCpu by remember { mutableStateOf(false) }
    var p1 by remember { mutableStateOf("") }
    var p2 by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Configuració", style = MaterialTheme.typography.headlineMedium)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Contra CPU")
            Switch(checked = isCpu, onCheckedChange = { isCpu = it })
        }

        TextField(value = p1, onValueChange = { p1 = it }, label = { Text("Nom Jugador 1") })

        if (!isCpu) {
            TextField(value = p2, onValueChange = { p2 = it }, label = { Text("Nom Jugador 2") })
        }

        Button(onClick = {
            val mode = if (isCpu) "1P" else "2P"
            navController.navigate(Routes.createGameRoute(mode, p1.ifEmpty { "P1" }, p2.ifEmpty { "CPU" }))
        }) {
            Text("Començar Partida")
        }
    }
}