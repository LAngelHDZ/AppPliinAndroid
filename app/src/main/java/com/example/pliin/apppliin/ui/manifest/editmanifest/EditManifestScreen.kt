package com.example.pliin.apppliin.ui.manifest.editmanifest

import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.model.emproyeeitem.DataEI
import com.example.pliin.apppliin.domain.model.emproyeeitem.FieldDataEI
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.delay
import java.time.LocalDate


fun getdatenow(): String {
    return LocalDate.now().toString()
}

@Composable
fun EditManifestScreen(
    navigationController: NavHostController,
    nameEmploye: String,
    idRecord:String,
    route:String,
    idPrem:String,
    EMViewModel: EMViewModel = hiltViewModel()
) {
    val isLoadGuideManifest: Boolean by EMViewModel.isLoadGuideManifest.observeAsState(true)
    val isDialogExitScreen: Boolean by EMViewModel.isAlertDialogexit.observeAsState(false)

    if (isLoadGuideManifest){
        EMViewModel.loadData(nameEmploye,idRecord,route,idPrem)
    }

    val nombre: String by EMViewModel.nombre.observeAsState("")
    val telefono: String by EMViewModel.telefono.observeAsState("")
    val dir1: String by EMViewModel.dir1.observeAsState("")
    val dir2: String by EMViewModel.dir2.observeAsState("")
    val dir3: String by EMViewModel.dir3.observeAsState("")
    val cp: String by EMViewModel.cp.observeAsState("")
    val municipio: String by EMViewModel.municipio.observeAsState("")

    val alto: String by EMViewModel.alto.observeAsState("")
    val ancho: String by EMViewModel.ancho.observeAsState("")
    val largo: String by EMViewModel.largo.observeAsState("")
    val pesoKg: String by EMViewModel.pesoKg.observeAsState("")
    val pesoVol: String by EMViewModel.pesoVol.observeAsState("")
    val typeEmbalaje: Boolean by EMViewModel.typeEmbalaje.observeAsState(false)
    val isEnableBtnCalcular: Boolean by EMViewModel.isenableBtnCalcular.observeAsState(false)
    val typeExcedente: String by EMViewModel.typePaq.observeAsState("")
    val isFormDatosPqt: Boolean by EMViewModel.isFormDatosPqt.observeAsState(false)



    val isDialogHighValue: Boolean by EMViewModel.isAlertDialogHighValue.observeAsState(false)
    val isSearchEnable: Boolean by EMViewModel.isSearchEnable.observeAsState(false)
    val progressCircular: Float by EMViewModel.progressCircularLoad.observeAsState(0f)
    val guia: String by EMViewModel.guia.observeAsState("")
    val countGuide: Int by EMViewModel.countGuides.observeAsState(0)
    val selectedOptionRuta: String by EMViewModel.selectedOptionRuta.observeAsState("")
    val nameEmployee: String by EMViewModel.nameEmployye.observeAsState("")
    val areaEmployee: String by EMViewModel.areaEmployye.observeAsState("")
    val selectedOptionTM: String by EMViewModel.selectedOptionTM.observeAsState("")
    val isDialogRuta: Boolean by EMViewModel.isDialogRuta.observeAsState(true)
    val isDialogDireccion: Boolean by EMViewModel.isDireccionDialog.observeAsState(initial = false)
    val isDialogDatosPqt: Boolean by EMViewModel.isDatosPQTnDialog.observeAsState(false)
    val isSelectbtn: Boolean by EMViewModel.isSelectbtn.observeAsState(false)
    val isSelectRutaEnabled: Boolean by EMViewModel.isSelectRutaEnabled.observeAsState(false)
    val isSesionDialog: Boolean by EMViewModel.isSesionDialog.observeAsState(false)
    val isLoadingDatGuide: Boolean by EMViewModel.isLoadingDataGuide.observeAsState(false)
    val isDialogLoadGuides: Boolean by EMViewModel.isDialogLoadEnable.observeAsState(false)
    val isGuideRegisted: Boolean by EMViewModel.isGuideRegisted.observeAsState(false)
    val messageGuideValidate: String by EMViewModel.messageGuideValidate.observeAsState("")
    val ruta: String by EMViewModel.ruta.observeAsState("")
    val claveManifest: String by EMViewModel.clavePreManifest.observeAsState("")
    val isLoadBtnEnable: Boolean by EMViewModel.isisLoadBtnEnable.observeAsState(false)
    val listEmployees: List<DataEI> by EMViewModel.listEmployees.observeAsState(initial = emptyList())
    val mapListGuide: Map<String, String> by EMViewModel.mapListGuide.observeAsState(
        mutableMapOf()
    )
    val date: String = getdatenow()
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
    ) { result ->
        if (result.contents != null) {
            Log.i("quide scanner", result.contents)
            EMViewModel.getContentQR(result.contents, navigationController)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (isLoadingDatGuide) {
            ScreenConfirmation(
                Modifier.align(Alignment.Center),
                isGuideRegisted,
                EMViewModel,
                countGuide,
                navigationController
            )
        } else {
            Column() {
                Header(
                    Modifier
                        .weight(0.2f)
                        .background(Color(0xFF4425a7)),
                    EMViewModel,
                    navigationController
                )
                Body(
                    Modifier
                        .weight(2.2f)
                        .padding(horizontal = 8.dp),
                    EMViewModel,
                    mapListGuide,
                    countGuide,
                    ruta,
                    claveManifest,
                    date,
                    nameEmployee,
                    areaEmployee,
                    listEmployees
                )
                Footer(
                    Modifier
                        .weight(0.6f)
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    navigationController,
                    scanLauncher,
                    EMViewModel,
                    isLoadBtnEnable,
                    guia,
                    isSearchEnable
                )
            }
            AlertDialogGuide(
                show = isSesionDialog,
                EMViewModel,
                messageGuideValidate
            )

            dataGuides(
                isDialogDatosPqt,
                isDialogDireccion,
                EMViewModel,
                navigationController,
                nombre,
                telefono,
                dir1, dir2, dir3,
                cp, municipio,
                alto, ancho, largo, pesoKg, pesoVol, typeEmbalaje,
                typeExcedente,
                isSelectbtn,
                isFormDatosPqt,
                isEnableBtnCalcular
            )
            alertDialogHighValue(
                EMViewModel,
                isDialogHighValue
            )
            AlertDialogLoadGuides(
                isDialogLoadGuides,
                EMViewModel,
                messageGuideValidate,
                navigationController
            )
        }
    }
}

@Composable
fun alertDialogHighValue(emViewModel: EMViewModel, isDialogHighValue: Boolean) {
    if(isDialogHighValue){
        AlertDialog(onDismissRequest = {

        },
            title = { Text(text = "Mensaje") },
            text = { Text(text = "¿EL paquete es de aLto valor?") },
            confirmButton = {
                TextButton(onClick = {
                    emViewModel.onHighValuePqt("SI")
                }) {
                    Text(text = "Si")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    emViewModel.onHighValuePqt("NO")
                }) {
                    Text(text = "No")
                }
            }
        )
    }
}

@Composable
fun dataGuides(
    isDialogDatosPqt: Boolean,
    isDialogDireccion: Boolean,
    EMViewModel: EMViewModel,
    navigationController: NavHostController,
    nombre: String,
    telefono: String,
    dir1: String,
    dir2: String,
    dir3: String,
    cp: String,
    municipio: String,
    alto: String,
    ancho: String,
    largo: String,
    pesoKg: String,
    pesoVol: String,
    typeEmbalaje: Boolean,
    typeExcedente: String,
    isSelectbtn: Boolean,
    isFormDatosPqt: Boolean,
    isEnableBtnCalcular: Boolean
) {

    Log.i("Valor de Is DIalog", "$isDialogDireccion")
    if (isDialogDireccion || isDialogDatosPqt) {
        Dialog(onDismissRequest = { }) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(24.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                if (isDialogDireccion) {
                    formDireccion(
                        nombre,
                        telefono,
                        dir1,
                        dir2,
                        dir3,
                        cp,
                        municipio,
                        EMViewModel
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    ButtonsForm(EMViewModel, "direccion", isSelectbtn)
                }

                if (isDialogDatosPqt) {
                    formDatosPqt(
                        alto,
                        ancho,
                        largo,
                        pesoKg,
                        pesoVol,
                        typeEmbalaje,
                        typeExcedente,
                        EMViewModel,
                        isFormDatosPqt,
                        isEnableBtnCalcular
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    ButtonsForm(EMViewModel, "datospqt", isSelectbtn)
                }
            }
        }
    }
}

@Composable
fun formDireccion(
    nombre: String,
    telefono: String,
    dir1: String,
    dir2: String,
    dir3: String,
    cp: String,
    municipio: String,
    EMViewModel: EMViewModel
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = Color(0xFF4425a7),
        text = "Guia"
    )
    Spacer(modifier = Modifier.size(6.dp))
    textFieldNameClient(nombre) {
        EMViewModel.onChangedFormDireccion(
            nombre = it,
            telefono = telefono,
            dir1 = dir1,
            dir2 = dir2,
            dir3 = dir3,
            cp = cp,
            municipio = municipio
        )
    }
    Spacer(modifier = Modifier.size(4.dp))
    textFieldNumberPhone(telefono) {
        EMViewModel.onChangedFormDireccion(
            nombre = nombre,
            telefono = it,
            dir1 = dir1,
            dir2 = dir2,
            dir3 = dir3,
            cp = cp,
            municipio = municipio
        )
    }
    Spacer(modifier = Modifier.size(4.dp))
    textFieldDir1(dir1) {
        EMViewModel.onChangedFormDireccion(
            nombre = nombre,
            telefono = telefono,
            dir1 = it,
            dir2 = dir2,
            dir3 = dir3,
            cp = cp,
            municipio = municipio
        )
    }
    Spacer(modifier = Modifier.size(4.dp))
    textFieldDir2(dir2) {
        EMViewModel.onChangedFormDireccion(
            nombre = nombre,
            telefono = telefono,
            dir1 = dir1,
            dir2 = it,
            dir3 = dir3,
            cp = cp,
            municipio = municipio
        )
    }
    Spacer(modifier = Modifier.size(4.dp))
    textFieldDir3(dir3) {
        EMViewModel.onChangedFormDireccion(
            nombre = nombre,
            telefono = telefono,
            dir1 = dir1,
            dir2 = dir2,
            dir3 = it,
            cp = cp,
            municipio = municipio
        )
    }
    Spacer(modifier = Modifier.size(4.dp))
    textFieldCP(cp) {
        EMViewModel.onChangedFormDireccion(
            nombre = nombre,
            telefono = telefono,
            dir1 = dir1,
            dir2 = dir2,
            dir3 = dir3,
            cp = it,
            municipio = municipio
        )
    }
    Spacer(modifier = Modifier.size(4.dp))
    textFieldMunicipio(municipio) {
        EMViewModel.onChangedFormDireccion(
            nombre = nombre,
            telefono = telefono,
            dir1 = dir1,
            dir2 = dir2,
            dir3 = dir3,
            cp = cp,
            municipio = it
        )
    }
}

@Composable
fun textFieldNameClient(
    nombre: String, onChangedFormDireccion: (String) -> Unit,
//                        onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = nombre,
        onValueChange = { onChangedFormDireccion(it) },
        label = { Text(text = "Nombre") },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
    )
}


@Composable
fun textFieldNumberPhone(
    telefono: (String),
    onChangedFormDireccion: (String) -> Unit
) {
    OutlinedTextField(
        value = telefono,
        onValueChange = { onChangedFormDireccion(it) },
        label = { Text(text = "Telefono") },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
fun textFieldDir1(
    dir1: (String),
    onChangedFormDireccion: (String) -> Unit
) {
    OutlinedTextField(
        value = dir1,
        onValueChange = { onChangedFormDireccion(it) },
        label = { Text(text = "Dirección 1") },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
fun textFieldDir2(
    dir2: (String),
    onChangedFormDireccion: (String) -> Unit
) {
    OutlinedTextField(
        value = dir2,
        onValueChange = { onChangedFormDireccion(it) },
        label = { Text(text = "Dirección 2") },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
fun textFieldDir3(
    dir3: (String),
    onChangedFormDireccion: (String) -> Unit
) {
    OutlinedTextField(
        value = dir3,
        onValueChange = { onChangedFormDireccion(it) },
        label = { Text(text = "Dirección 3") },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
fun textFieldCP(
    cp: (String),
    onChangedFormDireccion: (String) -> Unit
) {
    OutlinedTextField(
        value = cp,
        onValueChange = { onChangedFormDireccion(it) },
        label = { Text(text = "Codigo postal") },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
fun textFieldMunicipio(
    municipio: (String),
    onChangedFormDireccion: (String) -> Unit
) {
    OutlinedTextField(
        value = municipio,
        onValueChange = { onChangedFormDireccion(it) },
        label = { Text(text = "Municipio") },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
fun groupFormDireccion() {

}

@Composable
fun formDatosPqt(
    alto: String,
    ancho: String,
    largo: String,
    pesoKg: String,
    pesoVol: String,
    typeEmbalaje: Boolean,
    typeExcedente: String,
    EMViewModel: EMViewModel,
    isFormDatosPqt: Boolean,
    isEnableBtnCalcular: Boolean
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = Color(0xFF4425a7),
        text = "Peso"
    )
    Spacer(modifier = Modifier.size(6.dp))
    typeEmbalaje(typeEmbalaje, EMViewModel)
    Spacer(modifier = Modifier.size(4.dp))
    textFieldAlto(alto, isFormDatosPqt) {
        EMViewModel.onChangedFormDatos(alto = it, largo = largo, ancho = ancho, pesoKg = pesoKg)
    }
    Spacer(modifier = Modifier.size(4.dp))
    textFieldLargo(largo, isFormDatosPqt) {
        EMViewModel.onChangedFormDatos(alto = alto, largo = it, ancho = ancho, pesoKg = pesoKg)
    }
    Spacer(modifier = Modifier.size(4.dp))
    textFieldAncho(ancho, isFormDatosPqt) {
        EMViewModel.onChangedFormDatos(alto = alto, largo = largo, ancho = it, pesoKg = pesoKg)
    }
    Spacer(modifier = Modifier.size(4.dp))
    textFieldPesoKg(pesoKg, isFormDatosPqt) {
        EMViewModel.onChangedFormDatos(alto = alto, largo = largo, ancho = ancho, pesoKg = it)
    }
    Spacer(modifier = Modifier.size(4.dp))
    infoPesoVolExcedente(pesoVol, isFormDatosPqt, typeExcedente, EMViewModel)
    Spacer(modifier = Modifier.size(6.dp))
    btnCalcularPesoV(isFormDatosPqt, EMViewModel, isEnableBtnCalcular)

}

@Composable
fun btnCalcularPesoV(
    isFormDatosPqt: Boolean,
    EMViewModel: EMViewModel,
    isEnableBtnCalcular: Boolean
) {
    Button(
        onClick = { EMViewModel.calcularPesoVol() },
        enabled = isEnableBtnCalcular,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Calcular peso")
    }
}

@Composable
fun typeEmbalaje(typeEmbalaje: Boolean, EMViewModel: EMViewModel) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(text = "Tipo de embalaje")
    }
    Spacer(modifier = Modifier.size(2.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Chico")
                RadioButton(
                    selected = !typeEmbalaje,
                    onClick = { EMViewModel.onRadioBtnSeleted("Chico") })
            }
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Grande")
                RadioButton(
                    selected = typeEmbalaje,
                    onClick = { EMViewModel.onRadioBtnSeleted("Grande") })
            }
        }
    }
}

@Composable
fun textFieldAlto(
    alto: String, isFormDatosPqt: Boolean,
    onChangedFormDatos: (String) -> Unit
) {
    OutlinedTextField(
        value = alto,
        enabled = isFormDatosPqt,
        onValueChange = { onChangedFormDatos(it) },
        label = { Text(text = "Alto") },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
fun textFieldAncho(
    ancho: String, isFormDatosPqt: Boolean,
    onChangedFormDatos: (String) -> Unit
) {
    OutlinedTextField(
        value = ancho,
        enabled = isFormDatosPqt,
        onValueChange = { onChangedFormDatos(it) },
        label = { Text(text = "Ancho") },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
fun textFieldLargo(
    largo: String, isFormDatosPqt: Boolean,
    onChangedFormDatos: (String) -> Unit
) {
    OutlinedTextField(
        value = largo,
        enabled = isFormDatosPqt,
        onValueChange = { onChangedFormDatos(it) },
        label = { Text(text = "Largo") },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
fun textFieldPesoKg(
    pesoKg: String, isFormDatosPqt: Boolean,
    onChangedFormDatos: (String) -> Unit
) {
    OutlinedTextField(
        value = pesoKg,
        enabled = isFormDatosPqt,
        onValueChange = { onChangedFormDatos(it) },
        label = { Text(text = "Peso Kg") },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
fun infoPesoVolExcedente(
    pesoVol: String,
    isFormDatosPqt: Boolean,
    typeExcedente: String,
    EMViewModel: EMViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.weight(1f)) {
            textFieldPesoVol(pesoVol, isFormDatosPqt) {
                EMViewModel.enableBtnFormDatosPqt(pesoVol = it, typePqt = typeExcedente)
            }
        }
        Spacer(modifier = Modifier.size(2.dp))
        Box(modifier = Modifier.weight(1.5f)) {
            textFieldTypeExcedente(typeExcedente, isFormDatosPqt) {
                EMViewModel.enableBtnFormDatosPqt(pesoVol = pesoVol, typePqt = it)
            }
        }
    }
}

@Composable
fun textFieldPesoVol(
    pesoVol: String,
    isFormDatosPqt: Boolean,
    enableBtnFormDatosPqt: (String) -> Unit
) {
    OutlinedTextField(
        value = pesoVol,
        enabled = isFormDatosPqt,
        readOnly = true,
        onValueChange = { enableBtnFormDatosPqt(it) },
        label = { Text(text = "Peso vol", fontSize = 12.sp) },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
fun textFieldTypeExcedente(
    typeExcedente: String,
    isFormDatosPqt: Boolean,
    enableBtnFormDatosPqt: (String) -> Unit
) {
    OutlinedTextField(
        value = typeExcedente,
        enabled = isFormDatosPqt,
        readOnly = true,
        onValueChange = { enableBtnFormDatosPqt(it) },
        label = { Text(text = "Excendente") },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
fun ScreenConfirmation(
    modifier: Modifier,
    isGuideRegisted: Boolean,
    EMViewModel: EMViewModel,
    countGuide: Int,
    navigationController: NavHostController,
) {
    val countRegisterGuide: Int by EMViewModel.countRegisterGuide.observeAsState(0)
    val progressCircular: Float by EMViewModel.progressCircularLoad.observeAsState(0.1f)
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /*  Icon(
              imageVector = Icons.Rounded.LocalShipping,
              contentDescription = null,
              modifier = Modifier.size(100.dp),
              tint = Color(0xFF4c51c6)
          )*/
        Spacer(modifier = Modifier.size(8.dp))
        if (isGuideRegisted) {
            Incon()
            LaunchedEffect(key1 = 1) {
                delay(2000)
                EMViewModel.guideregistedOk(navigationController)
            }
        } else {
            // Text(text = "Registrando $countRegisterGuide de $countGuide")
            var progress = progressCircular / 100
            LinearProgressIndicator(progress = progress, color = Color(0xFF4c51c6))
            Text(text = "$progressCircular%")
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Registrando $countRegisterGuide de $countGuide",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = modifier.size(4.dp))
                CircularProgressIndicator(
                    modifier = modifier.size(30.dp),
                    color = Color(0xFF4c51c6)
                )
            }
            EMViewModel.loadingOk(countRegisterGuide, countGuide)
        }
    }
}

@Preview
@Composable
fun Incon() {
    Box() {
        Icon(
            imageVector = Icons.Rounded.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = Color(0xFF4c51c6)
        )
    }
}

@Composable
fun AlertDialogGuide(show: Boolean, EMViewModel: EMViewModel, message: String) {
    if (show) {
        AlertDialog(
            onDismissRequest = { EMViewModel.onAlertDialog() },
            title = { Text(text = "Advertencia") },
            text = { Text(text = message) },
            confirmButton = {
                TextButton(onClick = { EMViewModel.onAlertDialog() }) {
                    Text(text = "Cerrar")
                }
            }
        )
    }
}

@Composable
fun Header(modifier: Modifier, EMViewModel: EMViewModel, navigationController: NavHostController) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            Text(
                text = "Manifiesto",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        },
        backgroundColor = Color(0xFF4425a7),
        contentColor = Color.White,
        elevation = 4.dp,
        navigationIcon = {
            IconButton(onClick = { EMViewModel.backScreen(navigationController) }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Cancel,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                )
            }
        }
    )
}

@Composable
fun Body(
    modifier: Modifier,
    EMViewModel: EMViewModel,
    mapListGuide: Map<String, String>,
    countGuide: Int,
    ruta: String,
    claveManifest: String,
    date: String,
    nameEmployee: String,
    areaEmployee: String,
    listEmployees: List<DataEI>
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
    ) {
        Column {
            Row(modifier = Modifier.padding(horizontal = 2.dp)) {
                Box(modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()) {
                    Row() {
                        Text(
                            text = "Nodo:",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                        Text(text = "UPS", fontSize = 14.sp)
                    }
                }
                Box(
                    modifier = modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Text(
                        text = date,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }

            }

            DataManifest(
                Modifier.padding(bottom = 2.dp),
                ruta,
                claveManifest,
                nameEmployee,
                areaEmployee,
                EMViewModel,
                listEmployees
            )
            ListGuide(
                Modifier
                    .weight(3f),
                EMViewModel, mapListGuide, countGuide
            )
            FooterTable(countGuide)
        }
    }
}

@Composable
fun DataManifest(
    modifier: Modifier,
    ruta: String,
    claveManifest: String,
    nameEmployee: String,
    areaEmployee: String,
    EMViewModel: EMViewModel,
    listEmployees: List<DataEI>
) {
    Box(
        modifier = modifier.fillMaxWidth()
//        .background(Color(0xFFcfd9fb))
    ) {

        Card(
            modifier = modifier
                .fillMaxWidth()
            // .background(Color(0xFFcfd9fb))
            ,
            border = BorderStroke(2.dp, Color(0xFF4425a7)),
            backgroundColor = Color(0xFFcfd9fb)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ClaveManifest(ruta, claveManifest)
                Spacer(modifier = Modifier.size(5.dp))
                nameOperator(nameEmployee, areaEmployee, EMViewModel, listEmployees)
//                Spacer(modifier = Modifier.size(5.dp))
//                RutaAndLogo(data)
//                Spacer(modifier = Modifier.size(5.dp))
//                DateTime(data)
//                Spacer(modifier = Modifier.size(5.dp))
//                ClientName(data[2])
//                Spacer(modifier = Modifier.size(5.dp))
//                telefono(data[4])
//                Spacer(modifier = Modifier.size(5.dp))
//                Direccion(data[3])
            }
        }
    }
}

@Composable
fun nameOperator(
    nameEmployee: String,
    areaEmployee: String,
    EMViewModel: EMViewModel,
    listEmployees: List<DataEI>
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        // Spacer(modifier = Modifier.size(4.dp))
        Box(modifier = Modifier.weight(1f)) {
            if (areaEmployee.equals("Operador Logistico")) {
                NameOPTextField(nameEmployee)
            } else {
                SelectOPTextField(nameEmployee, listEmployees) {
                    EMViewModel.onValueChangeEmployee(
                        name = it
                    )
                }
            }
        }
//        Spacer(modifier = Modifier.size(4.dp))
//        Box(
//            modifier = Modifier
//                .weight(1f)
//            //.background(Color(0xFFf9f9f9)),
//        ) {
//           LogoEmpresa(data[7], Modifier.align(Alignment.Center))
//        }
    }
}

@Composable
fun ClaveManifest(ruta: String, claveManifest: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        // Spacer(modifier = Modifier.size(4.dp))
        Box(modifier = Modifier.weight(1.5f)) {
            ClavePMTextField(claveManifest)
        }
        Spacer(modifier = Modifier.size(4.dp))
        Box(
            modifier = Modifier
                .weight(0.8f)
            //.background(Color(0xFFf9f9f9)),
        ) {
            RutaTextField(ruta)
        }
    }
}

@Composable
fun NameOPTextField(nameEmployee: String) {
    Column() {
        TextLabelNameOp()
        BasicTextField(
            value = nameEmployee,
            readOnly = true,
            onValueChange = {
            },
            textStyle = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray
            ),
            maxLines = 1,
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 2.dp), // inner padding,
                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun SelectOPTextField(
    nameEmployee: String,
    listEmployees: List<DataEI>,
    onTextChanged: (FieldDataEI) -> Unit
) {
    var expand by remember { mutableStateOf(false) }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    val employees = remember {
        mutableStateListOf(
            "Luis Angel Cervantes Hernandez",
            "Lazaro Saul Hernandez Leyva",
            "Jose Luis Jimenez Mejia",
            "Diego ALberto COrtes Garcia",

            )
    }

    Column() {
        TextLabelNameOp()
        BasicTextField(
            modifier = Modifier
                .clickable { expand = true }
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFiledSize = coordinates.size.toSize()
                },
            value = nameEmployee,
            enabled = false,
            readOnly = true,
            onValueChange = {
            },
            textStyle = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 2.dp), // inner padding,
                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
        Box() {
            DropdownMenu(
                expanded = expand,
                onDismissRequest = { expand = false },
                modifier = Modifier
                    .height(200.dp)
                    .width(with(LocalDensity.current) { textFiledSize.width.toDp() })
            ) {
                Log.i("lista de empleados", employees.toString())
                listEmployees.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            expand = false
                            onTextChanged(option.fieldData!!)
                        }
                    ) {
                        val name = option.fieldData
                        Text(text = "${name?.nombre} ${name?.aPaterno} ${name?.aMaterno}")
                    }
                }
            }
        }
    }
}

@Composable
fun ClavePMTextField(claveManifest: String) {
    Column() {
        TextLabelClavePM()
        BasicTextField(
            value = claveManifest,
            readOnly = true,
            onValueChange = {
            },
            textStyle = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray
            ),
            maxLines = 1,
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(all = 4.dp), // inner padding,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun RutaTextField(ruta: String) {
    Column() {
        TextLabelEmpresa()
        BasicTextField(
            value = ruta,
            readOnly = true,
            onValueChange = {
            },
            textStyle = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray
            ),
            maxLines = 1,
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(all = 4.dp), // inner padding,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun TextLabelNameOp() {
    Text(text = "Empleado", fontWeight = FontWeight.Bold, fontSize = 12.sp)
}

@Composable
fun TextLabelClavePM() {
    Text(text = "Pre-folio", fontWeight = FontWeight.Bold, fontSize = 12.sp)
}

@Composable
fun TextLabelEmpresa() {
    Text(text = "Ruta", fontWeight = FontWeight.Bold, fontSize = 12.sp)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListGuide(
    modifier: Modifier,
    EMViewModel: EMViewModel,
    mapListGuide: Map<String, String>,
    countGuide: Int,
//    qrcontent: String
) {
    val ListGuide = listOf(
        "1Z0437452832J899771",
        "1Z0437452832J899772",
        "1Z0437452832J899773",
        "1Z0437452832J899774",
        "1Z0437452832J899775",
        "1Z0437452832J899776",
        "1Z0437452832J899777",
        "1Z0437452832J899778",
        "1Z0437452832J899779",
        "1Z0437452832J899710",
        "1Z0437452832J899711",
        "1Z0437452832J899712",
        "1Z0437452832J899713",
        "1Z0437452832J899714",
        "1Z0437452832J899715",
        "1Z0437452832J899716",
        "1Z0437452832J899717",
    )
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        //contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        stickyHeader {
            HeadTable()

        }
        items(mapListGuide.toList()) {
            Card(
                modifier.fillMaxWidth(),
                // border = BorderStroke(1.dp, Color(0xFF4425a7))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(horizontal = 4.dp)
                ) {
                    Box(modifier = Modifier.weight(2f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowRight,
                                contentDescription = null,
                                modifier = modifier
                                    .size(30.dp)
                                    .padding(0.dp),
                                tint = Color(0xFF4425a7)
                            )
                            Text(
                                text = it.first,
                                //modifier =modifier.padding(horizontal = 4.dp),
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        }
                    }

//                    Box(
//                        Modifier
//                            .fillMaxWidth()
//                            .weight(1.5f)
////                            .padding(start = 8.dp)
//                        ,
//                        contentAlignment = Alignment.Center
//                    ) {
//                        IconButton(
//                            onClick = {
//                                EMViewModel.onRemoveguideList(it.first, it.second)
//                            },
//                            modifier = modifier
//                        ) {
//                            Icon(
//                                imageVector = Icons.Rounded.Delete,
//                                contentDescription = null,
//                                modifier = modifier.size(30.dp),
//                                tint = Color.Red
//                            )
//                        }
//                    }
                }
            }
        }

    }
}

@Composable
fun HeadTable() {
    Card(
        modifier = Modifier,
        backgroundColor = Color(0xFFcfd9fb),
        border = BorderStroke(1.dp, Color(0xFF4425a7)),
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Guia", fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Acciones", fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.size(2.dp))
        }
    }
}

@Composable
fun FooterTable(countGuide: Int) {
    Card(
        modifier = Modifier,
        backgroundColor = Color.White,
        border = BorderStroke(1.dp, Color.White),
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .padding(start = 6.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Total guias: $countGuide", fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
//            Box(modifier = Modifier
//                .weight(1.5f)
//                .fillMaxWidth(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(text = "Acciones",fontWeight = FontWeight.SemiBold
//                )
//            }
//            Spacer(modifier = Modifier.size(2.dp))
        }
    }

}

@Composable
fun Footer(
    modifier: Modifier,
    navigationController: NavHostController,
    scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>,
    EMViewModel: EMViewModel,
    isLoadBtnEnable: Boolean,
    guia: String,
    isSearchEnable: Boolean
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column() {
            val StyleBox = (Modifier
                // .weight(1f)
                .height(55.dp))
            groupBtnInsertGuide(
                EMViewModel,
                navigationController,
                scanLauncher,
                StyleBox,
                guia,
                isSearchEnable
            )
            //ButtonScanner(StyleBox, cmViewModel, navigationController, scanLauncher)
            Spacer(modifier = Modifier.size(8.dp))
            ButtonLoadServer(
                StyleBox,
                isLoadBtnEnable,
                EMViewModel,
                navigationController
            )

        }
    }
}

@Composable
fun groupBtnInsertGuide(
    EMViewModel: EMViewModel,
    navigationController: NavHostController,
    scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>,
    StyleBox: Modifier,
    guia: String,
    isSearchEnable: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
            // .background(Color.Green),
            contentAlignment = Alignment.Center
        ) {
            TextFieldGuia(
                guia,
                isSearchEnable,
                EMViewModel,
                navigationController,
                StyleBox, scanLauncher
            ) {
                EMViewModel.onSearchChanged(guia = it)
            }
        }
    }
}

@Composable
fun TextFieldGuia(
    guia: String,
    isSearchEnable: Boolean,
    EMViewModel: EMViewModel,
    navigationController: NavHostController,
    modifier: Modifier,
    scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .height(63.dp)
            .fillMaxWidth(),
        value = guia,
        onValueChange = { onTextChanged(it) },
        label = {
            Text(
                text = "No. de guia",
                fontSize = 12.sp
            )
        },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
        trailingIcon = {

            Row() {
                Button(
                    enabled = isSearchEnable,
                    onClick = { EMViewModel.getContentQR(guia, navigationController) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(96, 127, 243),
                        contentColor = Color.White,
                        disabledBackgroundColor = Color.White,
                        disabledContentColor = Color(0xFF91a6f3)
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        //Text(text = "Agregar")
                        Icon(
                            imageVector = Icons.Rounded.AddCircle,
                            contentDescription = null,
                            modifier = Modifier
                                .size(35.dp),
                            // tint = Color(0xFF4425a7)
                        )
                    }

                }

                ButtonScanner(EMViewModel, navigationController, scanLauncher)
            }
        },
    )
}

@Composable
fun ButtonScanner(
    //modifier: Modifier,
    EMViewModel: EMViewModel,
    navigationController: NavHostController,
    scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>
) {
    Button(
        //modifier = modifier.fillMaxWidth(),
        onClick = { EMViewModel.initScanner(scanLauncher = scanLauncher) },
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(96, 127, 243),
            contentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.QrCodeScanner,
                contentDescription = null,
                modifier = Modifier
                    // .weight(1f)
                    .size(35.dp),
                //tint = Color.White
            )
            //  Text(
            //    text = "Abrir Scanner",
            //   fontSize = 14.sp,
            // modifier = Modifier.weight(2f)
            // )
        }
    }
}

@Composable
fun ButtonLoadServer(
    modifier: Modifier,
    isLoadBtnEnable: Boolean,
    rdViewModel: EMViewModel,
    navigationController: NavHostController
) {

    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = { rdViewModel.onDialogLoadGuides() },
        enabled = isLoadBtnEnable,
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(96, 127, 243),
            contentColor = Color.White,
            disabledBackgroundColor = Color(0xFF91a6f3),
            disabledContentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
            //   horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Rounded.CloudUpload,
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .size(40.dp),
                tint = Color.White
            )
            Text(
                text = "Actualizar",
                fontSize = 14.sp,
                modifier = Modifier.weight(2f),
            )
        }
    }
}



@Composable
fun DromMenuTM(
    selectedOption: String,
    EMViewModel: EMViewModel,
    listRutas: List<String>,
    onTextChanged: (String) -> Unit
) {
    var expand by remember { mutableStateOf(false) }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }
    OutlinedTextField(
        value = selectedOption,
        onValueChange = { },
        enabled = false,
        readOnly = true,
        modifier = Modifier
            .clickable { expand = true }
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                textFiledSize = coordinates.size.toSize()
            },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
    )
    Box() {
        DropdownMenu(
            expanded = expand,
            onDismissRequest = { expand = false },
            modifier = Modifier
                .height(200.dp)
                .width(with(LocalDensity.current) { textFiledSize.width.toDp() })
        ) {
            Log.i("lista dedevoluciones", listRutas.toString())
            listRutas.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        expand = false
                        onTextChanged(option)
                    }
                ) {
                    Text(text = option)
                }
            }
        }
    }
}

@Composable
fun ButtonsForm(
    EMViewModel: EMViewModel, typeForm: String, isSelectbtn: Boolean
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        TextButton(
            onClick = { EMViewModel.onContinueForm(typeForm) },
            enabled = isSelectbtn
        ) {
            Text(text = "Continuar")
        }
    }
}

@Composable
fun AlertDialogLoadGuides(
    show: Boolean,
    EMViewModel: EMViewModel,
    message: String,
    navigationController: NavHostController
) {
    if (show) {
        AlertDialog(
            onDismissRequest = { EMViewModel.onAlertDialog() },
            title = { Text(text = "Advertencia") },
            text = { Text(text = message) },
            confirmButton = {
                TextButton(onClick = { EMViewModel.create(navigationController) }) {
                    Text(text = "Continuar")
                }
            },
            dismissButton = {
                TextButton(onClick = { EMViewModel.onAlertDialog() }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}
