package com.example.pliin.apppliin.domain.usecase.guides

import com.example.pliin.apppliin.data.repositories.GuideRepository
import javax.inject.Inject

class ValidateGuideSystemUseCase @Inject constructor(
    private val repository: GuideRepository
) {

    suspend operator fun invoke(guide: String): List<String> {
        var code: String
        var recordId = ""
        do {
            val response = repository.validateGuideApi(guide)
            val messageGuideValidate = response.messages!![0]!!.code
            if (messageGuideValidate.equals("0")) {
                recordId = response.response?.data!![0]?.recordId!!
            }
            code = messageGuideValidate!!
        } while (messageGuideValidate.equals("500"))
        return listOf(code, recordId)
    }
}