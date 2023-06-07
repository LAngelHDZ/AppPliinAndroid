package com.example.pliin.apppliin.data.repositories

import android.util.Log
import com.example.pliin.apppliin.data.network.services.ManifestService
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.ConsecutivoManItem
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.toDomain
import javax.inject.Inject

class ManifestRepository @Inject constructor(private val manifestService: ManifestService) {

    suspend fun getConsecutivoMan(date: String): ConsecutivoManItem {
        val response = manifestService.consecutivoMan(date)
        Log.d("En retrofit objeto M", "$response")
        return response.toDomain()
    }
}