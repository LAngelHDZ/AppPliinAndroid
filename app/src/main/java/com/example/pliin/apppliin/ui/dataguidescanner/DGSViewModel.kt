package com.example.pliin.apppliin.ui.dataguidescanner

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.repository.CustomCameraX
import com.example.pliin.apppliin.domain.usecase.RechazadoUseCase
import com.example.pliin.apppliin.domain.usecase.SetDeliveryUseCase
import com.example.pliin.apppliin.domain.usecase.SetTryDeliveryUseCse
import com.example.pliin.apppliin.generals.GeneralMethodsGuide
import com.example.pliin.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DGSViewModel @Inject() constructor(
    private val setDeliveryUseCase: SetDeliveryUseCase,
    private val setTryDeliveryUseCse: SetTryDeliveryUseCse,
    private val rechazadoUseCase: RechazadoUseCase,
    private val generalMethodsGuide: GeneralMethodsGuide,
    private val repo: CustomCameraX
) : ViewModel() {


    private val _isAlertDialogexit = MutableLiveData<Boolean>()
    var isAlertDialogexit: LiveData<Boolean> = _isAlertDialogexit

    private val _isShowCameraX = MutableLiveData<Boolean>()
    var isShowCameraX: LiveData<Boolean> = _isShowCameraX

    private val _isAlertDialogConfirmation = MutableLiveData<Boolean>()
    var isAlertDialogConfirmation: LiveData<Boolean> = _isAlertDialogConfirmation

    private val _isDeliveryConfirmation = MutableLiveData<Boolean>()
    var isDeliveryConfirmation: LiveData<Boolean> = _isDeliveryConfirmation

    private val _isEnabledTFCommentRecibe = MutableLiveData<Boolean>()
    var isEnabledTFCommentRecibe: LiveData<Boolean> = _isEnabledTFCommentRecibe

    private val _isAnotherParent = MutableLiveData<Boolean>()
    var isAnotherParent: LiveData<Boolean> = _isAnotherParent

    private val _isBtnRegisterStatus = MutableLiveData<Boolean>()
    var isBtnRegisterStatus: LiveData<Boolean> = _isBtnRegisterStatus

    private val _isBtnTakePhoto = MutableLiveData<Boolean>()
    var isBtnTakePhoto: LiveData<Boolean> = _isBtnTakePhoto


    private val _selectedOption = MutableLiveData<String>()
    var selectedOption: LiveData<String> = _selectedOption

    private val _parentOrFailDelivery = MutableLiveData<String>()
    var parentOrFailDelivery: LiveData<String> = _parentOrFailDelivery

    private val _nameCliente = MutableLiveData<String>()
    var nameCliente: LiveData<String> = _nameCliente

    private val _nameRecibe = MutableLiveData<String>()
    var nameRecibe: LiveData<String> = _nameRecibe

    private val _titleAlertDialog = MutableLiveData<String>()
    var titleAlertDialog: LiveData<String> = _titleAlertDialog

    private val _textAlertDialog = MutableLiveData<String>()
    var textAlertDialog: LiveData<String> = _textAlertDialog

    private val _status = MutableLiveData<String>()
    var status: LiveData<String> = _status

    private val _typePago = MutableLiveData<String>()
    var typePago: LiveData<String> = _typePago

    private val _onTypePago = MutableLiveData<Boolean>()
    var onTypePago: LiveData<Boolean> = _onTypePago

    private val _listStatusIntentos = MutableLiveData<List<String>>()
    var listStatusIntentos: LiveData<List<String>> = _listStatusIntentos

    private val _statusIntentos = MutableLiveData<String>()
    var statusIntentos: LiveData<String> = _statusIntentos

    private val _directoryPhoto = MutableLiveData<String>()
    var directoryPhoto: LiveData<String> = _directoryPhoto

    private val _directoryPhotoList = MutableLiveData<MutableList<String>>()
    var directoryPhotoList: LiveData<MutableList<String>> = _directoryPhotoList

    private val _directoryPhotoPago = MutableLiveData<String>()
    var directoryPhotoPago: LiveData<String> = _directoryPhotoPago

    private val _typedirectoryPhoto = MutableLiveData<String>()
    var typedirectoryPhoto: LiveData<String> = _typedirectoryPhoto

    private val permissionRequestChannel = Channel<Boolean>()
    val permissionRequestFlow = permissionRequestChannel.receiveAsFlow()



    fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    ) {
        viewModelScope.launch {
            repo.showCameraPreview(
                previewView,
                lifecycleOwner
            )
        }
    }

    fun captureAndSave(context: Context, typeDirectoryPhoto: String) {
        viewModelScope.launch {
            repo.captureAndSaveImage(context)
            delay(1500)

            delay(1500)
            _isShowCameraX.value = false

            if (typeDirectoryPhoto.equals("Trf")){
                _directoryPhotoPago.value = repo.getDirectoryPhoto()
                Log.d("Directorio de la photo en DSGVIewmodelpago", directoryPhotoPago.value!!)
            }else{
                _directoryPhoto.value = repo.getDirectoryPhoto()
                Log.d("Directorio de la photo en DSGVIewmodelfirma", directoryPhoto.value!!)
                _isBtnRegisterStatus.value = btnContinueRegisterStu(directoryPhoto.value!!)
            }
        }
    }

    fun captureAndSaveBoucher(context: Context) {
        viewModelScope.launch {
            repo.captureAndSaveImage(context)
            delay(1500)
            _directoryPhoto.value = repo.getDirectoryPhoto()
            delay(1500)
            _isShowCameraX.value = false
            Log.d("Directorio de la photo en DSGVIewmodel", directoryPhotoPago.value!!)
            _isBtnRegisterStatus.value = btnContinueRegisterStu(directoryPhotoPago.value!!)
        }
    }

    fun onPhotoCaptured(directoryPhoto:String) = directoryPhoto.isNotEmpty()

    fun onShowCameraX(typePhoto:String){
        _typedirectoryPhoto.value=typePhoto
        _isShowCameraX.value = true
    }

    fun onRadioSelectTransfer(option:Boolean){
        if(option){
            _typePago.value=  "TRANSFERENCIA"
            _onTypePago.value=true
        }else {
            _onTypePago.value=false
            _typePago.value=   "EFECTIVO"
        }
    }

    fun onAlertDialogexit(exitConfirmation: Boolean, navigationController: NavHostController) {
        _isAlertDialogexit.value = false

        if (exitConfirmation) {
            reset()
            navigationController.popBackStack()
        }
    }

    fun onChangeListStatusIntentos(Status: String) {
        Log.i("status en metodo on chagnge", Status)
        _statusIntentos.value = Status
        if (Status.isNullOrEmpty()) {
            _listStatusIntentos.value = listOf(
                "No esta",
                "Cerrado",
                "Mudanza",
                "No existe No#",
                "No existe calle",
                "Sin Dinero",
                "Rechazado",
                "Recoleccion",
                "Vacaciones",
                "No hay Adulto quien firme"
            )
        } else {
            _listStatusIntentos.value = setListStatus(Status)
        }
    }

    private fun setListStatus(status: String): List<String> {
        Log.i("Status valida llenar list", status)
        var list: List<String> = emptyList()
        when (status) {
            "NO ESTA 1", "NO ESTA 2" -> {
                list = listOf(
                    "No esta",
                    "Mudanza",
                    "No existe No#",
                    "No existe calle",
                    "Rechazado",
                    "Recoleccion",
                    "Vacaciones"
                )
            }
            "CERRADO 1", "CERRADO 2" -> {
                list = listOf(
                    "Cerrado",
                    "Mudanza",
                    "No existe No#",
                    "No existe calle",
                    "Rechazado",
                    "Recoleccion",
                    "Vacaciones"
                )
            }
            "SIN DINERO 1", "SIN DINERO 2" -> {
                list = listOf(
                    "Sin dinero",
                    "Mudanza",
                    "No existe No#",
                    "No existe calle",
                    "Rechazado",
                    "Recoleccion",
                    "Vacaciones"
                )
            }
            "NO HAY ADULTO QUE FIRME 1", "NO HAY ADULTO QUE FIRME 2" -> {
                list = listOf(
                    "No hay Adulto quien firme",
                    "Mudanza",
                    "No existe No#",
                    "No existe calle",
                    "Rechazado",
                    "Recoleccion",
                    "Vacaciones"
                )
            }
            else -> {
                list = listOf(
                    "No esta",
                    "Cerrado",
                    "Mudanza",
                    "No existe No#",
                    "No existe calle",
                    "Sin Dinero",
                    "Rechazado",
                    "Recoleccion",
                    "Vacaciones",
                    "No hay Adulto quien firme"
                )
            }
        }
        Log.i("listado en procesando", "{$list}")
        return list
    }

    fun getNameCLient(client: String) {
        _nameCliente.value = client
    }

    fun onValueChangedParents(otherparent: String) {
        _parentOrFailDelivery.value = otherparent
        _isEnabledTFCommentRecibe.value=enabledTFCommentRecibe(otherparent)
        Log.i("pariente", "${selectedOption.value}")
        Log.i("Recibio", "${nameRecibe.value}")
    }

    fun onValueChangedRecibe(nameparent: String, typeStatus: String) {
        _nameRecibe.value = nameparent

        if (typeStatus.equals("ENTREGA")){
            _isBtnTakePhoto.value = btnTakePhoto(nameparent)
        }else{
            _isBtnRegisterStatus.value= btnContinueRegisterStu(nameparent)
        }

        Log.i("pariente", "${selectedOption.value}")
        Log.i("Recibio", "${nameRecibe.value}")
    }

    @SuppressLint("SuspiciousIndentation")
    fun onValueChanged(selected: String) {
        if (selected.equals("Otro")) {
            _nameRecibe.value = ""
            _parentOrFailDelivery.value = ""
            _isAnotherParent.value = true
        } else if (selected.equals("Titular")){
            _nameRecibe.value = nameCliente.value
            _parentOrFailDelivery.value = selected
            _isAnotherParent.value = false
            _isBtnTakePhoto.value = btnTakePhoto(nameCliente.value!!)
        } else {
            _nameRecibe.value = ""
            _isAnotherParent.value = false
            _selectedOption.value = selected
            _parentOrFailDelivery.value = selected
            _isEnabledTFCommentRecibe.value= enabledTFCommentRecibe(selected)
        }

        _selectedOption.value = selected
        Log.i("pariente", "${selectedOption.value}")
        Log.i("Recibio", "${nameRecibe.value}")
    }


    fun enabledTFCommentRecibe(parameter:String) = parameter.length >1

    fun onAlertDialogExitexchange() {
        _isAlertDialogexit.value = true
    }

    fun   btnContinueRegisterStu(commentOrRecibe:String)=commentOrRecibe.length>1

    fun btnTakePhoto(commentOrRecibe:String)=commentOrRecibe.length>1

    fun onAlertDialogConfirmationexchange(title: String, text: String, status: String) {
        _status.value = status
        _titleAlertDialog.value = title
        _textAlertDialog.value = text
        _isAlertDialogConfirmation.value = true
    }

    fun reset() {
        _isBtnTakePhoto.value=false
        _isBtnRegisterStatus.value=false
        _isEnabledTFCommentRecibe.value=false
        _status.value = ""
        _titleAlertDialog.value = ""
        _textAlertDialog.value = ""
        _parentOrFailDelivery.value = ""
        _isAnotherParent.value = false
        _isAlertDialogConfirmation.value = false
        _isDeliveryConfirmation.value = false
        _status.value = ""
        _nameCliente.value = ""
        _nameRecibe.value = ""
        _selectedOption.value = ""
        _listStatusIntentos.value = emptyList()
    }

    fun onAlertDeliveryConfirmation(newDelivery: Boolean, navigationController: NavHostController) {
        reset()
        if (newDelivery) {
            navigationController.popBackStack()
        } else {
            navigationController.navigate(AppScreen.AppMainScreen.route)
        }
    }

    fun setDelivery(
        guide: String,
        recordId: String,
        navigationController: NavHostController,
        idPreM: String,
        cod: String
    ) {
        var responseOK: Boolean
        _isDeliveryConfirmation.value = true
        
        viewModelScope.launch() {
            delay(1700)
            Log.i("Guide", guide)
            Log.i("recordId", recordId)
            status.value?.let { Log.i("status", it) }
            // Log.i("recibe",nameRecibe.value!!)
            //  Log.i("seleted", selectedOption.value!!)

            var typePago=""
            var pago=""

            if (cod.equals("SI")){
                typePago= _typePago.value!!
                pago = if (typePago.equals("EFECTIVO")){
                    "NO CONFIRMADO"
                }else{
                    "CONFIRMADO"
                }
            }else{
                typePago= "NO APLICA"
                pago="NO APLICA"
            }



            if (status.value.equals("ENTREGADO")) {
                responseOK = setDeliveryUseCase.invoke(
                    guide,
                    idPreM,
                    recordId,
                    generalMethodsGuide.toUpperLetter(status.value!!),
                    textfieldvacio(nameRecibe.value),
                    textfieldvacio(parentOrFailDelivery.value),
                    directoryPhoto.value!!,
                    directoryPhotoPago.value!!,
                    pago,
                    typePago

                )

            }
            /*else if(parentOrFailDelivery.value!!.equals("Rechazado")){
                responseOK = rechazadoUseCase.invoke(
                    guide,
                    recordId,
                    textfieldvacio(status.value),
                    textfieldvacio(nameRecibe.value),
                    textfieldvacio(parentOrFailDelivery.value)
                )
                Log.i("Opcion rechazado","Rechazado paquete")
            }*/
            else {
                responseOK = setTryDeliveryUseCse.invoke(
                    guide,
                    recordId,
                    textfieldvacio(status.value),
                    textfieldvacio(parentOrFailDelivery.value),
                    textfieldvacio(nameRecibe.value)
                )
            }

            if (responseOK) {
                navigationController.navigate(AppScreen.LoadingDeliveryScreen.route)
                reset()
            }
        }
    }

    private fun consecutivointento(intentStatus: String): String {
        var statusAPI = statusIntentos.value
        var consecutivo = ""
        var cadena: CharArray = charArrayOf()
        if (!statusAPI.isNullOrEmpty()) {
            cadena = statusAPI!!.toCharArray()

            if (validateStatusIntento(intentStatus)) {
                Log.i("soy una opcion valida", intentStatus)
                if (statusIntentos.value.equals("No value")) {
                    consecutivo = "1"
                    Log.i("soy vacio valor del api", statusIntentos.value.toString())
                } else {
                    Log.i("are el recorrido", statusIntentos.value.toString())
                    for (i in cadena.indices) {
                        Log.i("valor cadena ", cadena[i].toString())
                        if (cadena[i] == '1') {
                            Log.i("encontre el valor ", cadena[i].toString())
                            consecutivo = "2"
                            cadena[i] = '2'

                        } else if (cadena[i] == '2') {
                            Log.i("encontre el valor ", cadena[i].toString())
                            cadena[i] = '3'
                            consecutivo = "3"
                        }
                    }
                }
            }
        }
        Log.i("conversion", cadena.joinToString())
        return "$intentStatus $consecutivo"
        //return cadena.joinToString(separator = "")
    }

    private fun validateStatusIntento(status: String): Boolean {
        return (status.equals("No esta") || status.equals("Cerrado") || status.equals("Sin dinero") || status.equals(
            "No hay Adulto quien firme"
        ))

    }

    private fun textfieldvacio(text: String?): String {
        val value: String = if (text.isNullOrEmpty()) {
            ""
        } else {
            generalMethodsGuide.toUpperLetter(
                consecutivointento(
                    generalMethodsGuide.quitarsimbolos(text)
                )
            )
        }
        return value
    }

}