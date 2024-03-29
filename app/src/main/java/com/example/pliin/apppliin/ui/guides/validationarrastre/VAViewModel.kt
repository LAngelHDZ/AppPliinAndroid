package com.example.pliin.apppliin.ui.guides.validationarrastre

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.*
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.usecase.QueryGuideValidateUseCase
import com.example.pliin.apppliin.domain.usecase.RegisterGuideUseCase
import com.example.pliin.apppliin.generals.GeneralMethodsGuide
import com.example.pliin.navigation.AppScreen
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class VAViewModel @Inject constructor(
    private val registerGuideUseCase: RegisterGuideUseCase,
    private val generalMethodsGuide: GeneralMethodsGuide,
    private val queryGuideValidateUseCase: QueryGuideValidateUseCase
) :
    ViewModel() {
    private val _isSesionDialog = MutableLiveData<Boolean>()
    var isSesionDialog: LiveData<Boolean> = _isSesionDialog

    private val _progressCircularLoad = MutableLiveData<Float>()
    var progressCircularLoad: LiveData<Float> = _progressCircularLoad

    private val _countRegisterGuide = MutableLiveData<Int>()
    var countRegisterGuide: LiveData<Int> = _countRegisterGuide

    private val _mapListGuide = MutableLiveData<Map<String, String>>()
    var mapListGuide: LiveData<Map<String, String>> = _mapListGuide

    private val _countGuides = MutableLiveData<Int>()
    val countGuides: LiveData<Int> = _countGuides

    private val _isLoadingDataGuide = MutableLiveData<Boolean>()
    var isLoadingDataGuide: LiveData<Boolean> = _isLoadingDataGuide

    private val _isGuideRegisted = MutableLiveData<Boolean>()
    var isGuideRegisted: LiveData<Boolean> = _isGuideRegisted

    private val _conteQR = MutableLiveData<String>()
    val contentQR: LiveData<String> = _conteQR

    private val _recordId = MutableLiveData<String>()
    val recordId: LiveData<String> = _recordId

    private val _formattedValue = MutableLiveData<String>()
    val formattedValue: LiveData<String> get() = _formattedValue

    private val _messageGuideValidate = MutableLiveData<String>()
    val messageGuideValidate: LiveData<String> = _messageGuideValidate

    private val _guia = MutableLiveData<String>()
    val guia: LiveData<String> = _guia

    private val _isSearchEnable = MutableLiveData<Boolean>()
    val isSearchEnable: LiveData<Boolean> = _isSearchEnable


    var guideNoEmpty: Boolean = false

    private val _isDialogLoadEnable = MutableLiveData<Boolean>()
    val isDialogLoadEnable: LiveData<Boolean> = _isDialogLoadEnable

    private val _isLoadBtnEnable = MutableLiveData<Boolean>()
    val isisLoadBtnEnable: LiveData<Boolean> = _isLoadBtnEnable
    val keyGuide: Int = 1

    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 1
        minimumFractionDigits = 1
    }

    fun onSearchChanged(guia: String) {
        _guia.value = guia
        _isSearchEnable.value = enableSearch(guia)
    }

    fun enableSearch(guia: String) = guia.length > 10

    fun enableLoadBtn(CountGuide: Int) = CountGuide >= 1

    fun onAlertDialog() {
        _isSesionDialog.value = false
        _isDialogLoadEnable.value = false
    }

    fun onDialogLoadGuides() {
        _isDialogLoadEnable.value = true
        _messageGuideValidate.value = "Desea registrar las guias al sistema?"
    }

    fun LoadGuideServer(guia: String, navigationController: NavHostController) {
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
                    val deferred = async { registerGuideUseCase.invoke(value, "pago") }

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

    fun loadingOk(progres: Int, totalguides: Int) {
        Log.i("Estoy en el metodo que verifica las guias registradas", "$progres de $totalguides")
        if (progres >= totalguides) {
            _isGuideRegisted.value = true
        }
    }

    fun updateValue(value: Float) {
        val formatted = numberFormat.format(value)
        _progressCircularLoad.value = formatted.toFloat()
    }

    fun setData(guia: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = queryGuideValidateUseCase.invoke(guia)
            Log.i("recordID", "${response.component2()}")
            launch(Dispatchers.Main){
                if (response[0].equals("")){
                    Log.i("guia no existe", "is false")
                    guideNoEmpty = false
                } else{
                    _recordId.value = response.component2()
                    guideNoEmpty = true
                    Log.i("guia existe", "is true")
                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun getContentQR(guia: String, navigationController: NavHostController){
        val validate = generalMethodsGuide.validateFormatGuia(guia)

        if (validate) {
            viewModelScope.launch() {
                val response = queryGuideValidateUseCase.invoke(guia)
                Log.i("recordID", "${response.component2()}")

                var guiaEMpty = if (response[0].equals("")) {
                    Log.i("guia no existe", "is false")
                    false
                } else {
                    _recordId.value = response.component2()
                    Log.i("guia existe", "is true")
                    true
                }
                val currentmap = _mapListGuide.value?.toMutableMap() ?: mutableMapOf()
                val recordID = _recordId.value
                Log.i("Record Id", "$recordID")
                //Log.i("Variable guideNoEmpty", "$guiaEMpty")

                if (guiaEMpty) {
                    val guiarepeted = currentmap.get(guia)
                    if (guiarepeted.isNullOrEmpty()) {
                        var key = currentmap.size + keyGuide
                        currentmap[guia] = guia
                        _mapListGuide.value = currentmap
                        _conteQR.value = guia
                        _countGuides.value = _mapListGuide.value?.size
                        _isLoadBtnEnable.value = enableLoadBtn(currentmap.size)
                    } else {
                        _isSesionDialog.value = true
                        Log.i("Ya ha agregado esta guia", guia)
                        _messageGuideValidate.value = "Ya ha agregado esta guia: $guia"
                    }
                } else {
                    _isSesionDialog.value = true
                    _messageGuideValidate.value = "El numero de $guia no se encuentra en Arrastre"
                }
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

    fun guideregistedOk() {
        _isDialogLoadEnable.value = false
        _isLoadingDataGuide.value = false
        _isGuideRegisted.value = false
        _countGuides.value = 0
        _countRegisterGuide.value = 0
        _mapListGuide.value = emptyMap()
        _conteQR.value = ""

    }

    fun initScanner(scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>) {

        val scanOptions = ScanOptions()
        scanOptions.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        scanOptions.setOrientationLocked(true)
        scanOptions.setPrompt("Scanner QR/Barcode")
        scanOptions.setTorchEnabled(true)
        scanOptions.setBeepEnabled(true)
        scanLauncher.launch(scanOptions)
    }

    fun navigation(navigationController: NavHostController) {
        val previousScreenName = navigationController.previousBackStackEntry?.destination?.route
        if (previousScreenName.equals("MenuGuideScreen")) {
            navigationController.popBackStack()
        } else {
            navigationController.navigate(AppScreen.MenuGuideScreen.route)
        }
    }
}