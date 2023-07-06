package com.example.pliin.apppliin.generals

import android.util.Log
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
        return StringConvert.replace("\\s+".toRegex(), "")
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
        var StringConvert = arreglo.joinToString(separator = "")
        Log.i("String ", StringConvert)
        return StringConvert.replace("\\s+".toRegex(), "")
    }

    fun toUpperLetter(text: String): String {
        return text.uppercase()
    }
}