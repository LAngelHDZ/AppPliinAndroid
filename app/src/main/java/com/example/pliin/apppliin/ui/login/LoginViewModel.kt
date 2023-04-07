package com.example.pliin.apppliin.ui.login

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.GetGuideUseCase
import com.example.pliin.apppliin.domain.GetUserUseCase
import com.example.pliin.apppliin.domain.LoginUseCase
import com.example.pliin.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase, private val getUserUseCase: GetUserUseCase,private val getGuideUseCase: GetGuideUseCase) :
    ViewModel() {

    private val _isSesionDialog = MutableLiveData<Boolean>()
    var isSesionDialog : LiveData<Boolean> = _isSesionDialog

    private val _user = MutableLiveData<String>()
    val user : LiveData<String> =_user

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> =_password

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable

    fun onLoginChanged(user:String, password:String){
        _user.value = user
        _password.value = password
        _isLoginEnable.value = enableLogin(user,password)
    }

    private fun enableLogin(user: String, password: String) = user.length>8 && password.isNotEmpty()

     fun onCreate(){
        viewModelScope.launch {
            val result = getUserUseCase()
            if(!result.isNullOrEmpty()){
                Log.i("System","User recuperados")
                Log.i("System", result.toString())
            }else{
                Log.i("System","No hay usuarios")
                Log.i("System", result.toString())
            }
        }

       //  onGuideCreate()
    }
    fun onGuideCreate(){
        viewModelScope.launch {
            val result = getGuideUseCase()
            if(!result.isNullOrEmpty()){
                Log.i("System","Guides recuperados")
                Log.i("System", result.toString())
            }else{
                Log.i("System","No hay Guias")
                Log.i("System", result.toString())
            }
        }
    }


    fun onSesionDialog(){
        _isSesionDialog.value = false
    }

    fun onLoginSelected(navigationController: NavHostController) {
        viewModelScope.launch {
            val result = loginUseCase(user.value!!, password.value!!)
            if (result){
                Log.i("System", "result OK")
                _user.value =""
                _password.value = ""
                navigationController.navigate(AppScreen.AppMainScreen.route)
            }else{
                _isSesionDialog.value = true
                Log.i("System", "Invalid User")
            }
        }
    }
}