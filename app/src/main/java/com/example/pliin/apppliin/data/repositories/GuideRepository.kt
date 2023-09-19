package com.example.pliin.apppliin.data.repositories

import android.util.Log
import com.example.pliin.apppliin.data.database.dao.GuideDao
import com.example.pliin.apppliin.data.database.entities.toDatabase

import com.example.pliin.apppliin.data.network.dto.datospqt.DatosPqtDto
import com.example.pliin.apppliin.data.network.services.GuideService
import com.example.pliin.apppliin.domain.model.GuideItem
import com.example.pliin.apppliin.domain.model.deliveryItem.GetDataGuideRDItem
import com.example.pliin.apppliin.domain.model.deliveryItem.toDomain
import com.example.pliin.apppliin.domain.model.queryguideitem.QueryGuidePliinItem
import com.example.pliin.apppliin.domain.model.queryguideitem.toDomain
import com.example.pliin.apppliin.domain.model.responseruditem.ResponseRUDItem
import com.example.pliin.apppliin.domain.model.responseruditem.toDomain
import com.example.pliin.apppliin.domain.model.toDomain
import javax.inject.Inject

class GuideRepository @Inject constructor(
    private val apiguide: GuideService,
    private val guideDao: GuideDao
) {

    //Opbtiene las guias de la API
    suspend fun getGuideQueryApi(guide: String, observation: String, IdPreM: String): GetDataGuideRDItem {
        val response = apiguide.getGuide(guide,observation,IdPreM)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Opbtiene las guias de la API
    suspend fun getGuideCod(guide: String): GetDataGuideRDItem {
        val response = apiguide.getGuideCod(guide)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Opbtiene las guias de la API
    suspend fun registerGuideCod(guide: String, valueCod: Float, pago: String?): ResponseRUDItem {
        val response = apiguide.registerGuideCod(guide,valueCod,pago)
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

    //Valida si la guia tiene los datos de direccion
    suspend fun direccionValidateApi(guide: String): QueryGuidePliinItem {
        val response = apiguide.queryGuideDireccion(guide)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Registra la direccion de la guia
    suspend fun createDireccion(direecion: List<String>): ResponseRUDItem {
        val response = apiguide.addDireccionGuide(direecion)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Registra los datos del paquete de la guia, Peso y medias
    suspend fun createDataPqt(datosPqt: DatosPqtDto): ResponseRUDItem {
        val response = apiguide.addDatosPqtGuide(datosPqt)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Valida si la guia tiene los datos del paquete
    suspend fun datosPqtValidateApi(guide: String): QueryGuidePliinItem {
        val response = apiguide.queryGuideDatosPqt(guide)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Opbtiene las guias de la API
    suspend fun InsertGuidetoApi(guide: String, pago: String?): ResponseRUDItem {
        val response = apiguide.insertyGuide(guide,pago)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Opbtiene las guias de la API
    suspend fun GuideQueryAarrastre(guide: String, observation: String, idPrem: String): GetDataGuideRDItem {
        val response = apiguide.getGuide(guide,observation,idPrem)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

    //Agrega la guia a un manifiesto
    suspend fun setGuideManifest(data: List<String>, status: String): ResponseRUDItem {
        val response = apiguide.addGuideManifest(data,status)
        Log.i("response", "$response")
        // tokenDao.insertToken(response.toDatabase())
        return response.toDomain()
    }

//Metodos para consultas Locales

    //Gregar guias a DB Local
    suspend fun RegisterGuidesDB(guide:GuideItem){
        val response=guideDao.insertGuide(guide.toDatabase())
    }

    //Gregar guias a DB Local
    suspend fun getGuidesDB(folioManifest: String):List<GuideItem>{
        val response=guideDao.getGuidesDB(folioManifest)
        return response.map{it.toDomain()}
    }
}