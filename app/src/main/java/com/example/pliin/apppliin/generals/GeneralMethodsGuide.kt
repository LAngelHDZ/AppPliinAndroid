package com.example.pliin.apppliin.generals

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class GeneralMethodsGuide @Inject constructor() {

    fun validateFormatGuia(guia: String): Boolean {
        val pattern = Regex("^([1]{1})([A-Z]{1})([A-Z0-9]{16})$")
        return pattern.matches(guia)
    }

    fun reemplazaCaracter(cadena: String): String {
        var arreglo = cadena.toCharArray()
        for (i in arreglo.indices) {
            if (arreglo[i] == '/') {
                arreglo[i] = ' '
            }
        }
        var StringConvert = arreglo.joinToString(separator = "")
        Log.i("String ", StringConvert)
        return StringConvert
    }

    fun reemplazaCaracter(cadena: String, caracter: Char): String {
        var arreglo = cadena.toCharArray()
        for (i in arreglo.indices) {
            if (arreglo[i] == caracter) {
                arreglo[i] = ' '
            }
        }
        var StringConvert = arreglo.joinToString(separator = "")
        Log.i("String ", StringConvert)
        return StringConvert.replace("\\s+".toRegex(), "")
    }

    fun reemplazaCaracter(cadena: String, caracter: Char,remplazo:Char): String {
        var arreglo = cadena.toCharArray()
        for (i in arreglo.indices) {
            if (arreglo[i] == caracter) {
                arreglo[i] = remplazo
            }
        }
        var stringConvert = arreglo.joinToString(separator = "")
        Log.i("String ", stringConvert)
        return stringConvert
    }


    fun quitarsimbolos(text: String): String {
        var arreglo = text.toCharArray()
        for (i in arreglo.indices) {
            when (arreglo[i]) {
                'á' -> {
                    arreglo[i] = 'a'
                }
                'é' -> {
                    arreglo[i] = 'e'
                }
                'í' -> {
                    arreglo[i] = 'i'
                }

                'ó' -> {
                    arreglo[i] = 'o'
                }

                'ú' -> {
                    arreglo[i] = 'u'
                }
            }
            Log.i("Cambiado", arreglo[i].toString())
        }
        var stringConvert = arreglo.joinToString()
        Log.i("String ", stringConvert)
        return stringConvert
    }

    fun toUpperLetter(text: String): String {
        return text.uppercase()
    }

    fun toLowerLetter(text: String): String {
        return text.lowercase()
    }

     fun checkInternetConnection(): Boolean {
        return ping("www.google.com")
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
}