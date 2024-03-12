package com.example.pliin.apppliin.ui.manifest.validatemanifest

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
import com.example.pliin.apppliin.domain.model.GuideItem
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
fun ValidateManifestScreen(
    navigationController: NavHostController,
    nameEmploye: String,
    idRecord:String,
    route:String,
    idPrem:String,
    VMViewModel: VMViewModel = hiltViewModel()
) {
    val isLoadGuideManifest: Boolean by VMViewModel.isLoadGuideManifest.observeAsState(true)
    val isDialogExitScreen: Boolean by VMViewModel.isAlertDialogexit.observeAsState(false)
    val openEditManifest: Boolean by VMViewModel.openViewEditManifest.observeAsState(false)

    if (isLoadGuideManifest){
        VMViewModel.loadData(nameEmploye,idRecord,route,idPrem)
    }

    if (openEditManifest) VMViewModel.reloadGuides()




    val nombre: String by VMViewModel.nombre.observeAsState("")
    val telefono: String by VMViewModel.telefono.observeAsState("")
    val dir1: String by VMViewModel.dir1.observeAsState("")
    val dir2: String by VMViewModel.dir2.observeAsState("")
    val dir3: String by VMViewModel.dir3.observeAsState("")
    val cp: String by VMViewModel.cp.observeAsState("")
    val municipio: String by VMViewModel.municipio.observeAsState("")

    val alto: String by VMViewModel.alto.observeAsState("")
    val ancho: String by VMViewModel.ancho.observeAsState("")
    val largo: String by VMViewModel.largo.observeAsState("")
    val pesoKg: String by VMViewModel.pesoKg.observeAsState("")
    val pesoVol: String by VMViewModel.pesoVol.observeAsState("")
    val typeEmbalaje: Boolean by VMViewModel.typeEmbalaje.observeAsState(false)
    val isEnableBtnCalcular: Boolean by VMViewModel.isenableBtnCalcular.observeAsState(false)
    val typeExcedente: String by VMViewModel.typePaq.observeAsState("")
    val isFormDatosPqt: Boolean by VMViewModel.isFormDatosPqt.observeAsState(false)



    val isDialogHighValue: Boolean by VMViewModel.isAlertDialogHighValue.observeAsState(false)
    val isSearchEnable: Boolean by VMViewModel.isSearchEnable.observeAsState(false)
    val progressCircular: Float by VMViewModel.progressCircularLoad.observeAsState(0f)
    val guia: String by VMViewModel.guia.observeAsState("")
    val countGuide: Int by VMViewModel.countGuides.observeAsState(0)
    val countRegisterGuide: Int by VMViewModel.countRegisterGuide.observeAsState(0)

    val selectedOptionRuta: String by VMViewModel.selectedOptionRuta.observeAsState("")
    val nameEmployee: String by VMViewModel.nameEmployye.observeAsState("")
    val areaEmployee: String by VMViewModel.areaEmployye.observeAsState("")
    val selectedOptionTM: String by VMViewModel.selectedOptionTM.observeAsState("")
    val isDialogRuta: Boolean by VMViewModel.isDialogRuta.observeAsState(true)
    val isDialogDireccion: Boolean by VMViewModel.isDireccionDialog.observeAsState(initial = false)
    val isDialogDatosPqt: Boolean by VMViewModel.isDatosPQTnDialog.observeAsState(false)
    val isSelectbtn: Boolean by VMViewModel.isSelectbtn.observeAsState(false)
    val isSelectRutaEnabled: Boolean by VMViewModel.isSelectRutaEnabled.observeAsState(false)
    val isSesionDialog: Boolean by VMViewModel.isSesionDialog.observeAsState(false)
    val isLoadingDatGuide: Boolean by VMViewModel.isLoadingDataGuide.observeAsState(false)
    val isDialogLoadGuides: Boolean by VMViewModel.isDialogLoadEnable.observeAsState(false)
    val isGuideRegisted: Boolean by VMViewModel.isGuideRegisted.observeAsState(false)
    val messageGuideValidate: String by VMViewModel.messageGuideValidate.observeAsState("")
    val ruta: String by VMViewModel.ruta.observeAsState("")
    val claveManifest: String by VMViewModel.clavePreManifest.observeAsState("")
    val isLoadBtnEnable: Boolean by VMViewModel.isisLoadBtnEnable.observeAsState(false)
    val listEmployees: List<DataEI> by VMViewModel.listEmployees.observeAsState(initial = emptyList())
    val mapListGuide: Map<String, GuideItem> by VMViewModel.mapListGuide.observeAsState(
        mutableMapOf()
    )
    val date: String = getdatenow()
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
    ) { result ->
        if (result.contents != null) {
            Log.i("quide scanner", result.contents)
            VMViewModel.getContentQR(result.contents, navigationController)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (isLoadingDatGuide) {
            ScreenConfirmation(
                Modifier.align(Alignment.Center),
                isGuideRegisted,
                VMViewModel,
                countGuide,
                navigationController
            )
        } else {
            Column() {
                Header(
                    Modifier
                        .weight(0.2f)
                        .background(Color(0xFF4425a7)),
                    VMViewModel,
                    navigationController
                )
                Body(
                    Modifier
                        .weight(2.2f)
                        .padding(horizontal = 8.dp),
                    VMViewModel,
                    mapListGuide,
                    countGuide,
                    ruta,
                    claveManifest,
                    date,
                    nameEmployee,
                    areaEmployee,
                    listEmployees,
                    countRegisterGuide
                )
                Footer(
                    Modifier
                        .weight(0.6f)
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    navigationController,
                    scanLauncher,
                    VMViewModel,
                    isLoadBtnEnable,
                    guia,
                    isSearchEnable
                )
            }
            AlertDialogGuide(
                show = isSesionDialog,
                VMViewModel,
                messageGuideValidate,
                navigationController
            )

            dataGuides(
                isDialogDatosPqt,
                isDialogDireccion,
                VMViewModel,
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
                VMViewModel,
                isDialogHighValue
            )
            AlertDialogLoadGuides(
                isDialogLoadGuides,
                VMViewModel,
                messageGuideValidate,
                navigationController
            )
        }
    }
}

@Composable
fun alertDialogHighValue(VMViewModel: VMViewModel, isDialogHighValue: Boolean) {
    if(isDialogHighValue){
        AlertDialog(onDismissRequest = {

        },
            title = { Text(text = "Mensaje") },
            text = { Text(text = "¿EL paquete es de aLto valor?") },
            confirmButton = {
                TextButton(onClick = {
                    VMViewModel.onHighValuePqt("SI")
                }) {
                    Text(text = "Si")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    VMViewModel.onHighValuePqt("NO")
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
    VMViewModel: VMViewModel,
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
                        VMViewModel
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    ButtonsForm(VMViewModel, "direccion", isSelectbtn)
                }

                if (isDialogDatosPqt) {

                    Spacer(modifier = Modifier.size(4.dp))
                    ButtonsForm(VMViewModel, "datospqt", isSelectbtn)
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
    VMViewModel: VMViewModel
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = Color(0xFF4425a7),
        text = "Guia"
    )

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
fun btnCalcularPesoV(
    isFormDatosPqt: Boolean,
    VMViewModel: VMViewModel,
    isEnableBtnCalcular: Boolean
) {
    Button(
        onClick = { },
        enabled = isEnableBtnCalcular,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Calcular peso")
    }
}

@Composable
fun typeEmbalaje(typeEmbalaje: Boolean, VMViewModel: VMViewModel) {
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

            }
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

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
    VMViewModel: VMViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.weight(1f)) {
            textFieldPesoVol(pesoVol, isFormDatosPqt) {
                VMViewModel.enableBtnFormDatosPqt(pesoVol = it, typePqt = typeExcedente)
            }
        }
        Spacer(modifier = Modifier.size(2.dp))
        Box(modifier = Modifier.weight(1.5f)) {
            textFieldTypeExcedente(typeExcedente, isFormDatosPqt) {
                VMViewModel.enableBtnFormDatosPqt(pesoVol = pesoVol, typePqt = it)
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
    VMViewModel: VMViewModel,
    countGuide: Int,
    navigationController: NavHostController,
) {
    val countRegisterGuide: Int by VMViewModel.countRegisterGuide.observeAsState(0)
    val progressCircular: Float by VMViewModel.progressCircularLoad.observeAsState(0.1f)
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
                VMViewModel.guideregistedOk(navigationController)
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
fun AlertDialogGuide(
    show: Boolean,
    VMViewModel: VMViewModel,
    message: String,
    navigationController: NavHostController
) {
    if (show) {
        AlertDialog(
            onDismissRequest = { VMViewModel.onAlertDialog() },
            title = { Text(text = "Advertencia") },
            text = { TextDialog(message)},
            confirmButton = {
                TextButton(onClick = { VMViewModel.addGuideManifest(navigationController) }) {
                    Text(text = "Si")
                }
            },
            dismissButton = { TextButton(onClick = { VMViewModel.onAlertDialog()}) {
                Text(text = "Cerrar")

            }}
        )
    }
}

@Composable
fun TextDialog(message: String) {
    Column {
        Text(text = message)
        Text(text = "¿Quiere agregar esta guia al manifiesto?",
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Header(modifier: Modifier, VMViewModel: VMViewModel, navigationController: NavHostController) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            Text(
                text = "Validar manifiesto",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        },
        backgroundColor = Color(0xFF4425a7),
        contentColor = Color.White,
        elevation = 4.dp,
        navigationIcon = {
            IconButton(onClick = { VMViewModel.backScreen(navigationController) }
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
    VMViewModel: VMViewModel,
    mapListGuide: Map<String, GuideItem>,
    countGuide: Int,
    ruta: String,
    claveManifest: String,
    date: String,
    nameEmployee: String,
    areaEmployee: String,
    listEmployees: List<DataEI>,
    countRegisterGuide: Int
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
                VMViewModel,
                listEmployees
            )
            ListGuide(
                Modifier
                    .weight(3f),
                VMViewModel, mapListGuide, countGuide
            )
            FooterTable(countGuide,countRegisterGuide)
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
    VMViewModel: VMViewModel,
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
                nameOperator(nameEmployee, areaEmployee, VMViewModel, listEmployees)
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
    VMViewModel: VMViewModel,
    listEmployees: List<DataEI>
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        // Spacer(modifier = Modifier.size(4.dp))
        Box(modifier = Modifier.weight(1f)) {
            if (areaEmployee.equals("Operador Logistico")) {
                NameOPTextField(nameEmployee)
            } else {
                SelectOPTextField(nameEmployee, listEmployees) {
                    VMViewModel.onValueChangeEmployee(
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
    Text(text = "Folio", fontWeight = FontWeight.Bold, fontSize = 12.sp)
}

@Composable
fun TextLabelEmpresa() {
    Text(text = "Ruta", fontWeight = FontWeight.Bold, fontSize = 12.sp)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListGuide(
    modifier: Modifier,
    VMViewModel: VMViewModel,
    mapListGuide: Map<String, GuideItem>,
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
                backgroundColor = Color(0xFFEBF5FB)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,

                    modifier = modifier.padding(horizontal = 4.dp)
                ){
                    Box(modifier = Modifier.weight(2f)) {
                        Row(horizontalArrangement = Arrangement.Start) {
                            Box(modifier  = Modifier.weight(0.1f)){
                                Icon(
                                    imageVector = Icons.Rounded.ArrowRight,
                                    contentDescription = null,
                                    modifier = modifier
                                        .size(25.dp)
                                        .padding(0.dp),
                                    tint = Color(0xFF4425a7)
                                )
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = it.second.idGuia.toString(),
                                    //modifier =modifier.padding(horizontal = 4.dp),
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .weight(1.5f)
//                            .padding(start = 8.dp)
                        ,
                        contentAlignment = Alignment.Center
                    ) {

                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null,
                                modifier = modifier.size(30.dp),
                                tint = Color(it.second.color!!)
                            )

                    }
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
                    text = "Revision", fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.size(2.dp))
        }
    }
}

@Composable
fun FooterTable(countGuide: Int, countRegisterGuide: Int) {
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
                    text = "Guias verificadas: $countRegisterGuide de $countGuide", fontWeight = FontWeight.SemiBold,
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
    VMViewModel: VMViewModel,
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
                VMViewModel,
                navigationController,
                scanLauncher,
                StyleBox,
                guia,
                isSearchEnable
            )
            //ButtonScanner(StyleBox, cmViewModel, navigationController, scanLauncher)
          /*  Spacer(modifier = Modifier.size(8.dp))
            ButtonLoadServer(
                StyleBox,
                isLoadBtnEnable,
                VMViewModel,
                navigationController
            )*/

        }
    }
}

@Composable
fun groupBtnInsertGuide(
    VMViewModel: VMViewModel,
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
                VMViewModel,
                navigationController,
                StyleBox, scanLauncher
            ) {
                VMViewModel.onSearchChanged(guia = it)
            }
        }
    }
}

@Composable
fun TextFieldGuia(
    guia: String,
    isSearchEnable: Boolean,
    VMViewModel: VMViewModel,
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
                    onClick = { VMViewModel.getContentQR(guia, navigationController) },
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

                ButtonScanner(VMViewModel, navigationController, scanLauncher)
            }
        },
    )
}

@Composable
fun ButtonScanner(
    //modifier: Modifier,
    VMViewModel: VMViewModel,
    navigationController: NavHostController,
    scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>
) {
    Button(
        //modifier = modifier.fillMaxWidth(),
        onClick = { VMViewModel.initScanner(scanLauncher = scanLauncher) },
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
    rdViewModel: VMViewModel,
    navigationController: NavHostController
) {

    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = { },
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
    VMViewModel: VMViewModel,
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
    VMViewModel: VMViewModel, typeForm: String, isSelectbtn: Boolean
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        TextButton(
            onClick = { },
            enabled = isSelectbtn
        ) {
            Text(text = "Continuar")
        }
    }
}

@Composable
fun AlertDialogLoadGuides(
    show: Boolean,
    VMViewModel: VMViewModel,
    message: String,
    navigationController: NavHostController
) {
    if (show) {
        AlertDialog(
            onDismissRequest = { VMViewModel.onAlertDialog() },
            title = { Text(text = "Advertencia") },
            text = { Text(text = message) },
            confirmButton = {
                TextButton(onClick = {  }) {
                    Text(text = "Continuar")
                }
            },
            dismissButton = {
                TextButton(onClick = { VMViewModel.onAlertDialog() }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}
