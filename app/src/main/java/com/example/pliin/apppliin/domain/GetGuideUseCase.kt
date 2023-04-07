package com.example.pliin.apppliin.domain

import com.example.pliin.apppliin.data.UsersRepository
import com.example.pliin.apppliin.data.database.entities.toDatabase
import com.example.pliin.apppliin.domain.model.GuideItem
import com.example.pliin.apppliin.domain.model.UserItem
import javax.inject.Inject

class GetGuideUseCase @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke():List<GuideItem>?{
        val guides = repository.getAllGuideFromApi()
        if (guides.isNotEmpty()){
            repository.clearGuide()
            repository.insertGuides(guides.map { it.toDatabase() })
        }else{

        }

       return guides

    }

}