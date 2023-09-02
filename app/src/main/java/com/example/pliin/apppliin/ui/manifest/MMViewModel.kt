package com.example.pliin.apppliin.ui.manifest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.usecase.user.LoadEmployeeUseCase
import com.example.pliin.apppliin.generals.GeneralMethodsGuide
import com.example.pliin.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MMViewModel @Inject constructor(
    private val loadEmployeeUseCase: LoadEmployeeUseCase,
    private val generalMethodsGuide: GeneralMethodsGuide

) : ViewModel() {

    fun navigationCreateManifest(navigationController: NavHostController) {
        viewModelScope.launch {
            val area = if (loadEmployeeUseCase.invoke().area!!.equals("Operador Logistico")) {
                "OperadorLogistico"
            } else {
                "AuxiliarAdministrativo"
            }
            navigationController.navigate(AppScreen.CreateManifestScreen.createRoute(area))
        }
    }
}