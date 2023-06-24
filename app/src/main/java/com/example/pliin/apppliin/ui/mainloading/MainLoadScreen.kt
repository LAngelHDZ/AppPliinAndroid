package com.example.pliin.apppliin.ui.mainloading

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pliin.R
import com.example.pliin.navigation.AppScreen
import kotlinx.coroutines.delay
import java.io.BufferedReader
import java.io.InputStreamReader

private var status: Boolean = false

@Composable
fun MainLoadScreen(
    navigationController: NavHostController,
    NetworkConectivity: Boolean,
    mlViewModel: MLViewModel
) {
    status = NetworkConectivity
    Loading(navigationController, mlViewModel, NetworkConectivity)
}

@Composable
fun Loading(
    navigationController: NavHostController,
    mlViewModel: MLViewModel,
    NetworkConectivity: Boolean
) {
    Screen()
    LaunchedEffect(key1 = true) {
        delay(2000)
        if (NetworkConectivity) {
            mlViewModel.noToken(navigationController)
        } else {
            navigationController.popBackStack()
            navigationController.navigate(AppScreen.FailLoadScreen.route)
        }
    }
}

fun ping(host: String): Boolean {
    val command = "ping -c 4 $host" // El número 4 indica el número de paquetes de ping a enviar

    try {
        val process = Runtime.getRuntime().exec(command)
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String?
        val output = StringBuilder()

        while (reader.readLine().also { line = it } != null) {
            output.append(line + "\n")
        }

        val exitCode = process.waitFor()
        if (exitCode == 0) {
            println("Ping exitoso. Conexión a Internet activa.")
            println(output.toString())
            return true
        } else {
            println("No se pudo hacer ping. Verifica tu conexión a Internet.")
            return false
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}

/*Puedes llamar a la función ping() pasando la dirección IP o el nombre de host que deseas hacer ping. Por ejemplo, para hacer ping a Google:
kotlin
Copy code*/
val host = "www.google.com"
/*
ping(host)
Recuerda que esta función realizará un ping síncrono, lo que significa que bloqueará la ejecución del hilo principal hasta que se complete el ping. Para evitar bloquear la interfaz de usuario, es recomendable ejecutar el ping en un hilo secundario o utilizar técnicas de programación asincrónica, como corrutinas (coroutines) o AsyncTask, según tus necesidades específicas.
*/







@Preview(showSystemUi = true)
@Composable
fun Screen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4c51c6)), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.pliin_logo_blanco),
                contentDescription = "PLIIN",
                modifier = Modifier.size(250.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            
            CircularProgressIndicator(color = Color.White, strokeWidth = 4.dp)
        }
    }
}