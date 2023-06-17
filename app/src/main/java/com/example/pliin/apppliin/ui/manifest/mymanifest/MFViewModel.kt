package com.example.pliin.apppliin.ui.manifest.mymanifest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pliin.apppliin.data.database.dao.EmployeeDao
import com.example.pliin.apppliin.domain.usecase.GetManifestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MFViewModel @Inject constructor(
    private val getManifestUseCase: GetManifestUseCase


) : ViewModel() {
    fun loadManifest() {
        val year: String = LocalDate.now().year.toString()
        val month = addZeroDate(LocalDate.now().monthValue)
        val day = addZeroDate(LocalDate.now().dayOfMonth)
        val dateDTO = "<=$month/$day/$year"
        viewModelScope.launch {
            getManifestUseCase(dateDTO)
        }
    }

    fun getdatenow(): String {
        val year: String = LocalDate.now().year.toString()
        val month = addZeroDate(LocalDate.now().monthValue)
        val day = addZeroDate(LocalDate.now().dayOfMonth)
        return "$year$month$day"
    }

    private fun addZeroDate(dayOrMonth: Int): String {
        return if (dayOrMonth < 10) {
            "0$dayOrMonth"
        } else {
            dayOrMonth.toString()
        }
    }

}