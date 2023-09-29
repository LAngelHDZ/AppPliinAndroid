package com.example.pliin.apppliin.ui.planeation.planetionday

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.model.GuideItem
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.FieldData
import com.example.pliin.apppliin.domain.usecase.dblocal.GetGuidesManifestDBUseCase
import com.example.pliin.apppliin.domain.usecase.manifest.GetAllManifestUseCase
import com.example.pliin.apppliin.domain.usecase.manifest.GetGuidesManifestUseCase
import com.example.pliin.apppliin.domain.usecase.dblocal.GetManifestDBUseCase
import com.example.pliin.apppliin.domain.usecase.dblocal.SaveGuidesManDBUseCase
import com.example.pliin.apppliin.domain.usecase.dblocal.SaveManifestDBUseCase
import com.example.pliin.apppliin.domain.usecase.dblocal.UpdateStatusManifestDBUseCase
import com.example.pliin.apppliin.domain.usecase.manifest.GetOneManifestUseCase
import com.example.pliin.apppliin.domain.usecase.manifest.UpdateManifestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PDViewModel @Inject constructor(
    private val getManifestUseCase: GetAllManifestUseCase,
    private val getManifestDBUseCase: GetManifestDBUseCase,
    private val getGuidesManifestUseCase: GetGuidesManifestUseCase,
    private val saveGuidesManDBUseCase: SaveGuidesManDBUseCase,
    private val saveManifestDBUseCase: SaveManifestDBUseCase,
    private val getGuidesManifestDBUseCase: GetGuidesManifestDBUseCase,
    private val updateManifestUseCase: UpdateManifestUseCase,
    private val getOneManifestUseCase: GetOneManifestUseCase,
    private val updateStatusManifestDBUseCase: UpdateStatusManifestDBUseCase

):ViewModel(){
   private val _isLoadingPlaneation = MutableLiveData<String>()
    val isLoadingPlaneation:LiveData<String> = _isLoadingPlaneation

    private val _dataManifest = MutableLiveData<FieldData>()
    val dataManifest:LiveData<FieldData> = _dataManifest

    private val _folioManifest = MutableLiveData<String>()
    val folioManifest:LiveData<String> = _folioManifest

    private val _listGuide = MutableLiveData<List<GuideItem>>()
    val listGuide:LiveData<List<GuideItem>> = _listGuide

    private val _rutaManifest = MutableLiveData<String>()
    val rutaManifest:LiveData<String> = _rutaManifest

    private val _totalGuides = MutableLiveData<String>()
    val totalGuides:LiveData<String> = _totalGuides

    private val _idRecord = MutableLiveData<String>()
    val idRecord:LiveData<String> = _idRecord

    private val _statusManifest = MutableLiveData<String>()
    val statusManifest:LiveData<String> = _statusManifest

    private val _manifestAplicado = MutableLiveData<Boolean>()
    val manifestAplicado:LiveData<Boolean> = _manifestAplicado

    private val _messageDialog = MutableLiveData<String>()
    val messageDialog:LiveData<String> = _messageDialog

    private val _isDialog = MutableLiveData<Boolean>()
    val isDialog:LiveData<Boolean> = _isDialog

    private val _isCorrectUpdateStatus = MutableLiveData<Boolean>()
    val isCorrectUpdateStatus:LiveData<Boolean> = _isCorrectUpdateStatus




    private var btncountAplication:Int = 0

//    fun LoadManifestPlaneation(){
//        viewModelScope.launch{
//           val date = getdatenow()
//            val responsedb=getManifestDBUseCase.invoke()
//             if (responsedb?.clavePrincipal.isNullOrEmpty()){
//                 val response = getManifestUseCase.invoke(date)
//                 if (response.isNullOrEmpty()){
//                     _isLoadingPlaneation.value ="NoFound"
//                 }else{
//                     _dataManifest.value = response[0]?.fieldData
//                     _folioManifest.value = response[0]?.fieldData?.clavePrincipal
//                     _rutaManifest.value = response[0]?.fieldData?.ruta
//                     _totalGuides.value = response[0]?.fieldData?.totaolGuias?.toString()
//                     _statusManifest.value = response[0]?.fieldData?.statusPreM
//                     _isLoadingPlaneation.value ="Founded"
//                 }
//             }else{
//                 val list = getGuidesManifestDBUseCase.invoke(responsedb?.clavePrincipal!!)
//                 _listGuide.value = list
//                 _folioManifest.value = responsedb?.clavePrincipal
//                 _rutaManifest.value = responsedb?.ruta
//                 _totalGuides.value = responsedb?.totaolGuias.toString()
//                 _statusManifest.value = responsedb?.statusPreM
//                 _isLoadingPlaneation.value ="Founded"
//             }
//        }
//    }

    fun setDataManifest(
        folioManifest: String,
        ruta: String,
        totalGuides: String
    ) {
        viewModelScope.launch{
            _folioManifest.value = folioManifest
            _rutaManifest.value = ruta
            _totalGuides.value = totalGuides
//            _idRecord.value =idrecord
            val responseSGuides = getGuidesManifestDBUseCase.invoke(folioManifest)

            Log.i("Guias","$responseSGuides")

            if (responseSGuides.isNullOrEmpty()){
                _isLoadingPlaneation.value ="NoFound"
            }else{
                _listGuide.value = responseSGuides
                _isLoadingPlaneation.value ="Founded"
            }
        }
    }

    fun downloadManifest(){
        viewModelScope.launch{
            if (btncountAplication <= 0){
                val responseSManifest = saveManifestDBUseCase.invoke(dataManifest.value!!)
                val responseSGuides = getGuidesManifestUseCase.invoke(folioManifest.value!!)
                val ok = saveGuidesManDBUseCase.invoke(responseSGuides)
                val list = getGuidesManifestDBUseCase.invoke(folioManifest.value!!)
                _listGuide.value = list
                btncountAplication.plus(1)
            }
        }
    }

    fun finalizarManifest(){
        viewModelScope.launch {
            val dataManifest = getOneManifestUseCase.invoke(folioManifest.value!!)
            val idRecord = dataManifest?.get(0)?.recordId
            val nameEmployee = dataManifest?.get(1)?.fieldData?.nombreOperador
            val totalPqt = dataManifest?.get(1)?.fieldData?.totalpqt.toString()
            val dataDto:List<String?> = listOf(
                nameEmployee,
                totalPqt,
                totalPqt,
                idRecord,
                "FINALIZADO"
            )
            val responseUpdate = updateManifestUseCase.invoke(dataDto)
            if (responseUpdate){
               dialogmessage("Manifiesto finalizado")
                updateStatusManifestDBUseCase.invoke(folioManifest.value!!,"FINALIZADO")
                _isCorrectUpdateStatus.value=true
            }else{
                dialogmessage("Hubo un error intente mas tarde")
            }
        }
    }

    fun closeViewPlaneation(navigationController: NavHostController) {
     navigation(navigationController)
    }

    fun closeDialog(){
        _isDialog.value = false
    }

    fun dialogmessage(message:String){
        _isDialog.value = true
        _messageDialog.value =message
    }

    fun getdatenow():String{
        val year: String = LocalDate.now().year.toString()
        val month = addZeroDate(LocalDate.now().monthValue)
        val day = addZeroDate(LocalDate.now().dayOfMonth)
        return "$month/$day/$year"
    }

    private fun addZeroDate(dayOrMonth: Int): String{
        return if (dayOrMonth < 10){
            "0$dayOrMonth"
        }else{
            dayOrMonth.toString()
        }
    }
    fun reset(){
        _isLoadingPlaneation.value="Loading"
        _isCorrectUpdateStatus.value= false
        _messageDialog.value =""
        _idRecord.value =""
        _listGuide.value = emptyList()
        _dataManifest.value = null
        _manifestAplicado.value = false
        _folioManifest.value=""
        _rutaManifest.value=""
        _totalGuides.value=""
    }

    fun navigation(navigationController: NavHostController){
        navigationController.popBackStack()
        reset()
    }
}