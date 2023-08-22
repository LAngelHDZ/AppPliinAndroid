package com.example.pliin.apppliin.ui.planeationday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pliin.apppliin.domain.usecase.GetAllManifestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PDViewModel @Inject constructor(
    private val getManifestUseCase: GetAllManifestUseCase

):ViewModel(){

   private val _isLoadingPlaneation = MutableLiveData<Boolean>()
    val isLoadingPlaneation:LiveData<Boolean> = _isLoadingPlaneation

    fun LoadManifestPlaneation(){
        viewModelScope.launch {
           val date = getdatenow()
            val response = getManifestUseCase.invoke(date)

             if (response.isNullOrEmpty()){

             }
        }

    }


    fun getdatenow():String{
        val year: String = LocalDate.now().year.toString()
        val month = addZeroDate(LocalDate.now().monthValue)
        val day = addZeroDate(LocalDate.now().dayOfMonth)
        return "$month/$day/$year"
    }

    private fun addZeroDate(dayOrMonth: Int): String {
        return if (dayOrMonth < 10) {
            "0$dayOrMonth"
        } else {
            dayOrMonth.toString()
        }
    }

}