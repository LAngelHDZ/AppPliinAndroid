package com.example.pliin.apppliin.ui.planeationday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.Data
import com.example.pliin.apppliin.domain.usecase.GetAllManifestUseCase
import com.example.pliin.apppliin.domain.usecase.GetGuidesManifestUseCase
import com.example.pliin.apppliin.domain.usecase.GetManifestDBUseCase
import com.example.pliin.apppliin.domain.usecase.RegisterGuidesManDBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PDViewModel @Inject constructor(
    private val getManifestUseCase: GetAllManifestUseCase,
    private val getManifestDBUseCase: GetManifestDBUseCase,
    private val getGuidesManifestUseCase: GetGuidesManifestUseCase,
    private val registerGuidesManDBUseCase: RegisterGuidesManDBUseCase

):ViewModel(){

   private val _isLoadingPlaneation = MutableLiveData<String>()
    val isLoadingPlaneation:LiveData<String> = _isLoadingPlaneation

    private val _dataManifest = MutableLiveData< List<Data?>?>()
    val dataManifest:LiveData< List<Data?>?> = _dataManifest

    private val _folioManifest = MutableLiveData<String>()
    val folioManifest:LiveData<String> = _folioManifest

    private val _rutaManifest = MutableLiveData<String>()
    val rutaManifest:LiveData<String> = _rutaManifest

    private val _totalGuides = MutableLiveData<String>()
    val totalGuides:LiveData<String> = _totalGuides

    private val _statusManifest = MutableLiveData<String>()
    val statusManifest:LiveData<String> = _statusManifest



    fun LoadManifestPlaneation(){
        viewModelScope.launch{
           val date = getdatenow()
            val responsedb=getManifestDBUseCase.invoke()
             if (responsedb?.clavePrincipal.isNullOrEmpty()){
                 val response = getManifestUseCase.invoke(date)
                 if (response.isNullOrEmpty()){
                     _isLoadingPlaneation.value ="NoFound"
                 }else{
                     _dataManifest.value = response
                     _folioManifest.value = response[0]?.fieldData?.clavePrincipal
                     _rutaManifest.value = response[0]?.fieldData?.ruta
                     _totalGuides.value = response[0]?.fieldData?.totaolGuias?.toString()
                     _statusManifest.value = response[0]?.fieldData?.statusPreM
                     _isLoadingPlaneation.value ="Founded"
                 }
             }else{

                 _folioManifest.value = responsedb?.clavePrincipal
                 _rutaManifest.value = responsedb?.ruta
                 _totalGuides.value = responsedb?.toString()
                 _statusManifest.value = responsedb?.statusPreM
                 _isLoadingPlaneation.value ="Founded"
             }
        }
    }

    fun downloadManifest(){
        viewModelScope.launch {
            

            val response = getGuidesManifestUseCase.invoke(folioManifest.value!!)
            val ok = registerGuidesManDBUseCase.invoke(response)

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
    fun reset(){
        _isLoadingPlaneation.value="Loading"
    }

    fun navigation(navigationController: NavHostController) {
        navigationController.popBackStack()
        reset()
    }

}