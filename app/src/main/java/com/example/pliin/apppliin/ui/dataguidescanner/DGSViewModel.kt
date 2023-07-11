package com.example.pliin.apppliin.ui.dataguidescanner

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.usecase.RechazadoUseCase
import com.example.pliin.apppliin.domain.usecase.SetDeliveryUseCase
import com.example.pliin.apppliin.domain.usecase.SetTryDeliveryUseCse
import com.example.pliin.apppliin.generals.GeneralMethodsGuide
import com.example.pliin.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DGSViewModel @Inject() constructor(
    private val setDeliveryUseCase: SetDeliveryUseCase,
    private val setTryDeliveryUseCse: SetTryDeliveryUseCse,
    private val rechazadoUseCase: RechazadoUseCase,
    private val generalMethodsGuide: GeneralMethodsGuide
) : ViewModel() {
    private val _isAlertDialogexit = MutableLiveData<Boolean>()
    var isAlertDialogexit: LiveData<Boolean> = _isAlertDialogexit

    private val _isAlertDialogConfirmation = MutableLiveData<Boolean>()
    var isAlertDialogConfirmation: LiveData<Boolean> = _isAlertDialogConfirmation

    private val _isDeliveryConfirmation = MutableLiveData<Boolean>()
    var isDeliveryConfirmation: LiveData<Boolean> = _isDeliveryConfirmation

    private val _isAnotherParent = MutableLiveData<Boolean>()
    var isAnotherParent: LiveData<Boolean> = _isAnotherParent

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

    private val _listStatusIntentos = MutableLiveData<List<String>>()
    var listStatusIntentos: LiveData<List<String>> = _listStatusIntentos

    private val _statusIntentos = MutableLiveData<String>()
    var statusIntentos: LiveData<String> = _statusIntentos

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
        Log.i("pariente", "${selectedOption.value}")
        Log.i("Recibio", "${nameRecibe.value}")
    }

    fun onValueChangedRecibe(nameparent: String) {
        _nameRecibe.value = nameparent
        Log.i("pariente", "${selectedOption.value}")
        Log.i("Recibio", "${nameRecibe.value}")
    }

    @SuppressLint("SuspiciousIndentation")
    fun onValueChanged(selected: String) {
        if (selected.equals("Otro")) {
            _nameRecibe.value = ""
            _parentOrFailDelivery.value = ""
            _isAnotherParent.value = true
        } else if (selected.equals("Titular")) {
            _nameRecibe.value = nameCliente.value
            _parentOrFailDelivery.value = selected
            _isAnotherParent.value = false
        } else {
            _nameRecibe.value = ""
            _isAnotherParent.value = false
            _selectedOption.value = selected
            _parentOrFailDelivery.value = selected
        }
        _selectedOption.value = selected
        Log.i("pariente", "${selectedOption.value}")
        Log.i("Recibio", "${nameRecibe.value}")
    }

    fun onAlertDialogExitexchange() {
        _isAlertDialogexit.value = true
    }

    fun onAlertDialogConfirmationexchange(title: String, text: String, status: String) {
        _status.value = status
        _titleAlertDialog.value = title
        _textAlertDialog.value = text
        _isAlertDialogConfirmation.value = true
    }

    fun reset() {
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

    fun setDelivery(guide: String, recordId: String, navigationController: NavHostController) {
        var responseOK: Boolean
        _isDeliveryConfirmation.value = true
        viewModelScope.launch() {
            delay(1700)
            Log.i("Guide", guide)
            Log.i("recordId", recordId)
            status.value?.let { Log.i("status", it) }
            // Log.i("recibe",nameRecibe.value!!)
            //  Log.i("seleted", selectedOption.value!!)

            if (status.value.equals("ENTREGADO")) {
                responseOK = setDeliveryUseCase.invoke(
                    guide,
                    recordId,
                    generalMethodsGuide.toUpperLetter(status.value!!),
                    textfieldvacio(nameRecibe.value),
                    textfieldvacio(parentOrFailDelivery.value)
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
                    guide, recordId,
                    textfieldvacio(status.value), textfieldvacio(parentOrFailDelivery.value)
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