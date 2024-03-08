package com.example.pliin.apppliin.ui.manifest.validatemanifest

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.data.network.dto.datospqt.DatosPqtDto
import com.example.pliin.apppliin.data.network.dto.datospqt.FieldDataDP
import com.example.pliin.apppliin.domain.model.GuideItem
import com.example.pliin.apppliin.domain.model.direccionesguideitem.DatosGuideItem
import com.example.pliin.apppliin.domain.model.direccionesguideitem.DireccionesGuideItem
import com.example.pliin.apppliin.domain.model.emproyeeitem.DataEI
import com.example.pliin.apppliin.domain.model.emproyeeitem.FieldDataEI
import com.example.pliin.apppliin.domain.usecase.AddGuideManifestUseCase
import com.example.pliin.apppliin.domain.usecase.CreateManifestUseCase
import com.example.pliin.apppliin.domain.usecase.GetAllEmployeesUseCase
import com.example.pliin.apppliin.domain.usecase.GetConsecManUseCase
import com.example.pliin.apppliin.domain.usecase.GetGuideUseCase
import com.example.pliin.apppliin.domain.usecase.GetGuidesManifestUseCase
import com.example.pliin.apppliin.domain.usecase.GetOneManifestUseCase
import com.example.pliin.apppliin.domain.usecase.LoadEmployeeUseCase
import com.example.pliin.apppliin.domain.usecase.RegisterDatosPqtUseCase
import com.example.pliin.apppliin.domain.usecase.RegisterDireccionUseCase
import com.example.pliin.apppliin.domain.usecase.UpdateManifestUseCase
import com.example.pliin.apppliin.domain.usecase.ValidateExistsAddressUseCase
import com.example.pliin.apppliin.domain.usecase.ValidateExistsDataPqtUseCase
import com.example.pliin.apppliin.domain.usecase.ValidateGuideSystemUseCase
import com.example.pliin.apppliin.generals.GeneralMethodsGuide
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VMViewModel @Inject constructor(
    private val generalMethodsGuide: GeneralMethodsGuide,
    private val getConsecManUseCase: GetConsecManUseCase,
    private val loadEmployeeUseCase: LoadEmployeeUseCase,
    private val createManifestUseCase: CreateManifestUseCase,
    private val addGuideManifestUseCase: AddGuideManifestUseCase,
    private val getAllEmployeesUseCase: GetAllEmployeesUseCase,
    private val getGuideUseCase: GetGuideUseCase,
    private val validateGuideSystemUseCase: ValidateGuideSystemUseCase,
    private val validateExistsAddressUseCase: ValidateExistsAddressUseCase,
    private val validateExistsDataPqtUseCase: ValidateExistsDataPqtUseCase,
    private val registerDatosPqtUseCase: RegisterDatosPqtUseCase,
    private val registerDireccionUseCase: RegisterDireccionUseCase,
    private val getGuidesManifestUseCase: GetGuidesManifestUseCase,
    private val updateManifestUseCase: UpdateManifestUseCase,
    private val getOneManifestUseCase: GetOneManifestUseCase

) : ViewModel() {

    private val _progressCircularLoad = MutableLiveData<Float>()
    var progressCircularLoad: LiveData<Float> = _progressCircularLoad

    private val _isDialogExitScreen = MutableLiveData<Boolean>()
    var isDialogExitScreen: LiveData<Boolean> = _isDialogExitScreen

    private val _isSesionDialog = MutableLiveData<Boolean>()
    var isSesionDialog: LiveData<Boolean> = _isSesionDialog

    private val _isLoadGuideManifest = MutableLiveData<Boolean>()
    var isLoadGuideManifest: LiveData<Boolean> = _isLoadGuideManifest

    /*Variable que habilita el modal para ingresar los datos de direccion del paquete*/
    private val _isDireccionDialog = MutableLiveData<Boolean>()
    var isDireccionDialog: LiveData<Boolean> = _isDireccionDialog

    /*Variable que habilita el modal para ingresar los datos de peso del paquete*/
    private val _isDatosPQTnDialog = MutableLiveData<Boolean>()
    var isDatosPQTnDialog: LiveData<Boolean> = _isDatosPQTnDialog

    private val _isAlertDialogexit = MutableLiveData<Boolean>()
    var isAlertDialogexit: LiveData<Boolean> = _isAlertDialogexit

    private val _isAlertDialogConfirmation = MutableLiveData<Boolean>()
    var isAlertDialogConfirmation: LiveData<Boolean> = _isAlertDialogConfirmation

    private val _isSearchEnable = MutableLiveData<Boolean>()
    var isSearchEnable: LiveData<Boolean> = _isSearchEnable

    private val _isDialogRuta = MutableLiveData<Boolean>()
    var isDialogRuta: LiveData<Boolean> = _isDialogRuta

    private val _isSelectbtn = MutableLiveData<Boolean>()
    var isSelectbtn: LiveData<Boolean> = _isSelectbtn

    private val _isSelectRutaEnabled = MutableLiveData<Boolean>()
    var isSelectRutaEnabled: LiveData<Boolean> = _isSelectRutaEnabled

    private val _idRecord = MutableLiveData<String>()
    var idRecord: LiveData<String> = _idRecord

    private val _nameEmployye = MutableLiveData<String>()
    var nameEmployye: LiveData<String> = _nameEmployye

    private val _areaEmployye = MutableLiveData<String>()
    var areaEmployye: LiveData<String> = _areaEmployye

    //Guarda la guia que se escribe en el input texfield
    private val _guia = MutableLiveData<String>()
    var guia: LiveData<String> = _guia

    private val _selectedOptionRuta = MutableLiveData<String>()
    var selectedOptionRuta: LiveData<String> = _selectedOptionRuta

    private val _selectedOptionTM = MutableLiveData<String>()
    var selectedOptionTM: LiveData<String> = _selectedOptionTM

    private val _isLoadBtnEnable = MutableLiveData<Boolean>()
    val isisLoadBtnEnable: LiveData<Boolean> = _isLoadBtnEnable

    private val _isDialogLoadEnable = MutableLiveData<Boolean>()
    val isDialogLoadEnable: LiveData<Boolean> = _isDialogLoadEnable

    private val _messageGuideValidate = MutableLiveData<String>()
    val messageGuideValidate: LiveData<String> = _messageGuideValidate


//Variables para agregar la direccion a la guia

    /*Variable que alamcena las guias que se escanean*/
    private val _mapListDireccion = MutableLiveData<Map<String, DireccionesGuideItem>>()
    var mapListDireccion: LiveData<Map<String, DireccionesGuideItem>> = _mapListDireccion

    private val _isFormDireccionBtn = MutableLiveData<Boolean>()
    var isFormDireccionBtn: LiveData<Boolean> = _isFormDireccionBtn

    private val _telefono = MutableLiveData<String>()
    var telefono: LiveData<String> = _telefono

    private val _nombre = MutableLiveData<String>()
    var nombre: LiveData<String> = _nombre

    private val _dir1 = MutableLiveData<String>()
    var dir1: LiveData<String> = _dir1

    private val _dir2 = MutableLiveData<String>()
    var dir2: LiveData<String> = _dir2

    private val _dir3 = MutableLiveData<String>()
    var dir3: LiveData<String> = _dir3

    private val _cp = MutableLiveData<String>()
    var cp: LiveData<String> = _cp

    private val _municipio = MutableLiveData<String>()
    var municipio: LiveData<String> = _municipio
    //=======================================================
    //Variables para agregar las medidas del paquete y el peso si es necesario
    private val _isAlertDialogHighValue = MutableLiveData<Boolean>()
    var isAlertDialogHighValue: LiveData<Boolean> = _isAlertDialogHighValue
    /*Variable que alamcena las guias que se escanean*/
    private val _mapListDatosPqt = MutableLiveData<Map<String, DatosGuideItem>>()
    var mapListDatosPqt: LiveData<Map<String, DatosGuideItem>> = _mapListDatosPqt

    private val _isFormDatosPqt = MutableLiveData<Boolean>()
    var isFormDatosPqt: LiveData<Boolean> = _isFormDatosPqt

    private val _isenableBtnCalcular = MutableLiveData<Boolean>()
    var isenableBtnCalcular: LiveData<Boolean> = _isenableBtnCalcular

    private val _typeEmbalaje = MutableLiveData<Boolean>()
    var typeEmbalaje: LiveData<Boolean> = _typeEmbalaje

    private val _alto = MutableLiveData<String>()
    var alto: LiveData<String> = _alto

    private val _ancho = MutableLiveData<String>()
    var ancho: LiveData<String> = _ancho

    private val _largo = MutableLiveData<String>()
    var largo: LiveData<String> = _largo

    private val _pesoVol = MutableLiveData<String>()
    var pesoVol: LiveData<String> = _pesoVol

    private val _pesoKg = MutableLiveData<String>()
    var pesoKg: LiveData<String> = _pesoKg

    private val _typePaq = MutableLiveData<String>()
    var typePaq: LiveData<String> = _typePaq

    private val _altoValor = MutableLiveData<String>()
    var altoValor: LiveData<String> = _altoValor
//=======================================================

    private val _ruta = MutableLiveData<String>()
    var ruta: LiveData<String> = _ruta

    private val _clavePreManifest = MutableLiveData<String>()
    var clavePreManifest: LiveData<String> = _clavePreManifest

    private val _typePreManifest = MutableLiveData<String>()
    var typePreManifest: LiveData<String> = _typePreManifest

    private val _titleAlertDialog = MutableLiveData<String>()
    var titleAlertDialog: LiveData<String> = _titleAlertDialog

    private val _textAlertDialog = MutableLiveData<String>()
    var textAlertDialog: LiveData<String> = _textAlertDialog

    private val _status = MutableLiveData<String>()
    var status: LiveData<String> = _status


    /*Variable que captura lo que captura el scanner en este caos las guias que escanee*/
    private val _contentQR = MutableLiveData<String>()
    val contentQR: LiveData<String> = _contentQR

    /*Variable que recupera  la lista de los operadores logisticos activos*/
    private val _listEmployees = MutableLiveData<List<DataEI>>()
    val listEmployees: LiveData<List<DataEI>> = _listEmployees

    /*Variable que alamcena las guias que se escanean*/
    private val _mapListGuide = MutableLiveData<Map<String, GuideItem>>()
    var mapListGuide: LiveData<Map<String, GuideItem>> = _mapListGuide

    private val _mapListGuideAdd = MutableLiveData<Map<String, String>>()
    var mapListGuideAdd: LiveData<Map<String, String>> = _mapListGuideAdd

    private val _isGuideRegisted = MutableLiveData<Boolean>()
    var isGuideRegisted: LiveData<Boolean> = _isGuideRegisted

    private val _countGuides = MutableLiveData<Int>()
    val countGuides: LiveData<Int> = _countGuides

    private val _consecutiveMan = MutableLiveData<Int>()
    val consecutiveMan: LiveData<Int> = _consecutiveMan

    private val _isLoadingDataGuide = MutableLiveData<Boolean>()
    var isLoadingDataGuide: LiveData<Boolean> = _isLoadingDataGuide

    private val _countRegisterGuide = MutableLiveData<Int>()
    var countRegisterGuide: LiveData<Int> = _countRegisterGuide

    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 1
        minimumFractionDigits = 1
    }

    var codeMessage: Boolean = false
    var btnpushCrate: Int = 0
    val keyGuide: Int = 1

    fun onSearchChanged(guia:String) {
        _guia.value = guia
        _isSearchEnable.value = enableSearch(guia)
    }

    fun loadData(
        nameEmploye:String,
        idRecord:String,
        route:String,
        claveManifest:String
    ){
        _countRegisterGuide.value=0
        _ruta.value =route
        _nameEmployye.value=nameEmploye
        _idRecord.value=idRecord
        _clavePreManifest.value=claveManifest
        loadGuidesManifest(claveManifest)
        _isLoadGuideManifest.value=false
    }
    fun loadGuidesManifest(claveManifest: String){
        val currentmap = _mapListGuide.value?.toMutableMap() ?: mutableMapOf()
        viewModelScope.launch{
            val response= getGuidesManifestUseCase.invoke(claveManifest)
            if (response != null) {
                for (value in response){
                    val data =value?.fieldData
                    val guia = value?.fieldData?.idGuia.toString()
                    currentmap[guia] = GuideItem(
                        data?.idGuia,
                        0xFFEBF5FB,
                        false
                    )
                    _mapListGuide.value = currentmap
                    _countGuides.value = _mapListGuide.value?.size
                }
            }
        }
    }

    fun onHighValuePqt(highValue:String){
        _isAlertDialogHighValue.value=false
        _isDireccionDialog.value=true
        _altoValor.value=highValue

    }

    fun enableSearch(guia: String) = guia.length > 9

    fun enableLoadBtn(CountGuide: Int,Employee:String) = (CountGuide >= 1 || Employee.length >1)


    fun enableBtnFormDatosPqt(pesoVol: String, typePqt: String) =
        pesoVol.length > 1 && typePqt.length > 1


    fun enabledCaluclarenbaled(alto: String, ancho: String, largo: String, pesoKg: String) =
        alto.isNotEmpty() && largo.isNotEmpty() && ancho.isNotEmpty() && pesoKg.isNotEmpty()


    fun onValueChangeEmployee(name: FieldDataEI) {
        _nameEmployye.value = "${name.nombre} ${name.aPaterno} ${name.aMaterno}"

        val nameEM = "${name.nombre} ${name.aPaterno} ${name.aMaterno}"
        _isLoadBtnEnable.value = enableLoadBtn(countGuides.value!!, nameEM)
    }


    fun guideregistedOk(navigationController: NavHostController) {
        backScreen(navigationController)
        _isDialogLoadEnable.value = false
        _isLoadingDataGuide.value = false
        _isGuideRegisted.value = false
        _countGuides.value = 0
        _countRegisterGuide.value = 0
        _mapListGuide.value = emptyMap()
        _contentQR.value = ""
        reset()
    }

    fun backScreen(navigationController: NavHostController) {
        reset()
        navigationController.popBackStack()
    }

    fun reset() {
        btnpushCrate=0
        _nombre.value = ""
        _telefono.value = ""
        _dir1.value = ""
        _dir2.value = ""
        _dir3.value = ""
        _cp.value = ""
        _municipio.value = ""

        _alto.value = ""
        _ancho.value = ""
        _largo.value = ""
        _pesoVol.value = ""
        _pesoKg.value = ""
        _typeEmbalaje.value = false
        _typePaq.value = ""
        _typePreManifest.value = ""

        _mapListDireccion.value = emptyMap()
        _mapListDatosPqt.value = emptyMap()
        _selectedOptionRuta.value = ""
        _selectedOptionTM.value = ""
        _ruta.value = ""
        _consecutiveMan.value = 0
        _countGuides.value = 0
        _clavePreManifest.value = ""
        _mapListGuide.value = emptyMap()
        _mapListGuideAdd.value = emptyMap()
        _progressCircularLoad.value = 0.0f
        _isDialogLoadEnable.value = false
        _isSelectbtn.value = false
        _isSelectRutaEnabled.value = false
        _isDialogRuta.value = true
    }

    fun onAlertDialog() {
        _isSesionDialog.value = false
        _isDialogLoadEnable.value = false
    }






    fun getContentQR(guia: String, navigationController: NavHostController) {
        val formateValidate = generalMethodsGuide.validateFormatGuia(guia)
        val currentmap = _mapListGuide.value?.toMutableMap() ?: mutableMapOf()
        val guiaRepetida = currentmap.get(guia)
        viewModelScope.launch {
            if (formateValidate) {
                if (!guiaRepetida?.idGuia.isNullOrEmpty()) {
//                    val guideSystem = validateGuide(guia)
                    currentmap[guia] = GuideItem(
                        guia,
                        0xFF4425a7,
                        true
                    )
                            _mapListGuide.value = currentmap
                            _contentQR.value = guia
                            _countGuides.value = mapListGuide.value?.size
                    _countRegisterGuide.value = _countRegisterGuide.value?.plus(1)
                            //setData(guia)
                } else {
                    _isSesionDialog.value = true
                    _messageGuideValidate.value = " La guia: $guia, no se encontró en este manifiesto"
                }
            } else {
                _isSesionDialog.value = true
                _messageGuideValidate.value = "El formato de la guia $guia no es valido"
            }
        }
    }



    fun getdatenow(): String {
        val year: String = LocalDate.now().year.toString()
        val month = addZeroDate(LocalDate.now().monthValue)
        val day = addZeroDate(LocalDate.now().dayOfMonth)
        return "$year$month$day"
    }



    fun selectEmployye(employee: FieldDataEI): String? {
        val nameEmployee: String? = if (areaEmployye.value.equals("Operador Logistico")) {
            "${employee.nombre} ${employee.aPaterno} ${employee.aMaterno}"
        } else {
            nameEmployye.value
        }

        return nameEmployee
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
        scanOptions.setTorchEnabled(true)
        scanLauncher.launch(scanOptions)
    }
}