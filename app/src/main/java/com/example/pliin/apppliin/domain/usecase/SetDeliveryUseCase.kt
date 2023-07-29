package com.example.pliin.apppliin.domain.usecase

import android.util.Log
import com.example.pliin.apppliin.data.repositories.DeliveryRepository
import com.example.pliin.apppliin.data.repositories.UsersRepository
import javax.inject.Inject

class SetDeliveryUseCase @Inject constructor(
    private val deliveryR: DeliveryRepository,
    private val usersRepository: UsersRepository
) {

    suspend operator fun invoke(
        guide: String?,
        recordId: String?,
        status: String?,
        recibe: String?,
        parentOrFailDelivery: String?,
        url:String
    ): Boolean {
        Log.i("status intento entrega", parentOrFailDelivery!!)
        Log.i("quien recibe", recibe!!)

        val userData = usersRepository.getAllUserDatabase()
        val user = userData.user!!


        val responseDelivery = deliveryR.setDelivery(guide!!, recibe, parentOrFailDelivery)
        val messageDelivery = responseDelivery.messages!![0].code
        val recordIdDelivery= responseDelivery.response?.recordId
        val setPhoto = deliveryR.setDeliveryPhoto(recordIdDelivery!!,url)
        val messageResponsePhoto = setPhoto.messages
        Log.i("COde set photo",messageResponsePhoto.toString())

        val responseUpdateStaus = deliveryR.setUpdateStatus(status, recordId!!)
        val messageUpdateStatus = responseUpdateStaus.messages!![0].code
        val responseCreateStatus = deliveryR.setCreateStatus(guide, status!!)
        val messageCreateStatus = responseCreateStatus.messages!![0].code
        val responseTryDelivery = deliveryR.setTryDelivery(guide, status, "Exitoso")
        val messageTryDelivery = responseTryDelivery.messages!![0].code

        // return false
        return messageDelivery.equals("0") and messageUpdateStatus.equals("0") and messageCreateStatus.equals(
            "0"
        ) and messageTryDelivery.equals("0")
    }
}