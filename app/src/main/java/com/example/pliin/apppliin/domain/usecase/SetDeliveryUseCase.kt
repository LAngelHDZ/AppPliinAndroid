package com.example.pliin.apppliin.domain.usecase

import android.util.Log
import com.example.pliin.apppliin.data.repositories.DeliveryRepository
import com.example.pliin.apppliin.data.repositories.GuideRepository
import com.example.pliin.apppliin.data.repositories.UsersRepository
import javax.inject.Inject

class SetDeliveryUseCase @Inject constructor(
    private val deliveryR: DeliveryRepository,
    private val usersRepository: UsersRepository,
    private val guiderepository: GuideRepository,
    private val getGuideCodUseCase: GetGuideCodUseCase
){

    suspend operator fun invoke(
        guide: String?,
        idPreM: String,
        recordId: String?,
        status: String?,
        recibe: String?,
        parentOrFailDelivery: String?,
        url: String,
        urlPago: String?,
        pago: String,
        typePago: String
    ): Boolean {
//        Log.i("status intento entrega", parentOrFailDelivery!!)
//        Log.i("quien recibe", recibe!!)

//        val userData = usersRepository.getAllUserDatabase()
//
//        val user = userData.user!!{

        val responseDelivery = deliveryR.setDelivery(guide!!, recibe, parentOrFailDelivery,typePago,pago,idPreM)

        val messageDelivery = responseDelivery.messages!![0].code

        val recordIdDelivery= responseDelivery.response?.recordId

        if (pago.equals("CONFIRMADO")){
           val setPhoto = deliveryR.setDeliveryPhoto(recordIdDelivery!!,urlPago!!,"pagoContenedor")

            val guideCod=getGuideCodUseCase.invoke(guide)

            val recordid=guideCod.response?.data?.get(0)?.recordId

            val responseUpdatePago = deliveryR.setUpdatePago(pago, recordId!!,"manifiestoPaquetes")

            val responseUpdateCod = deliveryR.setUpdatePago(pago, recordid!!,"CodAPI")

        }

        val setPhoto = deliveryR.setDeliveryPhoto(recordIdDelivery!!,url,"contenedorFoto")
        val messageResponsePhoto = setPhoto.messages


        val responseUpdateStaus = deliveryR.setUpdateStatus(status, recordId!!,"manifiestoPaquetes")
        val messageUpdateStatus = responseUpdateStaus.messages!![0].code

        val responseCreateStatus = deliveryR.setCreateStatus(guide, status!!)
        val messageCreateStatus = responseCreateStatus.messages!![0].code

        val responseTryDelivery = deliveryR.setTryDelivery(guide, status, "Exitoso")
        val messageTryDelivery = responseTryDelivery.messages!![0].code

        val responseGuide = guiderepository.getGuideQueryApi(guide,"Asignado",idPreM)
        val recordIdGuide = responseGuide.response?.data?.get(0)?.recordId

        val updateRGuiasManifest = deliveryR.setUpdateStatus(status, recordIdGuide!!,"GuiasAPI")
        val messageUpdateGuide = updateRGuiasManifest.messages?.get(0)?.code
        
        // return false
        return messageDelivery.equals("0") and messageUpdateStatus.equals("0") and messageCreateStatus.equals(
            "0"
        ) and messageTryDelivery.equals("0") and messageUpdateGuide.equals("0")
    }
}