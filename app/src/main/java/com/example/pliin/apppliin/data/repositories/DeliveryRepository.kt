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
        parent: String?,
        typePago: String?,
        pago: String
    ): ResponseRegisterDeliveryItem {
        val response = apidelivery.setDelivery(guide, recibe, parent,typePago,pago)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    suspend fun setDeliveryPhoto(recordId: String,url:String,campo:String):ResponseRegisterDeliveryItem{
        val response = apidelivery.setDeliveryPhoto(url,recordId,campo)
        Log.i("response", "$response")
        return response.toDomain()
    }

    //Opbtiene las response de la actualizacion del status de la guia
    suspend fun setUpdateStatus(status: String?, recordId: String,presentacion:String): ResponseRegisterDeliveryItem {
        val response = apiupdate.setUpdateStatus(status, recordId,presentacion)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Actualiza el status del pago del cod
    suspend fun setUpdatePago(pago: String?, recordId: String,presentacion:String): ResponseRegisterDeliveryItem {
        val response = apiupdate.setUpdatePago(pago, recordId,presentacion)
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
    suspend fun setTryDelivery(
        guide: String,
        status: String,
        comment: String
    ): ResponseRegisterDeliveryItem {
        val response = apiupdate.setIntentoEntrega(guide, status, comment)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

}