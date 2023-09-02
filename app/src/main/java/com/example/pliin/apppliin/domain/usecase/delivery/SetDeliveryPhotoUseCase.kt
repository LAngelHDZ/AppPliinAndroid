package com.example.pliin.apppliin.domain.usecase.delivery

import com.example.pliin.apppliin.data.repositories.DeliveryRepository
import com.example.pliin.apppliin.domain.usecase.login.ReloginUseCase
import javax.inject.Inject

class SetDeliveryPhotoUseCase @Inject constructor(
    private val reloginUseCase: ReloginUseCase,
    private val deliveryRepository: DeliveryRepository

) {

    suspend operator fun invoke(recordId:String,url:String){

        val response = deliveryRepository.setDeliveryPhoto(recordId, url)
        
    }
}