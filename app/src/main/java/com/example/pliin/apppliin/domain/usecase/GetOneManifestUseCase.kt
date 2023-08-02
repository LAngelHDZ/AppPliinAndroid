package com.example.pliin.apppliin.domain.usecase

import android.util.Log
import com.example.pliin.apppliin.data.repositories.ManifestRepository
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.Data
import javax.inject.Inject

class GetOneManifestUseCase @Inject constructor(
    private val manifestRepository: ManifestRepository,
    private val reloginUseCase: ReloginUseCase,
    private val loadEmployeeUseCase: LoadEmployeeUseCase
) {
    suspend operator fun invoke(claveManifest: String):List<Data?>?{
        reloginUseCase()
        val data: List<String> = listOf("", "", "1",claveManifest)
        val response = manifestRepository.getManifest(data)
        Log.d("Manifest", "$response")
        return response.response?.data
    }
}