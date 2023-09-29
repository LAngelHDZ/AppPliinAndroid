package com.example.pliin.apppliin.data.repositories

import android.util.Log
import com.example.pliin.apppliin.data.database.dao.ManifestDao
import com.example.pliin.apppliin.data.database.entities.toDatabase
import com.example.pliin.apppliin.data.model.deliverymodel.GetDataGuideDRModel
import com.example.pliin.apppliin.data.model.responserudmodel.ResponseRUDModel
import com.example.pliin.apppliin.data.network.services.ManifestService
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.ConsecutivoManItem
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.FieldData
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.toDomain
import com.example.pliin.apppliin.domain.model.deliveryItem.GetDataGuideRDItem
import com.example.pliin.apppliin.domain.model.deliveryItem.toDomain
import com.example.pliin.apppliin.domain.model.responseruditem.ResponseRUDItem
import com.example.pliin.apppliin.domain.model.responseruditem.toDomain
import javax.inject.Inject

class ManifestRepository @Inject constructor(
    private val manifestService: ManifestService,
    private val daoMaifest:ManifestDao

) {

//    Metodos para funcionar con el API

//    FUncion para obtener el numero conseutivo del ultimo manifiesto registrado
    suspend fun getConsecutivoMan(date: String): ConsecutivoManItem {
        val response = manifestService.consecutivoMan(date)
        Log.d("En retrofit objeto M", "$response")
        return response.toDomain()
    }


//    Funcion para obtener una lista de manifiestos por fecha po por empleados asignado
    suspend fun getManifest(data: List<String>): ConsecutivoManItem {
        val response = manifestService.getManifest(data)
        Log.d("En retrofit objeto M", "$response")
        return response.toDomain()
    }

//    Funcion para obtener la informacion de un manifiesto por su clave Principàl
    suspend fun getGuidesManifest(claveManifest:String):GetDataGuideRDItem {
        val response = manifestService.getGuidesManifest(claveManifest)
        Log.d("En retrofit objeto M", "$response")
        return response.toDomain()
    }

    //FUncion para reasignar las Guias de un manifiesto
    suspend fun reasignarGuideManifest(recordId:String):ResponseRUDItem{
        val response = manifestService.reasignarGuideManifest(recordId)
        return response.toDomain()
    }


//    Funcion para crear un manifieto en el sistema
    suspend fun createManifest(data: List<String?>): ResponseRUDItem {
        val response = manifestService.createManifest(data as List<String>)
        Log.d("En retrofit objeto M", "$response")
        return response.toDomain()
    }

//    Funcion para crear una actualizacion de manifiesto en el sistema
    suspend fun updateManifest(data: List<String?>): ResponseRUDItem {
        val response = manifestService.updateManifest(data as List<String>)
        Log.d("En retrofit objeto M", "$response")
        return response.toDomain()
    }

//    Metodos para funcionar en la BD Local del movil


//    Funcion para recuperar la informacion de la BD local
    suspend fun getDBManifest(folioManifest: String): FieldData? {
        val response = daoMaifest.getManifest(folioManifest)
        return response?.toDomain()
    }

    //    Funcion para recuperar la informacion de la BD local
    suspend fun getAllDBManifest():List<FieldData?>  {
        val response = daoMaifest.getAllManifest()
        return response.map { it?.toDomain() }
    }

    //    Funcion para recuperar la informacion de la BD local
    suspend fun getAllDBManifestAplicado():List<FieldData?>  {
        val response = daoMaifest.getAllManifestAplicado()
        return response.map { it?.toDomain() }
    }

    //Funcion para recuperar la informacion del manifiesto de la DB
    suspend fun saveManifestDB(dataManifest:FieldData){
        val response = daoMaifest.insertManifest(dataManifest.toDatabase())
    }


    //Actualiza el status del manifiesto local para llevar el control de los manifiestos
    suspend fun updateStatusManifestDB(claveManifest: String, status: String) {
        return daoMaifest.updateStatusManifest(claveManifest,status)
    }
}