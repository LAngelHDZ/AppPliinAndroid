package com.example.pliin.apppliin.domain.usecase.delivery

import android.annotation.SuppressLint
import android.util.Log
import com.example.pliin.apppliin.data.repositories.DeliveryRepository
import com.example.pliin.apppliin.data.repositories.UsersRepository
import javax.inject.Inject

class RechazadoUseCase @Inject constructor(
    private val deliveryR: DeliveryRepository,
    private val usersRepository: UsersRepository
) {


    @SuppressLint("SuspiciousIndentation")
    suspend operator fun invoke(
        guide: String?,
        recordId: String?,
        status: String?,
        recibe: String?,
        parentOrFailDelivery: String?
    ): Boolean {
        val userData = usersRepository.getAllUserDatabase()
        val user = userData.user
        var messageDelivery = ""
        Log.i("user", user.toString())
        Log.i("seleted", parentOrFailDelivery!!)
        Log.i("status intento entrega eb use case", parentOrFailDelivery!!)
        //  Log.i("seleted", selectedOption.value!!)
        if (user.equals("opabril")) {

            val responseDelivery =
                deliveryR.setDelivery(guide!!, "No recibido", parentOrFailDelivery, "pago", "pago")
            messageDelivery = responseDelivery.messages!![0]!!.code!!
        } else {
            messageDelivery = "0"
        }

        val responseUpdateStaus = deliveryR.setUpdateStatus(status, recordId!!,"manifiestoPaquetes")
        val messageUpdateStatus = responseUpdateStaus.messages!![0]!!.code
        val responseCreateStatus = deliveryR.setCreateStatus(guide!!, status!!)
        val messageCreateStatus = responseCreateStatus.messages!![0]!!.code
        val responseTryDelivery = deliveryR.setTryDelivery(guide, status, "-")
        val messageTryDelivery = responseTryDelivery.messages!![0]!!.code

        // return false
        return messageDelivery.equals("0") and messageUpdateStatus.equals("0") and messageCreateStatus.equals(
            "0"
        ) and messageTryDelivery.equals("0")
    }
}