package com.example.pliin.apppliin.ui.planeation.viewmanifestplaneation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.Data
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.FieldData
import com.example.pliin.apppliin.domain.usecase.dblocal.GetAllManifestDBUserCase
import com.example.pliin.apppliin.domain.usecase.manifest.GetAllManifestUseCase
import com.example.pliin.apppliin.domain.usecase.user.LoadEmployeeUseCase
import com.example.pliin.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class VMFViewModel @Inject constructor(
    private val getAllManifestUseCase: GetAllManifestUseCase,
    private val loadEmployeeUseCase: LoadEmployeeUseCase,
    private val allManifestDBUseCase: GetAllManifestDBUserCase

) : ViewModel() {
    private val _listManifest = MutableLiveData<List<FieldData?>>()
    var listManifest: LiveData<List<FieldData?>> = _listManifest

    private val _isLoadingPlaneation = MutableLiveData<String>()
    val isLoadingPlaneation:LiveData<String> = _isLoadingPlaneation

    private val _claveManifest = MutableLiveData<String>()
    val claveManifest:LiveData<String> = _claveManifest

    private val _idRecord = MutableLiveData<String>()
    val idRecord:LiveData<String> = _idRecord

    private val _ruta = MutableLiveData<String>()
    val ruta:LiveData<String> = _ruta

    private val _nameEmployee = MutableLiveData<String>()
    val nameEmployee :LiveData<String> = _nameEmployee

    private val _totalguides = MutableLiveData<String>()
    val totalguides :LiveData<String> = _totalguides

    private val _enableLoadManifest = MutableLiveData<Boolean>()
    var enableLoadManifest: LiveData<Boolean> = _enableLoadManifest

    private val _optionsDialog = MutableLiveData<Boolean>()
    var optionsDialog: LiveData<Boolean> = _optionsDialog

    fun reset(){
        _listManifest.value= emptyList()
        _enableLoadManifest.value = true
        _isLoadingPlaneation.value="Loading"
    }

    fun loadManifest(){
        val year: String = LocalDate.now().year.toString()
        val month = addZeroDate(LocalDate.now().monthValue)
        val day = addZeroDate(LocalDate.now().dayOfMonth)
        val dateDTO = "<=$month/$day/$year"
        viewModelScope.launch{
            val manifestDB = allManifestDBUseCase.invoke()

            if(manifestDB.isNullOrEmpty()){
                _isLoadingPlaneation.value="NoFound"
            }else{
                _isLoadingPlaneation.value="Founded"
                _listManifest.value = manifestDB
            }
        }
    }

    fun onOptionDialog(){
        _optionsDialog.value=false
    }

    fun clickManifest(clavemanifest: String, nameEmployee: String, ruta: String,totalguides:String){
        viewModelScope.launch{
            val employe = loadEmployeeUseCase()
            _nameEmployee.value=nameEmployee
            _ruta.value=ruta
//                _idRecord.value=idRecord
            _claveManifest.value = clavemanifest
            _totalguides.value= totalguides
            _optionsDialog.value = true
        }
    }

    fun viewManifest(navigationController: NavHostController){
        _optionsDialog.value=false
        navigationController.navigate(AppScreen.PlaneationDayScreen.createRoute(claveManifest.value!!,ruta.value!!,totalguides.value!!))
        _enableLoadManifest.value = true
    }

    fun navigate(navigationController: NavHostController){
        navigationController.popBackStack()
        reset()
    }

    fun getdatenow():String{
        val year: String = LocalDate.now().year.toString()
        val month = addZeroDate(LocalDate.now().monthValue)
        val day = addZeroDate(LocalDate.now().dayOfMonth)
        return "$year$month$day"
    }

    private fun addZeroDate(dayOrMonth: Int): String {
        return if (dayOrMonth < 10) {
            "0$dayOrMonth"
        } else {
            dayOrMonth.toString()
        }
    }

}