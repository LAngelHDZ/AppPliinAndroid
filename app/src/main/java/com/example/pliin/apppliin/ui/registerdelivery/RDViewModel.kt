package com.example.pliin.apppliin.ui.registerdelivery


import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.usecase.GetGuideUseCase
import com.example.pliin.apppliin.generals.GeneralMethodsGuide
import com.example.pliin.navigation.AppScreen
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RDViewModel @Inject() constructor(
    private val getGuideUseCase: GetGuideUseCase,
    private val generalMethodsGuide: GeneralMethodsGuide

) : ViewModel() {
    private val _isSesionDialog = MutableLiveData<Boolean>()
    var isSesionDialog: LiveData<Boolean> = _isSesionDialog

    private val _isLoadingDataGuide = MutableLiveData<Boolean>()
    var isLoadingDataGuide: LiveData<Boolean> = _isLoadingDataGuide

    private val _conteQR = MutableLiveData<String>()
    val contentQR: LiveData<String> = _conteQR

    private val _messageGuideValidate = MutableLiveData<String>()
    val messageGuideValidate: LiveData<String> = _messageGuideValidate

    private val _guia = MutableLiveData<String>()
    val guia: LiveData<String> = _guia

    private val _isSearchEnable = MutableLiveData<Boolean>()
    val isSearchEnable: LiveData<Boolean> = _isSearchEnable

    fun onSearchChanged(guia: String) {
        _guia.value = guia
        _isSearchEnable.value = enableSearch(guia)
    }

    fun enableSearch(guia: String) = guia.length > 9

    fun onAlertDialog() {
        _isSesionDialog.value = false
    }

    fun getContentQR(guia: String, navigationController: NavHostController) {

        val formatGuide = generalMethodsGuide.validateFormatGuia(guia)

        if (formatGuide) {
            _conteQR.value = guia
            _isLoadingDataGuide.value = true
            viewModelScope.launch {
                if (generalMethodsGuide.checkInternetConnection()) {
                    //  delay(1500)
                    val result = getGuideUseCase.invoke(guia)
//                Log.i("Status intento", result[12].toString())
//                Log.i("Status Guia", result[6].toString())
                    //  Log.i("System guide","$guia")
                    val guide = result[0]
                    if (!guide.equals("500")) {
                        if (guide.isNullOrEmpty()) {
                            messageGuideValidate("La guia $guia no se ha encontrado en un manifiesto")
                        } else {
                            when (result[6]) {
                                "ENTREGADO" -> {
                                    Log.i("Status Guia", result[6].toString())
                                    messageGuideValidate("La guia No. $guia ha sido registrada como ENTREGADO")
                                }

                                "DEVUELTO A SALTER" -> {
                                    messageGuideValidate("La guia No. $guia ha sido registrada como DEVUELTO A SALTER")
                                }

                                "EN RUTA A SALTER" -> {
                                    messageGuideValidate("La guia No. $guia se encuentra en RUTA A SALTER")
                                }

                                "ENTREGA FALLIDA" -> {
                                    messageGuideValidate("La guia No. $guia esta en ENTREGA FALLIDA")
                                }

                              /*  "ARRASTRE" -> {
                                    messageGuideValidate("La guia No. $guia se encuentra en ARRASTRE")
                                }*/

                                "EN ALMACEN " -> {
                                    messageGuideValidate("La guia No. $guia se encuentra en ALMACEN")
                                }

                                "RETORNO" -> {
                                    messageGuideValidate("La guia No. $guia se encuentra en RETORNO A UPS")
                                }

                                else -> {
                                    navigationController.navigate(
                                        AppScreen.DataGuideScannerScreen.createRoute(
                                            result[0]!!,
                                            result[1]!!,
                                            generalMethodsGuide.reemplazaCaracter(result[2]!!),
                                            generalMethodsGuide.reemplazaCaracter(result[3]!!),
                                            result[4]!!,
                                            result[5]!!,
                                            result[6]!!,
                                            result[7]!!,
                                            result[8]!!,
                                            result[9]!!,
                                            result[10]!!,
                                            result[11]!!,
                                            result[12]!!
                                        )
                                    )
                                    _isLoadingDataGuide.value = false
                                    _guia.value = ""
                                }
                            }
                        }
                    } else {
                        messageGuideValidate("Hubo un error al consultar la información")
                    }
                } else {
                    messageGuideValidate("Hubo un error de comunicación, favor de revizar su conexión a internet")
                }
            }
        } else {
            messageGuideValidate("Formato de Guia no Valido")
        }
    }

    private fun messageGuideValidate(message: String) {
        _messageGuideValidate.value = message
        _isLoadingDataGuide.value = false
        _isSesionDialog.value = true
    }

    //inicaliza el scanner
    fun initScanner(scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>) {

        val scanOptions = ScanOptions()
        scanOptions.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        scanOptions.setOrientationLocked(true)
        scanOptions.setPrompt("Scannear QR/Barcode")
        scanOptions.setBeepEnabled(false)
        scanOptions.setTorchEnabled(true)
        scanLauncher.launch(scanOptions)
    }

    fun navigation(navigationController: NavHostController) {
        val previousScreenName = navigationController.previousBackStackEntry?.destination?.route

        if (previousScreenName.equals("MainAppScreen/{nameEmployee}/{area}")) {

            navigationController.popBackStack()
        } else {

            navigationController.navigate(AppScreen.AppMainScreen.route)
        }
    }

}