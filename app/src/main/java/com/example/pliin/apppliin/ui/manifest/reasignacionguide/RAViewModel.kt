package com.example.pliin.apppliin.ui.manifest.reasignacionguide

import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.usecase.guides.GetGuideUseCase
import com.example.pliin.apppliin.domain.usecase.manifest.GetOneManifestUseCase
import com.example.pliin.apppliin.domain.usecase.guides.ReasignarGuideUseCase
import com.example.pliin.apppliin.domain.usecase.manifest.UpdateManifestUseCase
import com.example.pliin.apppliin.generals.GeneralMethodsGuide
import com.example.pliin.navigation.AppScreen
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RAViewModel @Inject constructor(
    private val getGuideUseCase: GetGuideUseCase,
    private val generalMethodsGuide: GeneralMethodsGuide,
    private val reasignarGuideUseCase: ReasignarGuideUseCase,
    private val updateManifestUseCase: UpdateManifestUseCase,
    private val getOneManifestUseCase: GetOneManifestUseCase
):ViewModel(){
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

    fun getContentQR(guia: String, navigationController: NavHostController){
        val formatGuide = generalMethodsGuide.validateFormatGuia(guia)
        if (formatGuide) {
            _conteQR.value = guia
            _isLoadingDataGuide.value = true
            viewModelScope.launch {
                if (generalMethodsGuide.checkInternetConnection()) {
                    //  delay(1500)
                    val result = getGuideUseCase.invoke(guia,"")
//                Log.i("Status intento", result[12].toString())
//                Log.i("Status Guia", result[6].toString())
                    //  Log.i("System guide","$guia")
                    val guide = result[0]
                    val observacion = result[14]
                    if (!guide.equals("500")){
                        if (guide.isNullOrEmpty()){
                            messageGuideValidate("La guia $guia no se ha encontrado en un manifiesto")
                        }else if(observacion.equals("Reasignado")){
                            messageGuideValidate("Esta guia ya se encuentra reasignada, puede agregarla a otro manifiesto")
                        }else {
                            when (result[6]) {
                                "ENTREGADO" -> {
                                    Log.i("Status Guia", result[6].toString())
                                    messageGuideValidate("No se puede reasignar esta guia  con estatus; ENTREGADO")
                                }

                                "EN RUTA A SALTER" -> {
                                    messageGuideValidate("No se puede reasignar esta guia  con estatus; RUTA A SALTER")
                                }

                                "RETORNO" -> {
                                    messageGuideValidate("La guia No. $guia se encuentra en RETORNO A UPS")
                                }
                                else -> {
                                    val response = reasignarGuideUseCase(result[13]!!)
                                    val manifest= getOneManifestUseCase.invoke(result[1]!!)
                                    val totalpqt= manifest?.get(0)?.fieldData?.totalpqt?.minus(1)
                                    val totaguias= manifest?.get(0)?.fieldData?.totaolGuias?.minus(1)
                                    val nameEMploye = manifest?.get(0)?.fieldData?.nombreOperador
                                    val idRecord = manifest?.get(0)?.recordId
                                    val statusMAn = manifest!!.get(0)!!.fieldData!!.statusPreM
                                    val data:List<String?> = listOf(nameEMploye,totalpqt.toString(),totaguias.toString(),idRecord,statusMAn)
                                    val updateMan = updateManifestUseCase.invoke(data)
                                    if (response && updateMan){
                                        messageGuideValidate("La guia No. $guia se a reasignado, puede agrearla a otro manifiesto")
                                    }else{
                                        messageGuideValidate("Hubo un problema al reasignar la guia, favor de comunicarse con soporte")
                                    }
                                }
                            }
                        }
                    } else {
                        messageGuideValidate("Hubo un error al consultar la información")
                    }
                } else {
                    messageGuideValidate("Hubo un error de comunicación, favor de revisar su conexión a internet")
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
    fun initScanner(scanLauncher: ManagedActivityResultLauncher<ScanOptions,ScanIntentResult>){

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

        if (previousScreenName.equals("ManifiestoMainScreen")) {

            navigationController.popBackStack()
        } else {

            navigationController.navigate(AppScreen.ManifiestoMainScreen.route)
        }
    }
}