package com.example.pliin.apppliin.data.repositories

import android.util.Log
import com.example.pliin.apppliin.data.model.deliverymodel.GetDataGuideDRModel
import com.example.pliin.apppliin.data.model.responseregisterdelivery.ResponseRegisterDeliveryModel
import com.example.pliin.apppliin.data.network.services.GuideService
import com.example.pliin.apppliin.domain.model.deliveryItem.GetDataGuideRDItem
import com.example.pliin.apppliin.domain.model.deliveryItem.toDomain
import com.example.pliin.apppliin.domain.model.queryguideitem.QueryGuidePliinItem
import com.example.pliin.apppliin.domain.model.queryguideitem.toDomain
import com.example.pliin.apppliin.domain.model.responseregisterdeliveryi.ResponseRegisterDeliveryItem
import com.example.pliin.apppliin.domain.model.responseregisterdeliveryi.toDomain
import javax.inject.Inject

class GuideRepository @Inject constructor(private val apiguide: GuideService) {

    //Opbtiene las guias de la API
    suspend fun getGuideQueryApi(guide: String): GetDataGuideRDItem {
        val response = apiguide.getGuide(guide)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Opbtiene las guias de la API
    suspend fun validateGuideApi(guide: String): QueryGuidePliinItem {
        val response = apiguide.queryGuide(guide)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Opbtiene las guias de la API
    suspend fun InsertGuidetoApi(guide: String): ResponseRegisterDeliveryItem {
        val response = apiguide.insertyGuide(guide)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Opbtiene las guias de la API
    suspend fun GuideQueryAarrastre(guide: String): GetDataGuideRDItem {
        val response = apiguide.getGuide(guide)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }
}