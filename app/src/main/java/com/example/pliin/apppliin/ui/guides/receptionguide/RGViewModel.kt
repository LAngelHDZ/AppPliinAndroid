package com.example.pliin.apppliin.ui.guides.receptionguide

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.*
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.usecase.GetGuideCodUseCase
import com.example.pliin.apppliin.domain.usecase.RegisterGuideCodUseCase
import com.example.pliin.apppliin.domain.usecase.RegisterGuideUseCase
import com.example.pliin.apppliin.generals.GeneralMethodsGuide
import com.example.pliin.navigation.AppScreen
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RGViewModel @Inject constructor(
    private val registerGuideUseCase: RegisterGuideUseCase,
    private val generalMethodsGuide: GeneralMethodsGuide,
    private val GetGuideCodUseCase: GetGuideCodUseCase,
    private val registerGuideCodUseCase: RegisterGuideCodUseCase
) :
    ViewModel() {
    private val _isSesionDialog = MutableLiveData<Boolean>()
    var isSesionDialog: LiveData<Boolean> = _isSesionDialog

    private val _isGuideExistCod = MutableLiveData<Boolean>()
    var isGuideExistCod: LiveData<Boolean> = _isGuideExistCod

    private val _isFormCod = MutableLiveData<Boolean>()
    var isFormCod: LiveData<Boolean> = _isFormCod

    private val _progressCircularLoad = MutableLiveData<Float>()
    var progressCircularLoad: LiveData<Float> = _progressCircularLoad

    private val _countRegisterGuide = MutableLiveData<Int>()
    var countRegisterGuide: LiveData<Int> = _countRegisterGuide

    private val _valueCod = MutableLiveData<String>()
    var valueCod: LiveData<String> = _valueCod

    private val _pagoCod = MutableLiveData<String>()
    var pagoCod: LiveData<String> = _pagoCod

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

    private val _formattedValue = MutableLiveData<String>()
    val formattedValue: LiveData<String> get() = _formattedValue

    private val _messageGuideValidate = MutableLiveData<String>()
    val messageGuideValidate: LiveData<String> = _messageGuideValidate

    private val _guia = MutableLiveData<String>()
    val guia: LiveData<String> = _guia

    private val _isSearchEnable = MutableLiveData<Boolean>()
    val isSearchEnable: LiveData<Boolean> = _isSearchEnable

    private val _isDialogLoadEnable = MutableLiveData<Boolean>()
    val isDialogLoadEnable: LiveData<Boolean> = _isDialogLoadEnable

    private val _isLoadBtnEnable = MutableLiveData<Boolean>()
    val isisLoadBtnEnable: LiveData<Boolean> = _isLoadBtnEnable
    val keyGuide: Int = 1

    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 1
        minimumFractionDigits = 1
    }

    fun setCodGuide() {
        _isFormCod.value = false
        _isGuideExistCod.value = false
        viewModelScope.launch {
            val pago = pagoCod.value
            val guia = contentQR.value
            val valueCod = valueCod.value?.toFloat()
            if (pago.equals("SIN CONFIRMAR")){
                setData(guia!!, pago)
                val result = registerGuideCodUseCase.invoke(guia, valueCod!!, pago)
            }else{
                setData(guia!!, pago)
            }
        }
    }

    fun onSearchChanged(guia: String) {
        _guia.value = guia
        _isSearchEnable.value = enableSearch(guia)
    }

    fun onExistsCod(option: String) {
        if (option.equals("Si")) {
            _isFormCod.value = true
            _pagoCod.value = "SIN CONFIRMAR"
        } else{

            _isGuideExistCod.value = false
            _pagoCod.value = "NO APLICA"
            setCodGuide()
        }
    }

    fun onChangeCod(cod: String) {
        _valueCod.value = cod
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
        currentmap.let{
            viewModelScope.launch(Dispatchers.IO){
                for ((key, value) in currentmap){
                    Log.i(key, value)
                    var progress = progressCircularLoad.value
                    Log.i("Agregado", "$progress")
                    val deferred = async { registerGuideUseCase.invoke(value, "pago") }
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

        viewModelScope.launch(Dispatchers.Main) {
            delay(2500)
            Log.i(
                "Estoy en el metodo que verifica las guias registradas",
                "$progres de $totalguides"
            )
            if (progres >= totalguides) {
                _isGuideRegisted.value = true
            }
        }
    }

    fun updateValue(value: Float) {
        val formatted = numberFormat.format(value)
        _progressCircularLoad.value = formatted.toFloat()
    }

    fun setData(guia: String, pago: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val deferred = async { registerGuideUseCase.invoke(guia, pago) }
            Log.i("Agregado", "listo")
            deferred.await()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun getContentQR(guia: String, navigationController: NavHostController) {
        val validate = generalMethodsGuide.validateFormatGuia(guia)
        viewModelScope.launch {
            if (validate) {
                val currentmap = _mapListGuide.value?.toMutableMap() ?: mutableMapOf()
                val guiarepeted = currentmap.get(guia)
                if (guiarepeted.isNullOrEmpty()) {
                    val existsCOd = GetGuideCodUseCase.invoke(guia)
                    if (!existsCOd) {
                        _isGuideExistCod.value = true
                    }

                    var key = currentmap.size + keyGuide
                    currentmap[guia] = guia
                    _mapListGuide.value = currentmap
                    _conteQR.value = guia
                    _countGuides.value = _mapListGuide.value?.size
                    // setData(guia)

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
    }

    fun onRemoveguideList(guia: String, second: String){
        val currentmap = _mapListGuide.value?.toMutableMap() ?: mutableMapOf()
        currentmap.remove(guia)
        _mapListGuide.value = currentmap
        _countGuides.value = _mapListGuide.value?.size
        _isLoadBtnEnable.value = enableLoadBtn(currentmap.size)
    }

    fun guideregistedOk(){
        _isDialogLoadEnable.value = false
        _isLoadingDataGuide.value = false
        _isGuideRegisted.value = false
        _countGuides.value = 0
        _countRegisterGuide.value = 0
        _mapListGuide.value = emptyMap()
        _conteQR.value = ""
    }

    fun reset() {
        _pagoCod.value = ""
        _valueCod.value = ""
        _mapListGuide.value = emptyMap()
        _conteQR.value = ""
        _countGuides.value=0
        _countRegisterGuide.value=0
        _conteQR.value=""
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

    fun navigation(navigationController: NavHostController) {
        val previousScreenName = navigationController.previousBackStackEntry?.destination?.route
        if (previousScreenName.equals("MenuGuideScreen")) {
            navigationController.popBackStack()
            reset()
        } else {
            reset()
            navigationController.navigate(AppScreen.MenuGuideScreen.route)
        }
    }
}