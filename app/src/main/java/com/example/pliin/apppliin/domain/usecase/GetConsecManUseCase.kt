package com.example.pliin.apppliin.domain.usecase

import android.util.Log
import com.example.pliin.apppliin.data.repositories.ManifestRepository
import javax.inject.Inject

class GetConsecManUseCase @Inject constructor(
    private val manifestRepository: ManifestRepository
) {

    suspend operator fun invoke(date: String): Int? {
        val response = manifestRepository.getConsecutivoMan(date)
        val consecutivo = response.response?.data!![0]?.fieldData?.consecutivoFolio
        Log.d("Objeto consecutivo", "$response")
        Log.i("Consecutivo", "$consecutivo")
        return consecutivo
    }
}