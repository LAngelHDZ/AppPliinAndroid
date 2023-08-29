package com.example.pliin.apppliin.domain.usecase

import android.util.Log
import com.example.pliin.apppliin.data.repositories.DeliveryRepository
import com.example.pliin.apppliin.data.repositories.GuideRepository
import com.example.pliin.apppliin.data.repositories.UsersRepository
import javax.inject.Inject

class SetTryDeliveryUseCse @Inject constructor(
    private val deliveryR: DeliveryRepository,
    private val usersRepository: UsersRepository,
    private val guiderepository: GuideRepository,
) {
    suspend operator fun invoke(
        guide: String?,
        idPreM: String,
        recordId: String?,
        status: String?,
        parentOrFailDelivery: String,
        comment: String
    ): Boolean {
        val userData = usersRepository.getAllUserDatabase()
        val user = userData.user
        Log.i("user", user.toString())
        Log.i("seleted", parentOrFailDelivery)
        Log.i("status intento entrega eb use case", parentOrFailDelivery!!)
        //  Log.i("seleted", selectedOption.value!!)

        if (parentOrFailDelivery.equals("RECHAZADO")){
            val responseDelivery = deliveryR.setDelivery(
                guide!!,
                "No recibido",
                parentOrFailDelivery,
                "NO APLICA",
                "NO APLICA"
            )
            val messageDelivery = responseDelivery.messages!![0]!!.code
        }
        val responseUpdateStaus = deliveryR.setUpdateStatus(status, recordId!!,"manifiestoPaquetes")
        val messageUpdateStatus = responseUpdateStaus.messages!![0]!!.code
        val responseCreateStatus = deliveryR.setCreateStatus(guide!!, status!!)
        val messageCreateStatus = responseCreateStatus.messages!![0]!!.code
        val responseTryDelivery = deliveryR.setTryDelivery(guide!!, parentOrFailDelivery, comment)
        val messageTryDelivery = responseTryDelivery.messages!![0]!!.code

        val responseGuide = guiderepository.getGuideQueryApi(guide,"Asignado",idPreM)
        val recordIdGuide = responseGuide.response?.data?.get(0)?.recordId

        val updateRGuiasManifest = deliveryR.setUpdateStatus(status, recordIdGuide!!,"GuiasAPI")
        val messageUpdateGuide = updateRGuiasManifest.messages?.get(0)?.code

        //  return false
        return messageUpdateStatus.equals("0") and messageCreateStatus.equals("0") and messageTryDelivery.equals(
            "0"
        )
    }
}