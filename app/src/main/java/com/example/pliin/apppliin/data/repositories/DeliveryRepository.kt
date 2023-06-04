package com.example.pliin.apppliin.data.repositories

import android.util.Log
import com.example.pliin.apppliin.data.network.services.DeliveryService
import com.example.pliin.apppliin.data.network.services.UpdateStatusGuideService
import com.example.pliin.apppliin.domain.model.responseregisterdeliveryi.ResponseRegisterDeliveryItem
import com.example.pliin.apppliin.domain.model.responseregisterdeliveryi.toDomain
import javax.inject.Inject

class DeliveryRepository @Inject constructor(
    private val apidelivery: DeliveryService,
    private val apiupdate: UpdateStatusGuideService
) {

    //Opbtiene las guias de la API
    suspend fun setDelivery(
        guide: String,
        recibe: String?,
        parent: String?
    ): ResponseRegisterDeliveryItem {
        val response = apidelivery.setDelivery(guide, recibe, parent)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Opbtiene las response de la actualizacion del status de la guia
    suspend fun setUpdateStatus(status: String?, recordId: String): ResponseRegisterDeliveryItem {
        val response = apiupdate.setUpdateStatus(status, recordId)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Opbtiene las response de la actualizacion del status de la guia
    suspend fun setCreateStatus(guide: String, status: String): ResponseRegisterDeliveryItem {
        val response = apiupdate.setCreateStatus(guide, status)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Opbtiene las response de la actualizacion del status de la guia
    suspend fun setTryDelivery(guide: String, status: String): ResponseRegisterDeliveryItem {
        val response = apiupdate.setIntentoEntrega(guide, status)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

}