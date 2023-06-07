package com.example.pliin.apppliin.ui.manifest.createmanifest

import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.usecase.GetConsecManUseCase
import com.example.pliin.apppliin.generals.GeneralMethodsGuide
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CMViewModel @Inject constructor(
    private val generalMethodsGuide: GeneralMethodsGuide,
    private val getConsecManUseCase: GetConsecManUseCase

) : ViewModel() {

    private val _progressCircularLoad = MutableLiveData<Float>()
    var progressCircularLoad: LiveData<Float> = _progressCircularLoad

    private val _isSesionDialog = MutableLiveData<Boolean>()
    var isSesionDialog: LiveData<Boolean> = _isSesionDialog

    private val _isAlertDialogexit = MutableLiveData<Boolean>()
    var isAlertDialogexit: LiveData<Boolean> = _isAlertDialogexit

    private val _isAlertDialogConfirmation = MutableLiveData<Boolean>()
    var isAlertDialogConfirmation: LiveData<Boolean> = _isAlertDialogConfirmation

    private val _isDeliveryConfirmation = MutableLiveData<Boolean>()
    var isDeliveryConfirmation: LiveData<Boolean> = _isDeliveryConfirmation

    private val _isDialogRuta = MutableLiveData<Boolean>()
    var isDialogRuta: LiveData<Boolean> = _isDialogRuta

    private val _selectedOption = MutableLiveData<String>()
    var selectedOption: LiveData<String> = _selectedOption

    private val _isLoadBtnEnable = MutableLiveData<Boolean>()
    val isisLoadBtnEnable: LiveData<Boolean> = _isLoadBtnEnable

    private val _isDialogLoadEnable = MutableLiveData<Boolean>()
    val isDialogLoadEnable: LiveData<Boolean> = _isDialogLoadEnable

    private val _messageGuideValidate = MutableLiveData<String>()
    val messageGuideValidate: LiveData<String> = _messageGuideValidate

    private val _parentOrFailDelivery = MutableLiveData<String>()
    var parentOrFailDelivery: LiveData<String> = _parentOrFailDelivery

    private val _ruta = MutableLiveData<String>()
    var ruta: LiveData<String> = _ruta

    private val _clavePreManifest = MutableLiveData<String>()
    var clavePreManifest: LiveData<String> = _clavePreManifest

    private val _titleAlertDialog = MutableLiveData<String>()
    var titleAlertDialog: LiveData<String> = _titleAlertDialog

    private val _textAlertDialog = MutableLiveData<String>()
    var textAlertDialog: LiveData<String> = _textAlertDialog

    private val _status = MutableLiveData<String>()
    var status: LiveData<String> = _status

    private val _conteQR = MutableLiveData<String>()
    val contentQR: LiveData<String> = _conteQR

    private val _mapListGuide = MutableLiveData<Map<String, String>>()
    var mapListGuide: LiveData<Map<String, String>> = _mapListGuide

    private val _listStatusIntentos = MutableLiveData<List<String>>()
    var listStatusIntentos: LiveData<List<String>> = _listStatusIntentos

    private val _statusIntentos = MutableLiveData<String>()
    var statusIntentos: LiveData<String> = _statusIntentos

    private val _countGuides = MutableLiveData<Int>()
    val countGuides: LiveData<Int> = _countGuides

    private val _isLoadingDataGuide = MutableLiveData<Boolean>()
    var isLoadingDataGuide: LiveData<Boolean> = _isLoadingDataGuide

    private val _countRegisterGuide = MutableLiveData<Int>()
    var countRegisterGuide: LiveData<Int> = _countRegisterGuide

    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 1
        minimumFractionDigits = 1
    }

    val keyGuide: Int = 1

    fun enableLoadBtn(CountGuide: Int) = CountGuide >= 1

    fun onValueChanged(selected: String) {
        _selectedOption.value = selected
    }

    fun backScreen(navigationController: NavHostController) {
        navigationController.popBackStack()
    }

    fun reset() {
        _selectedOption.value = ""
        _ruta.value = ""
        _isDialogRuta.value = false
    }

    fun onAlertDialog() {
        _isSesionDialog.value = false
        _isDialogLoadEnable.value = false
    }

    fun onDialogLoadGuides() {
        _isDialogLoadEnable.value = true
        _messageGuideValidate.value = "Desea registrar las guias al sistema?"
    }

    fun continueSetGuides() {
        _isDialogRuta.value = false
        _ruta.value = selectedOption.value
        clavePreGenerate()
    }

    fun getContentQR(guia: String, navigationController: NavHostController) {
        val validate = generalMethodsGuide.validateFormatGuia(guia)
        if (validate) {
            val currentmap = _mapListGuide.value?.toMutableMap() ?: mutableMapOf()
            val guiarepeted = currentmap.get(guia)
            if (guiarepeted.isNullOrEmpty()) {
                var key = currentmap.size + keyGuide
                currentmap[guia] = guia
                _mapListGuide.value = currentmap
                _conteQR.value = guia
                _countGuides.value = _mapListGuide.value?.size
                //setData(guia)
                _isLoadBtnEnable.value = enableLoadBtn(currentmap.size)
            } else {
                _isSesionDialog.value = true
                Log.i("Ya ha agregado esta guia", guia)
                _messageGuideValidate.value = "Ya ha agregado esta guia: $guia"
            }

            Log.i("Formato de Guia Valido", guia)
            // _messageGuideValidate.value ="Formato de guia $guia Valido"
            //  _isSesionDialog.value = true
        } else {
            _isSesionDialog.value = true
            Log.i("Formato de Guia Invalido", guia)
            _messageGuideValidate.value = "El formato de la guia $guia no es valido"
        }
    }

    fun onRemoveguideList(guia: String, second: String) {
        val currentmap = _mapListGuide.value?.toMutableMap() ?: mutableMapOf()
        currentmap.remove(guia)
        _mapListGuide.value = currentmap
        _countGuides.value = _mapListGuide.value?.size
        _isLoadBtnEnable.value = enableLoadBtn(currentmap.size)
    }

    fun clavePreGenerate() {
        _clavePreManifest.value = "LCA" + getdatenow() + "UPS"
        val clave = clavePreManifest.value
        Log.i("", "$clave")
    }

    fun obtenerHoraActual(): String {
        return LocalTime.of(LocalTime.now().hour, LocalTime.now().minute, LocalTime.now().second)
            .toString()
    }

    fun getdatenow(): String {
        val year: String = LocalDate.now().year.toString()
        val month = addZeroDate(LocalDate.now().monthValue)
        val day = addZeroDate(LocalDate.now().dayOfMonth)
        return "$year$month$day"
    }

    fun create() {
        getConsecutivoManifest()
    }

    fun getConsecutivoManifest() {
        val year: String = LocalDate.now().year.toString()
        val month = addZeroDate(LocalDate.now().monthValue)
        val day = addZeroDate(LocalDate.now().dayOfMonth)
        val dateDTO = "<=/05/$year"
        // val dateDTO= "<=06/06/2023"
        Log.i("Fecha now format DTO", "$dateDTO")
        viewModelScope.launch {
            val consecutivo = getConsecManUseCase.invoke(dateDTO)
            val clave = clavePreManifest.value
            _clavePreManifest.value = "$clave$consecutivo"

            Log.i("Clave Completa PreM", "${clavePreManifest.value}")
        }

    }

    fun createManifest() {

    }

    fun LoadGuideServer() {
        var progres = 0
        _progressCircularLoad.value = 0f
        _isLoadingDataGuide.value = true
        val currentmap = _mapListGuide.value?.toMutableMap() ?: mutableMapOf()
        val totalguides = countGuides.value
        val loading: Float = 100 / totalguides!!.toFloat()
        Log.i("Load", "$loading")
        Log.i("Load", "$totalguides")

        currentmap.let {

            viewModelScope.launch(Dispatchers.IO) {

                for ((key, value) in currentmap) {
                    Log.i(key, value)
                    var progress = progressCircularLoad.value
                    Log.i("Agregado", "$progress")
                    val deferred = async { }


                    Log.i("Agregado", "listo")
                    deferred.await()
                    Thread.sleep(1000)
                    launch(Dispatchers.Main) {
                        progres += 1
                        _countRegisterGuide.value = progres
                        _progressCircularLoad.value = _progressCircularLoad.value?.plus(loading)
                        // _progressCircularLoad.value= loadingporcentguide.value?.div(100)
                        progress = _progressCircularLoad.value
                        updateValue(progressCircularLoad.value!!)
                    }
                }
            }
        }
    }

    fun updateValue(value: Float) {
        val formatted = numberFormat.format(value)
        _progressCircularLoad.value = formatted.toFloat()
    }


    private fun addZeroDate(dayOrMonth: Int): String {
        return if (dayOrMonth < 10) {
            "0$dayOrMonth"
        } else {
            dayOrMonth.toString()
        }
    }

    fun initScanner(scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>) {

        val scanOptions = ScanOptions()
        scanOptions.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        scanOptions.setOrientationLocked(true)
        scanOptions.setPrompt("Scanner QR/Barcode")
        //   scanOptions.setBarcodeImageEnabled(true)
        scanOptions.setBeepEnabled(false)
        scanLauncher.launch(scanOptions)
    }

}