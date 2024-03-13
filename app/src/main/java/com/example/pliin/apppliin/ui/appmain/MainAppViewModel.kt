package com.example.pliin.apppliin.ui.appmain

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.model.avisopagoitem.FieldDataAvisoItem
import com.example.pliin.apppliin.domain.usecase.AvisoPagoUseCase
import com.example.pliin.apppliin.domain.usecase.DeleteUserUSeCase
import com.example.pliin.apppliin.domain.usecase.LoadEmployeeUseCase
import com.example.pliin.apppliin.domain.usecase.LogoutUseCase
import com.example.pliin.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainAppViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val deleteUserUSeCase: DeleteUserUSeCase,
    private val loadEmployeeUseCase: LoadEmployeeUseCase,
    private val avisoPagoUseCase: AvisoPagoUseCase
) : ViewModel() {

    private val _isLogout = MutableLiveData<Boolean>()
    var isLogout: LiveData<Boolean> = _isLogout
    private val _isLoged = MutableLiveData<Boolean>()
    var isLoged: LiveData<Boolean> = _isLoged

    private val _nameEmployye = MutableLiveData<String>()
    var nameEmployee: LiveData<String> = _nameEmployye

    private val _areaEmployye = MutableLiveData<String>()
    var areaEmployee: LiveData<String> = _areaEmployye

    private val _avisoMessage = MutableLiveData<String>()
    var avisoMessage: LiveData<String> = _avisoMessage
    private val _avisoMessageTitle = MutableLiveData<String>()
    var avisoMessageTitle: LiveData<String> = _avisoMessageTitle

    private val _showAviso = MutableLiveData<Int>()
    var showAviso: LiveData<Int> = _showAviso

    private val _lockedAviso = MutableLiveData<Int>()
    var lockedAviso: LiveData<Int> = _lockedAviso

    private val _countDias = MutableLiveData<Int>()
    var countDias: LiveData<Int> = _countDias

    private val _progressUnlocked = MutableLiveData<Boolean>()
    var progressUnlocked: LiveData<Boolean> = _progressUnlocked

    private val _enabledButton = MutableLiveData<Boolean>()
    var enabledButton: LiveData<Boolean> = _enabledButton

    private val _isUnlocked = MutableLiveData<Boolean>()
    var isUnlocked: LiveData<Boolean> = _isUnlocked

    private val _data = MutableLiveData<FieldDataAvisoItem>()
    var data: LiveData<FieldDataAvisoItem> = _data




    fun logut(navigationController: NavHostController) {
        _isLoged.value = false
        _isLogout.value = true
        viewModelScope.launch {
            delay(1500)
            if (logoutUseCase.invoke()) {
                deleteUserUSeCase.invoke()
                navigate(navigationController)
                _isLoged.value = true
            }
        }
    }

    fun gsaveDataEmployee(name: String, area: String) {
        _nameEmployye.value = name
        _areaEmployye.value = area
        _isLoged.value = false
        viewModelScope.launch {
           avisoPago()

        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun avisoPago() {

//        viewModelScope.launch {
            val response = avisoPagoUseCase.invoke()

            if (response != null) {
                _lockedAviso.value = response.bloqueoAviso
                _showAviso.value = response.avisoShow

               val  mesFactura = response.mes!!
              val  diaFactura = response.diaFactura!!
               val  mesSuspencion = response.mesSuspencion!!
                val diaSuspencion = response.diaSuspencion!!


            if (_lockedAviso.value == 0) {
                if (response.contador!! < 4){

                    _avisoMessageTitle.value="¡Gracias por su atención!"
                    _avisoMessage.value = "Hola estimado cliente SALTER le informamos que el corte del servicio es este $diaFactura de $mesFactura, la factura correspondiente ya ha sido enviada al correo electrónico notificado por la empresa. "
                }else{

                     _avisoMessageTitle.value="¡Gracias por su atención!"
                    _avisoMessage.value="Hola estimado cliente SALTER le informamos que el corte del servicio fue este $diaFactura de $mesFactura, el servicio será suspendido este próximo $diaSuspencion de $mesSuspencion. por favor, verifique su bandeja de entrada para cubrir la factura correspondiente y evitar la suspensión temporal del servicio.\n"
                }
                _enabledButton.value=true

            } else {
                _avisoMessageTitle.value="¡Gracias por su atención!"

                _avisoMessage.value =
                    "El sistema ha sido bloqueado, comunícate a servicio al cliente, será un placer ayudarte."

            }

            _data.value = response
       }
    }

    fun closeAviso() {
            _showAviso.value = 0
        _isUnlocked.value=false
//        _enabledButton.value=true
//            _countDias.value =_data.value?.contador

    }

    fun verificarUnlocked(){
        viewModelScope.launch {
            _progressUnlocked.value=true
            avisoPago()
            delay(1000)
            if (_lockedAviso.value==0){
                _isUnlocked.value=true
                _avisoMessage.value="Sistema desbloqueado, gracias por su pago"
                _showAviso.value=1
            }

            _progressUnlocked.value =false
        }
    }

    private fun navigate(navController: NavHostController) {
        val previousScreenName = navController.previousBackStackEntry?.destination?.route
        if (previousScreenName.equals("LoginScreen")) {

            navController.popBackStack()
            _isLogout.value = false
        } else {
            navController.navigate(AppScreen.LoginScreen.route)
            _isLogout.value = false
        }
    }
}