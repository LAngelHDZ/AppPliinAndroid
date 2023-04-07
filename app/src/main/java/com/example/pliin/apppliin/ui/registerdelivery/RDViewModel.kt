package com.example.pliin.apppliin.ui.registerdelivery


import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RDViewModel @Inject() constructor()  : ViewModel() {
    private val _isSesionDialog = MutableLiveData<Boolean>()
    var isSesionDialog : LiveData<Boolean> = _isSesionDialog
    private val _conteQR = MutableLiveData<String>()
    val contentQR : LiveData<String> = _conteQR

    private val _guia = MutableLiveData<String>()
    val guia : LiveData<String> =_guia

    private val _isSearchEnable = MutableLiveData<Boolean>()
    val isSearchEnable: LiveData<Boolean> = _isSearchEnable

    fun onSearchChanged(guia:String){
        _guia.value = guia
        _isSearchEnable.value = enableSearch(guia)
    }
    fun enableSearch(guia: String) =  guia.length >9

    fun onSesionDialog(){
        _isSesionDialog.value = false
    }
    fun getContentQR(result: ScanIntentResult) {
        _conteQR.value = result.contents
        _isSesionDialog.value = true
    }
    fun initScanner(scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>) {

        val scanOptions = ScanOptions()
        scanOptions.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        scanOptions.setOrientationLocked(true)
        scanOptions.setPrompt("Scannear QR/Barcode")
        scanOptions.setBeepEnabled(false)
        scanLauncher.launch(scanOptions)
    }




}