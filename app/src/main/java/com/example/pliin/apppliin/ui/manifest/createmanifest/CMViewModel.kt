package com.example.pliin.apppliin.ui.manifest.createmanifest

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
import com.example.pliin.apppliin.domain.model.direccionesguideitem.DatosGuideItem
import com.example.pliin.apppliin.domain.model.direccionesguideitem.DireccionesGuideItem
import com.example.pliin.apppliin.domain.model.emproyeeitem.DataEI
import com.example.pliin.apppliin.domain.model.emproyeeitem.FieldDataEI
import com.example.pliin.apppliin.domain.usecase.AddGuideManifestUseCase
import com.example.pliin.apppliin.domain.usecase.CreateManifestUseCase
import com.example.pliin.apppliin.domain.usecase.GetAllEmployeesUseCase
import com.example.pliin.apppliin.domain.usecase.GetConsecManUseCase
import com.example.pliin.apppliin.domain.usecase.GetGuideUseCase
import com.example.pliin.apppliin.domain.usecase.LoadEmployeeUseCase
import com.example.pliin.apppliin.domain.usecase.RegisterDatosPqtUseCase
import com.example.pliin.apppliin.domain.usecase.RegisterDireccionUseCase
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
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CMViewModel @Inject constructor(
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
    private val registerDireccionUseCase: RegisterDireccionUseCase

) : ViewModel() {

    private val _progressCircularLoad = MutableLiveData<Float>()
    var progressCircularLoad: LiveData<Float> = _progressCircularLoad

    private val _isDialogMessageGuide = MutableLiveData<Boolean>()
    var isDialogMessageGuide: LiveData<Boolean> = _isDialogMessageGuide

    private val _enabledSupRuta = MutableLiveData<Boolean>()
    var enabledSupRuta: LiveData<Boolean> = _enabledSupRuta

    private val _selectLocal = MutableLiveData<String>()
    var selectLocal: LiveData<String> = _selectLocal

    private val _isDialogExitScreen = MutableLiveData<Boolean>()
    var isDialogExitScreen: LiveData<Boolean> = _isDialogExitScreen

    /*Variable que habilita el modal para ingresar los datos de direccion del paquete*/
    private val _isDireccionDialog = MutableLiveData<Boolean>()
    var isDireccionDialog: LiveData<Boolean> = _isDireccionDialog

    /*Variable que habilita el modal para ingresar los datos de peso del paquete*/
    private val _isDatosPQTnDialog = MutableLiveData<Boolean>()
    var isDatosPQTnDialog: LiveData<Boolean> = _isDatosPQTnDialog

    private val _isSaveChanges = MutableLiveData<Boolean>()
    var isSaveChanges: LiveData<Boolean> = _isSaveChanges

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

    private val _nameEmployye = MutableLiveData<String>()
    var nameEmployye: LiveData<String> = _nameEmployye

    private val _areaEmployye = MutableLiveData<String>()
    var areaEmployye: LiveData<String> = _areaEmployye

    //Guarda la guia que se escribe en el input texfield
    private val _guia = MutableLiveData<String>()
    var guia: LiveData<String> = _guia

    private val _selectedOptionRuta = MutableLiveData<String>()
    var selectedOptionRuta: LiveData<String> = _selectedOptionRuta
    private val _selectedOptionSubRuta = MutableLiveData<String>()
    var selectedOptionSubRuta: LiveData<String> = _selectedOptionSubRuta

    private val _selectedOptionTM = MutableLiveData<String>()
    var selectedOptionTM: LiveData<String> = _selectedOptionTM

    private val _isLoadBtnEnable = MutableLiveData<Boolean>()
    val isisLoadBtnEnable: LiveData<Boolean> = _isLoadBtnEnable

    private val _isDialogLoadEnable = MutableLiveData<Boolean>()
    val isDialogLoadEnable: LiveData<Boolean> = _isDialogLoadEnable

    private val _messageGuideValidate = MutableLiveData<String>()
    val messageGuideValidate: LiveData<String> = _messageGuideValidate


//Variables para agregar la direccion a la guia

    private val _isAlertDialogHighValue = MutableLiveData<Boolean>()
    var isAlertDialogHighValue: LiveData<Boolean> = _isAlertDialogHighValue

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
    private val _mapListGuide = MutableLiveData<Map<String, String>>()
    var mapListGuide: LiveData<Map<String, String>> = _mapListGuide


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

    val keyGuide: Int = 1

    var createOne: Int = 0

    fun onSearchChanged(guia: String) {
        _guia.value = guia
        _isSearchEnable.value = enableSearch(guia)
    }

    fun enableSearch(guia: String) = guia.length > 9

    fun enableLoadBtn(CountGuide: Int) = CountGuide >= 1

    fun onChangedFormDireccion(
        nombre: String,
        telefono: String,
        dir1: String,
        dir2: String,
        dir3: String,
        cp: String,
        municipio: String
    ) {
        _nombre.value = nombre
        _telefono.value = telefono
        _dir1.value = dir1
        _dir2.value = dir2
        _dir3.value = dir3
        _cp.value = cp
        _municipio.value = municipio
        _isSelectbtn.value = enableBtnFormDir(nombre, telefono, dir1, dir2, dir3, cp, municipio)
    }

    fun calcularPesoVol() {
        val alto = _alto.value?.toFloat()
        val largo = largo.value?.toFloat()
        val ancho = ancho.value?.toFloat()
        val pesoVol = (ancho!! * alto!! * largo!! / 5000)

        if (pesoVol > 30) {
            _typePaq.value = "Excedente"
        } else {
            _typePaq.value = "No excedente"
        }
        _pesoVol.value = pesoVol.toString()
        _isSelectbtn.value = enableBtnFormDatosPqt(pesoVol.toString(), _typePaq.value.toString())
    }

    fun enableBtnFormDatosPqt(pesoVol: String, typePqt: String) =
        pesoVol.length > 1 && typePqt.length > 1

    fun enableBtnFormDir(
        nombre: String,
        telefono: String,
        dir1: String,
        dir2: String,
        dir3: String,
        cp: String,
        municipio: String
    ) =
        ((nombre.length > 1 && telefono.length >= 1 && cp.length > 1 && municipio.length > 1) && (dir1.length > 1 || dir2.length > 1 || dir3.length > 1))

    fun onRadioBtnSeleted(value: String) {
        if (value != "Chico") {
            _typeEmbalaje.value = true
            if (ancho.value.isNullOrEmpty() && alto.value.isNullOrEmpty() && largo.value.isNullOrEmpty() && pesoKg.value.isNullOrEmpty()) {
                _isenableBtnCalcular.value = false
            } else {
                _isenableBtnCalcular.value = enabledCaluclarenbaled(
                    alto.value!!,
                    ancho.value!!, largo.value!!, pesoKg.value!!
                )
            }
            if (pesoVol.value.isNullOrEmpty() && typePaq.value.isNullOrEmpty()) {
                _isSelectbtn.value = false
            } else {
                _isSelectbtn.value = enableBtnFormDatosPqt(pesoVol.value!!, typePaq.value!!)
            }
        } else {
            _typeEmbalaje.value = false
            _isSelectbtn.value = true
            _isenableBtnCalcular.value = false
        }
        _isFormDatosPqt.value = value == "Grande"
    }

    fun onChangedFormDatos(alto: String, largo: String, ancho: String, pesoKg: String) {
        _alto.value = alto
        _ancho.value = ancho
        _largo.value = largo
        _pesoKg.value = pesoKg
        _isenableBtnCalcular.value = enabledCaluclarenbaled(alto, ancho, largo, pesoKg)
    }

    fun enabledCaluclarenbaled(alto: String, ancho: String, largo: String, pesoKg: String) =
        alto.isNotEmpty() && largo.isNotEmpty() && ancho.isNotEmpty() && pesoKg.isNotEmpty()

    fun onValueChangedMT(selected: String) {
        if (!selectedOptionTM.value.equals(selected)) {
            _selectedOptionRuta.value = ""
        }
        _selectedOptionTM.value = selected
        _isSelectRutaEnabled.value = enableSelectTM(selected)
    }

    fun onValueChangeEmployee(name: FieldDataEI) {
        _nameEmployye.value = "${name.nombre} ${name.aPaterno} ${name.aMaterno}"
    }

    fun onValueChangedRuta(selected: String) {

        if (selected.equals("Local 1") || selected.equals("Local 2") || selected.equals("Costa Chica")) {
            _selectedOptionSubRuta.value = ""
            _selectLocal.value = selected
            _enabledSupRuta.value = true
            _selectedOptionRuta.value = selected
        } else {
            _selectLocal.value = ""
            _selectedOptionSubRuta.value = ""

            _enabledSupRuta.value = false
            _selectedOptionRuta.value = selected
            _isSelectbtn.value = enableSelectbtn(selected)
        }
    }

    fun onValueChangedSubRuta(selected: String) {
       val  selecccion =  if (_selectedOptionRuta.value.equals("Costa Chica")) "Costa Chica $selected" else selected

            _selectedOptionSubRuta.value = selecccion
            _isSelectbtn.value = enableSelectbtn(selected)

    }

    fun enableSelectTM(select: String) = select.length > 1

    fun enableSelectbtn(select: String) = select.length > 1

    fun guideregistedOk(navigationController: NavHostController) {
        onAlertDialogexit(false, navigationController)
        _isDialogLoadEnable.value = false
        _isLoadingDataGuide.value = false
        _isGuideRegisted.value = false
        _countGuides.value = 0
        _countRegisterGuide.value = 0
        _mapListGuide.value = emptyMap()
        _contentQR.value = ""
        reset()
    }

    fun backScreen(navigationController: NavHostController, modal: String) {
        if (modal.equals("SelectRuta")) {
            navigationController.popBackStack()
        } else {
            _isDialogExitScreen.value = true
        }
    }

    fun onAlertDialogexit(exit: Boolean, navigationController: NavHostController) {
        if (exit) {
            navigationController.popBackStack()
            _isDialogExitScreen.value = false
            _isDialogRuta.value = false
        } else {
            navigationController.popBackStack()
            reset()
            _isDialogExitScreen.value = false
        }
    }

    fun saveChanges() {
        _isSaveChanges.value = true
    }

    fun onHighValuePqt(highValue: String) {
        _isAlertDialogHighValue.value = false
        _isDireccionDialog.value = true
        _altoValor.value = highValue

    }

    fun clearForm() {
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
    }

    fun reset() {
        createOne = 0
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
        _nameEmployye.value = ""
        _consecutiveMan.value = 0
        _countGuides.value = 0
        _clavePreManifest.value = ""
        _mapListGuide.value = emptyMap()
        _progressCircularLoad.value = 0.0f
        _isDialogLoadEnable.value = false
        _isSelectbtn.value = false
        _isSelectRutaEnabled.value = false
        _isDialogRuta.value = true

        _enabledSupRuta.value = false
        _selectLocal.value=""
    }

    fun onAlertDialog() {
        _isDialogMessageGuide.value = false
        _isDialogLoadEnable.value = false
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

    fun onDialogLoadGuides() {
        _isDialogLoadEnable.value = true
        _messageGuideValidate.value = "Desea registrar las guias al sistema?"
    }

    fun continueSetGuides(area: String) {

        if (!_selectedOptionSubRuta.value.isNullOrEmpty()){
            _selectedOptionRuta.value = _selectedOptionSubRuta.value
        }
        _isDialogRuta.value = false
        _ruta.value = selectedOptionRuta.value
        _isSelectbtn.value = false

        getDataEmployee()
        clavePreGenerate(area)
    }

    fun getDataEmployee() {
        viewModelScope.launch {
            val data = loadEmployeeUseCase.invoke()
            if (data.area.equals("Operador Logistico")) {
                _nameEmployye.value = "${data.nombre} ${data.aPaterno} ${data.aMaterno}"
            } else {
                _listEmployees.value = getAllEmployeesUseCase.invoke() as List<DataEI>?
            }
            _areaEmployye.value = data.area
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun onContinueForm(form: String) {
        if (form == "datospqt") {
            val typeEmbalaje = _typeEmbalaje.value.toString()
            Log.i("Valor type Embalaje", typeEmbalaje)
            if (typeEmbalaje == "null") {
                _typeEmbalaje.value = false
            }
            Log.i("Valor type Embalaje livedata", "${_typeEmbalaje.value}")
            _isDatosPQTnDialog.value = false
            setDaatosGuia(_contentQR.value!!)
            Log.i("contenido de Qr", "${contentQR.value}")
            Log.i("Datos guardados Direcion", "${mapListDireccion.value!![contentQR.value]}")
            Log.i("Datos guardados peso", "${mapListDatosPqt.value}")
        } else {
            _isDireccionDialog.value = false
            _isDatosPQTnDialog.value = true

            /* if(){

             }*/
        }
        //_isSelectbtn.value = false
    }

    fun setDaatosGuia(guia: String) {
        val direccionmap = _mapListDireccion.value?.toMutableMap() ?: mutableMapOf()
        val datosPqtMap = _mapListDatosPqt.value?.toMutableMap() ?: mutableMapOf()
        direccionmap[guia] = DireccionesGuideItem(
            guia,
            telefono.value,
            nombre.value,
            dir1.value,
            dir2.value,
            dir3.value,
            cp.value,
            municipio.value,
            "${dir1.value} ${dir2.value} ${dir3.value}"
        )

        if (typeEmbalaje.value == true) {
            datosPqtMap[guia] = DatosGuideItem(
                guia,
                _alto.value?.toFloat(),
                _ancho.value?.toFloat(),
                _largo.value?.toFloat(),
                _pesoVol.value?.toFloat(),
                _pesoKg.value?.toFloat(),
                _typePaq.value,
                _altoValor.value
            )
        } else {
            datosPqtMap[guia] = DatosGuideItem(
                guia,
                1f,
                1f,
                1f,
                1f,
                1f,
                "No excedente",
                _altoValor.value
            )
        }
        /*  datosPqtMap[guia] = DatosGuideItem(
              guia,
              _alto.value?.toFloat(),
              _ancho.value?.toFloat(),
              _largo.value?.toFloat(),
              _pesoVol.value?.toFloat(),
              _pesoKg.value?.toFloat(),
              _typePaq.value
          )*/
        _mapListDatosPqt.value = datosPqtMap
        _mapListDireccion.value = direccionmap
        clearForm()
    }

    fun getContentQR(guia: String, navigationController: NavHostController) {
        val formateValidate = generalMethodsGuide.validateFormatGuia(guia)
        val currentmap = _mapListGuide.value?.toMutableMap() ?: mutableMapOf()
        val direccionmap = _mapListDireccion.value?.toMutableMap() ?: mutableMapOf()
        val datosPqtMap = _mapListDatosPqt.value?.toMutableMap() ?: mutableMapOf()
        val datosRepetidos = datosPqtMap[guia]
        val direccionRepetida = direccionmap[guia]?.guia
        val guiaRepetida = currentmap.get(guia)
        viewModelScope.launch {
            if (formateValidate) {
                if (guiaRepetida.isNullOrEmpty()) {
//                    val guideSystem = validateGuide(guia)
                    val guidevalidate = validateGuideSystemUseCase.invoke(guia)
                    val codeValidate = guidevalidate.component1() == "0"
                    Log.d("Codigo message", "$guidevalidate")
                    Log.d("Codigo message", "$codeValidate")
                    Log.d("mesage en validación", "$codeMessage")
                    if (codeValidate) {
//                        val guideAtManifest = guideOtherManifest(guia)
                        val guideOtherManifest = getGuideUseCase.invoke(guia, "Asignado")
                        val code400 = guideOtherManifest.component1().isNullOrEmpty()
                        Log.d("mesage en validación", "$code400")
                        if (code400) {
                            var accessIfDataPqt = false
                            if (direccionRepetida.isNullOrEmpty()) {
                                Log.d("Dentro de if 1", "$")
                                val existeDireccion = validateExistsAddressUseCase.invoke(guia)
                                if (!existeDireccion) {
                                    Log.d("Dentro de if 2", "$")
                                    _isAlertDialogHighValue.value = true
                                } else {
                                    accessIfDataPqt = true
                                }
                            }
                            if (accessIfDataPqt) {
                                if (datosRepetidos?.guia.isNullOrEmpty()) {
                                    val existeDatosPqt = validateExistsDataPqtUseCase(guia)
                                    if (!existeDatosPqt) {
                                        _isDatosPQTnDialog.value = true
                                    }
                                }
                            }

                            Log.d("valos de isdialog direccion", "${isDireccionDialog.value}")

                            var key = currentmap.size + keyGuide
                            currentmap[guia] = guia
                            _mapListGuide.value = currentmap
                            _contentQR.value = guia
                            _countGuides.value = _mapListGuide.value?.size
                            //setData(guia)
                            _isLoadBtnEnable.value = enableLoadBtn(currentmap.size)
                        } else {
                            mesageValidateGuide("Esta guia ya se encuentra asignada a un manifiesto")

                        }
                    } else {
                        mesageValidateGuide("La guia $guia no se encuentra en el sistema")
                    }
                } else {
                    mesageValidateGuide("Ya ha agregado esta guia: $guia")

                }
            } else {
                mesageValidateGuide("El formato de la guia $guia no es valido")
            }
        }
    }

    fun mesageValidateGuide(message: String) {
        _isDialogMessageGuide.value = true
        _messageGuideValidate.value = message
    }

    fun guideOtherManifest(guide: String): Boolean {
        var guideOtherManifest: List<String?> = emptyList()
        viewModelScope.launch {
            guideOtherManifest = getGuideUseCase.invoke(guide, "Asignado")
            codeMessage = guideOtherManifest.isEmpty()
        }
        Thread.sleep(3000)
        return guideOtherManifest.isEmpty()
    }

    fun validateGuide(guide: String) {

        viewModelScope.launch {
            val guidevalidate = validateGuideSystemUseCase.invoke(guide)
            val code = guidevalidate.component1()
            Log.d("Codigo message", "$guidevalidate")
            Log.d("Codigo message", "$code")
            codeMessage = code == "0"
        }
        Thread.sleep(3000)
    }

    fun onRemoveguideList(guia: String, second: String) {
        val currentmap = _mapListGuide.value?.toMutableMap() ?: mutableMapOf()
        currentmap.remove(guia)
        _mapListGuide.value = currentmap
        _countGuides.value = _mapListGuide.value?.size
        _isLoadBtnEnable.value = enableLoadBtn(currentmap.size)
    }

    fun clavePreGenerate(area: String) {

        var typeManifest = ""
        when (selectedOptionTM.value) {
            "Local" -> {
                typeManifest = "1"

            }

            "Traslado" -> {
                typeManifest = "2"
            }

            else -> {
                typeManifest = "1"
            }

        }
        /* val typeManifest = if (selectedOptionTM.value.equals("Local")) {
             "LCA"
         } else {
             "TRF"
         }*/
        _typePreManifest.value = typeManifest
        _clavePreManifest.value = typeManifest + getdatenow() + "1"
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

    fun create(navigationController: NavHostController) {
        if (createOne <= 0) {
            createOne += 1
            viewModelScope.launch {
                getConsecutivoManifest()
                Thread.sleep(1000)
            }
        }
    }

    fun getConsecutivoManifest() {
        val year: String = LocalDate.now().year.toString()
        val month = addZeroDate(LocalDate.now().monthValue)
        val day = addZeroDate(LocalDate.now().dayOfMonth)

        val dateDTO = "$month/$day/$year"
        Log.i("Fecha now format DTO", dateDTO)

        viewModelScope.launch {
            val consecutivo = getConsecManUseCase.invoke(dateDTO).plus(1)
            _consecutiveMan.value = consecutivo
            val clave = clavePreManifest.value
            _clavePreManifest.value = "$clave$consecutivo"
            Log.i("Clave Completa PreM", "${clavePreManifest.value}")
            createManifest(consecutivo)
        }
    }

    fun selectEmployye(employee: FieldDataEI): String? {
        val nameEmployee: String? = if (areaEmployye.value.equals("Operador Logistico")) {
            "${employee.nombre} ${employee.aPaterno} ${employee.aMaterno}"
        } else {
            nameEmployye.value
        }

        return nameEmployee
    }

    fun createManifest(consecutivo: Int) {
        viewModelScope.launch {
            val employee = loadEmployeeUseCase.invoke()

            val claveManifest = clavePreManifest.value
            val nodo = "UPS"
            val totalPqt = countGuides.value.toString()
            val consecutivoMan = consecutiveMan.value.toString()
            var nameEmployee = selectEmployye(employee)
            val typeManifest = typePreManifest.value
            val ruta = generalMethodsGuide.toUpperLetter(ruta.value!!)
            val statusPreM = if (nameEmployee.isNullOrEmpty() || nameEmployee == "-") {
                "NO APLICADO"
            } else {
                "APLICADO"
            }

            if (nameEmployee.isNullOrEmpty()) {
                nameEmployee = ""

            }

            val dataDto: List<String?> = listOf(
                claveManifest,
                consecutivoMan,
                nodo,
                nameEmployee,
                ruta,
                totalPqt,
                totalPqt,
                typeManifest,
                statusPreM
            )

            createManifestUseCase.invoke(dataDto)
            Log.i("Datos dto Manifest", "$dataDto")
            loadGuideServer()
        }
    }

    fun loadGuideServer() {
        var progres = 0
        _progressCircularLoad.value = 0f
        _isLoadingDataGuide.value = true
        val currentmap = _mapListGuide.value?.toMutableMap() ?: mutableMapOf()
        val direccionmap = _mapListDireccion.value?.toMutableMap() ?: mutableMapOf()
        val datosPqtMap = _mapListDatosPqt.value?.toMutableMap() ?: mutableMapOf()
        val totalguides = countGuides.value
        val loading: Float = 100 / totalguides!!.toFloat()
        Log.i("Load", "$loading")
        Log.i("Load", "$totalguides")

        currentmap.let {
            viewModelScope.launch(Dispatchers.IO) {
                val idPreM = clavePreManifest.value!!
                val numPaquetes = "1"
                val observacion = "Asignado"

                for ((key, value) in currentmap) {
                    val direccion = direccionmap[value]
                    val datosPqt = datosPqtMap[value]
                    var dataGuiaDto = listOf(idPreM, value, numPaquetes, observacion)
                    Log.i(key, value)
                    var progress = progressCircularLoad.value
                    Log.i("Agregado", "$progress")
                    val deferredGuide = async { addGuideManifestUseCase.invoke(dataGuiaDto) }
                    var dataDir: List<String?> = emptyList()
                    if (!direccion?.guia.isNullOrEmpty()) {
                        dataDir = listOf(
                            generalMethodsGuide.toUpperLetter(direccion?.guia!!),
                            generalMethodsGuide.toUpperLetter(direccion.telefono!!),
                            generalMethodsGuide.toUpperLetter(direccion.nombre!!),
                            generalMethodsGuide.toUpperLetter(direccion.dir1!!),
                            generalMethodsGuide.toUpperLetter(direccion.dir2!!),
                            generalMethodsGuide.toUpperLetter(direccion.dir3!!),
                            generalMethodsGuide.toUpperLetter(direccion.cp!!),
                            generalMethodsGuide.toUpperLetter(direccion.municipio!!)
                        )
                        val deferredDireccion =
                            async { registerDireccionUseCase.invoke((dataDir as List<String>)) }
                        deferredDireccion.await()
                    }

                    if (!datosPqt?.guia.isNullOrEmpty()) {
                        var dataPqt = DatosPqtDto(
                            FieldDataDP(
                                datosPqt!!.guia,
                                datosPqt.alto!!,
                                datosPqt.ancho!!,
                                datosPqt.largo!!,
                                datosPqt.pesoVol!!,
                                datosPqt.pesoKg!!,
                                generalMethodsGuide.toUpperLetter(datosPqt.typePaq!!),
                                datosPqt.ALtoValor!!
                            )
                        )
                        val deferredDatosPqt = async { registerDatosPqtUseCase.invoke(dataPqt) }
                        deferredDatosPqt.await()
                    }
                    Log.i("Agregado", "listo")
                    deferredGuide.await()
                    Thread.sleep(1000)
                    launch(Dispatchers.Main) {
                        progres += 1
                        _countRegisterGuide.value = progres
                        _progressCircularLoad.value = _progressCircularLoad.value?.plus(loading)
                        // _progressCircularLoad.value= loadingporcentguide.value?.div(100)
                        progress = _progressCircularLoad.value
                        updateValue(progressCircularLoad.value!!)
                    }
                    dataGuiaDto = emptyList()
                    dataDir = emptyList()
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
        scanOptions.setTorchEnabled(true)
        scanLauncher.launch(scanOptions)
    }
}