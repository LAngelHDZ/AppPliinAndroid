package com.example.pliin.apppliin.domain.usecase.delivery

import com.example.pliin.apppliin.data.repositories.DeliveryRepository
import javax.inject.Inject

class CreateStatusUSeCase @Inject constructor(private val deliveryR: DeliveryRepository) {
    //MEtodo para crear un status de seguimiento de guia
    suspend operator fun invoke(guide: String, status: String): String {
        var code: String
        do {
            val responseCreateStatus = deliveryR.setCreateStatus(guide, status)
            val messageCreateStatus = responseCreateStatus.messages!![0]!!.code
            code = messageCreateStatus!!
        } while (!messageCreateStatus.equals("0"))
        return code
    }
}