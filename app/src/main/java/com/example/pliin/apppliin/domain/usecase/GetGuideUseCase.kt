package com.example.pliin.apppliin.domain.usecase

import android.annotation.SuppressLint
import android.util.Log
import com.example.pliin.apppliin.data.repositories.GuideRepository
import com.example.pliin.apppliin.domain.model.deliveryItem.FieldDataItem
import com.example.pliin.apppliin.domain.model.deliveryItem.GetDataGuideRDItem
import javax.inject.Inject

class GetGuideUseCase @Inject constructor(
    private val repository: GuideRepository,
    private val reloginUseCase: ReloginUseCase
) {
    private var message: String? = ""
    private var codeStatus: String? = ""
    private var login: Boolean = false

    @SuppressLint("SuspiciousIndentation")
    suspend operator fun invoke(
        guide: String,
        observacion:String
    ): List<String?>{
        var guides: GetDataGuideRDItem
        do {
            Log.i("Guia escaneada", guide)
            guides = repository(guide,observacion,"")
            message = guides.messages!![0]!!.message
            codeStatus = guides.messages!![0]!!.code

            login = if (codeStatus.equals("952")) {
                val resposelogin = reloginUseCase.invoke()
                true
            } else {
                false
            }
        } while (login)

        return if (codeStatus.equals("0") && message.equals("OK")) {
            GetData(guides)
        } else if (codeStatus.equals("500")) {
            listOf("500", "", "", "", "", "", "", "", "", "", "", "","","","")
        } else {
            listOf("", "", "", "", "", "", "", "", "", "", "", "","","","")
        }
    }

    suspend fun repository(guide: String,observation:String,IdPreM:String): GetDataGuideRDItem {
        return repository.getGuideQueryApi(guide,observation,IdPreM)
    }

    private fun fiedDatanull(): FieldDataItem {
        return FieldDataItem("", "", "", "", "", "", "", "", 0.0, "", "", 0.0, "")
    }

    private fun GetData(guides: GetDataGuideRDItem): List<String?> {
        val response = guides.response!!.data!![0]
        Log.i("record Id en lista", "${response?.portalData?.manifiestoPaquetes!![0]!!.recordId}")
        return listOf(
            refillvacio(response.fieldData?.idGuia),
            refillvacio(response.fieldData?.idPreM),
            refillvacio(response.fieldData?.direccionesNombre),
            refillvacio(response.fieldData?.direccionesDircompleta),
            refillvacio(response.fieldData?.direccionesTelefono),
            refillvacio(response.fieldData?.direccionesCODS),
            refillvacio(response.fieldData?.manifiestoPaquetesEstatus),
            refillvacio(response.fieldData?.preManifiestosEmpresa),
            refillvacio(response.fieldData?.preManifiestosRuta),
            refillvacio(response.fieldData?.pesokg.toString()),
            refillvacio(response.fieldData?.valorGuia.toString()),
            refillvacio(response.portalData.manifiestoPaquetes[0]!!.recordId),
            refillvacio(response.fieldData?.statusIntento),
            refillvacio(response.recordId),
            refillvacio(response.fieldData?.observacion)
        )
    }

    private fun refillvacio(campo: String?): String {
        return if (campo.isNullOrEmpty()) {
            "No value"
        } else {
            campo
        }
    }
}