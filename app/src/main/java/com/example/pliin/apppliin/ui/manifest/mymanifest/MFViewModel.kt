package com.example.pliin.apppliin.ui.manifest.mymanifest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.Data
import com.example.pliin.apppliin.domain.usecase.GetManifestUseCase
import com.example.pliin.apppliin.domain.usecase.LoadEmployeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MFViewModel @Inject constructor(
    private val getManifestUseCase: GetManifestUseCase,
    private val loadEmployeeUseCase: LoadEmployeeUseCase
) : ViewModel() {
    private val _listManifest = MutableLiveData<List<Data>>()
    var listManifest: LiveData<List<Data>> = _listManifest

    private val _claveManifest = MutableLiveData<String>()
    val claveManifest:LiveData<String> = _claveManifest

    private val _enableLoadManifest = MutableLiveData<Boolean>()
    var enableLoadManifest: LiveData<Boolean> = _enableLoadManifest

    private val _optionsDialog = MutableLiveData<Boolean>()
    var optionsDialog: LiveData<Boolean> = _optionsDialog

    fun reset(){
        _listManifest.value= emptyList()
        _enableLoadManifest.value = true
    }

    fun loadManifest() {
        val year: String = LocalDate.now().year.toString()
        val month = addZeroDate(LocalDate.now().monthValue)
        val day = addZeroDate(LocalDate.now().dayOfMonth)
        val dateDTO = "<=$month/$day/$year"
        viewModelScope.launch {
            val result = getManifestUseCase(dateDTO)
            _listManifest.value = result as List<Data>?
            _enableLoadManifest.value = false
        }
    }

    fun clickManifest(claveManifest:String){
        viewModelScope.launch {
            val employe = loadEmployeeUseCase()
            if(employe.area.equals("Operador Logistico")){
                Log.i("Soy operador", "dirigeme a la vista")
            }else{
                _claveManifest.value = claveManifest
                _optionsDialog.value = true
            }
        }
    }

    fun navigate(navigationController: NavHostController){
        navigationController.popBackStack()
        reset()
    }

    fun getdatenow():String {
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