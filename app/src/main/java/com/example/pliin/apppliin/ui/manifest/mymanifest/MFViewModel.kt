package com.example.pliin.apppliin.ui.manifest.mymanifest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.Data
import com.example.pliin.apppliin.domain.usecase.manifest.GetAllManifestUseCase
import com.example.pliin.apppliin.domain.usecase.user.LoadEmployeeUseCase
import com.example.pliin.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MFViewModel @Inject constructor(
    private val getAllManifestUseCase: GetAllManifestUseCase,
    private val loadEmployeeUseCase: LoadEmployeeUseCase
) : ViewModel() {
    private val _listManifest = MutableLiveData<List<Data>>()
    var listManifest: LiveData<List<Data>> = _listManifest

    private val _claveManifest = MutableLiveData<String>()
    val claveManifest:LiveData<String> = _claveManifest

    private val _idRecord = MutableLiveData<String>()
    val idRecord:LiveData<String> = _idRecord

    private val _ruta = MutableLiveData<String>()
    val ruta:LiveData<String> = _ruta

    private val _nameEmployee = MutableLiveData<String>()
    val nameEmployee :LiveData<String> = _nameEmployee

    private val _enableLoadManifest = MutableLiveData<Boolean>()
    var enableLoadManifest: LiveData<Boolean> = _enableLoadManifest

    private val _optionsDialog = MutableLiveData<Boolean>()
    var optionsDialog: LiveData<Boolean> = _optionsDialog

    fun reset(){
        _listManifest.value= emptyList()
        _enableLoadManifest.value = true
    }

    fun loadManifest(){
        val year: String = LocalDate.now().year.toString()
        val month = addZeroDate(LocalDate.now().monthValue)
        val day = addZeroDate(LocalDate.now().dayOfMonth)
        val dateDTO = "<=$month/$day/$year"
        viewModelScope.launch{
            val result = getAllManifestUseCase.invoke(dateDTO,)
            _listManifest.value = result as List<Data>?
        }
        _enableLoadManifest.value = false
    }

    fun onOptionDialog(){
        _optionsDialog.value=false
    }

    fun clickManifest(claveManifest: String, idRecord: String, nameEmployee: String, ruta: String){
        var employee= if (nameEmployee.isNullOrEmpty()){
            "-"
        }else{
            nameEmployee
        }
        viewModelScope.launch{
            val employe = loadEmployeeUseCase()
            if(employe.area.equals("Operador Logistico")){
                Log.i("Soy operador", "dirigeme a la vista")
                _nameEmployee.value=employee
                _ruta.value=ruta
                _idRecord.value=idRecord
                _claveManifest.value = claveManifest
            }else{
                _nameEmployee.value=employee
                _ruta.value=ruta
                _idRecord.value=idRecord
                _claveManifest.value = claveManifest
            }
            _optionsDialog.value = true
        }
    }

    fun viewEditManifest(navigationController: NavHostController){
        _optionsDialog.value=false
        navigationController.navigate(AppScreen.EditManifestScreen.createRoute(nameEmployee.value!!,idRecord.value!!,ruta.value!!,claveManifest.value!!))
        _enableLoadManifest.value = true
    }

    fun viewManifest(navigationController: NavHostController){
        _optionsDialog.value=false
        navigationController.navigate(AppScreen.ViewManifestScreen.createRoute(nameEmployee.value!!,idRecord.value!!,ruta.value!!,claveManifest.value!!))
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