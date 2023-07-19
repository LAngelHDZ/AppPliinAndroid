package com.example.pliin.apppliin.data.repositories

import android.util.Log
import com.example.pliin.apppliin.data.model.deliverymodel.GetDataGuideDRModel
import com.example.pliin.apppliin.data.model.responserudmodel.ResponseRUDModel
import com.example.pliin.apppliin.data.network.services.ManifestService
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.ConsecutivoManItem
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.toDomain
import com.example.pliin.apppliin.domain.model.deliveryItem.GetDataGuideRDItem
import com.example.pliin.apppliin.domain.model.deliveryItem.toDomain
import com.example.pliin.apppliin.domain.model.responseruditem.ResponseRUDItem
import com.example.pliin.apppliin.domain.model.responseruditem.toDomain
import javax.inject.Inject

class ManifestRepository @Inject constructor(private val manifestService: ManifestService) {

    suspend fun getConsecutivoMan(date: String): ConsecutivoManItem {
        val response = manifestService.consecutivoMan(date)
        Log.d("En retrofit objeto M", "$response")
        return response.toDomain()
    }

    suspend fun getManifest(data: List<String>): ConsecutivoManItem {
        val response = manifestService.getManifest(data)
        Log.d("En retrofit objeto M", "$response")
        return response.toDomain()
    }

    suspend fun getGuidesManifest(claveManifest:String):GetDataGuideRDItem {
        val response = manifestService.getGuidesManifest(claveManifest)
        Log.d("En retrofit objeto M", "$response")
        return response.toDomain()
    }

    //Metodo para reasignar las Guias de un manifiesto
    suspend fun reasignarGuideManifest(recordId:String):ResponseRUDItem{
        val response = manifestService.reasignarGuideManifest(recordId)
        return response.toDomain()
    }

    suspend fun createManifest(data: List<String?>): ResponseRUDItem {
        val response = manifestService.createManifest(data as List<String>)
        Log.d("En retrofit objeto M", "$response")
        return response.toDomain()
    }

    suspend fun updateManifest(data: List<String?>): ResponseRUDItem {
        val response = manifestService.updateManifest(data as List<String>)
        Log.d("En retrofit objeto M", "$response")
        return response.toDomain()
    }
}