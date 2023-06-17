package com.example.pliin.apppliin.ui.manifest.createmanifest

import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.delay
import java.time.LocalDate


fun getdatenow(): String {
    return LocalDate.now().toString()
}

@Composable
fun CreateManifestScreen(cmViewModel: CMViewModel, navigationController: NavHostController) {
    val progressCircular: Float by cmViewModel.progressCircularLoad.observeAsState(0f)
    val countGuide: Int by cmViewModel.countGuides.observeAsState(0)
    val selectedOption: String by cmViewModel.selectedOption.observeAsState(" ")
    val isDialogRuta: Boolean by cmViewModel.isDialogRuta.observeAsState(true)
    val isSelectbtn: Boolean by cmViewModel.isSelectbtn.observeAsState(false)
    val isSesionDialog: Boolean by cmViewModel.isSesionDialog.observeAsState(false)
    val isLoadingDatGuide: Boolean by cmViewModel.isLoadingDataGuide.observeAsState(false)
    val isDialogLoadGuides: Boolean by cmViewModel.isDialogLoadEnable.observeAsState(false)
    val isGuideRegisted: Boolean by cmViewModel.isGuideRegisted.observeAsState(false)
    val messageGuideValidate: String by cmViewModel.messageGuideValidate.observeAsState("")
    val ruta: String by cmViewModel.ruta.observeAsState("")
    val claveManifest: String by cmViewModel.clavePreManifest.observeAsState("")
    val isLoadBtnEnable: Boolean by cmViewModel.isisLoadBtnEnable.observeAsState(false)
    val mapListGuide: Map<String, String> by cmViewModel.mapListGuide.observeAsState(
        mutableMapOf()
    )
    val date: String = getdatenow()
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
    ) { result ->
        if (result.contents != null) {
            Log.i("quide scanner", result.contents)
            cmViewModel.getContentQR(result.contents, navigationController)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (isLoadingDatGuide) {
            ScreenConfirmation(
                Modifier.align(Alignment.Center),
                isGuideRegisted,
                cmViewModel,
                countGuide,
                navigationController
            )
        } else {
            Column() {
                Header(
                    Modifier
                        .weight(0.2f)
                        .background(Color(0xFF4425a7)),
                    cmViewModel,
                    navigationController
                )
                Body(
                    Modifier
                        .weight(2.2f)
                        .padding(horizontal = 8.dp),
                    cmViewModel, mapListGuide, countGuide, ruta, claveManifest, date
                )
                Footer(
                    Modifier
                        .weight(0.6f)
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    navigationController, scanLauncher, cmViewModel, isLoadBtnEnable
                )
            }
            AlertDialogGuide(
                show = isSesionDialog,
                cmViewModel,
                messageGuideValidate
            )
            selectRuta(cmViewModel, selectedOption, navigationController, isDialogRuta, isSelectbtn)

            AlertDialogLoadGuides(
                show = isDialogLoadGuides,
                cmViewModel,
                messageGuideValidate,
                navigationController
            )
        }
    }
}

@Composable
fun ScreenConfirmation(
    modifier: Modifier,
    isGuideRegisted: Boolean,
    cmViewModel: CMViewModel,
    countGuide: Int,
    navigationController: NavHostController,
) {
    val countRegisterGuide: Int by cmViewModel.countRegisterGuide.observeAsState(0)
    val progressCircular: Float by cmViewModel.progressCircularLoad.observeAsState(0.1f)
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
                cmViewModel.guideregistedOk(navigationController)
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
            cmViewModel.loadingOk(countRegisterGuide, countGuide)
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
fun AlertDialogGuide(show: Boolean, cmViewModel: CMViewModel, message: String) {
    if (show) {
        AlertDialog(
            onDismissRequest = { cmViewModel.onAlertDialog() },
            title = { Text(text = "Advertencia") },
            text = { Text(text = message) },
            confirmButton = {
                TextButton(onClick = { cmViewModel.onAlertDialog() }) {
                    Text(text = "Cerrar")
                }
            }
        )
    }
}

@Composable
fun Header(modifier: Modifier, cmViewModel: CMViewModel, navigationController: NavHostController) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = { Text(text = "Manifiesto") },
        backgroundColor = Color(0xFF4425a7),
        contentColor = Color.White,
        elevation = 4.dp,
        navigationIcon = {
            IconButton(onClick = { cmViewModel.backScreen(navigationController) }
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
    cmViewModel: CMViewModel,
    mapListGuide: Map<String, String>,
    countGuide: Int,
    ruta: String,
    claveManifest: String,
    date: String
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
                            fontSize = 18.sp
                        )
                        Text(text = "UPS", fontSize = 18.sp)
                    }
                }
                Box(
                    modifier = modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Text(text = date)
                }

            }

            DataManifest(
                Modifier.padding(bottom = 2.dp), ruta, claveManifest
            )
            ListGuide(
                Modifier
                    .weight(3f),
                cmViewModel, mapListGuide, countGuide
            )
            FooterTable(countGuide)
        }
    }
}

@Composable
fun DataManifest(modifier: Modifier, ruta: String, claveManifest: String) {
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
                nameOperator(listOf())
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
fun nameOperator(data: List<String>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        // Spacer(modifier = Modifier.size(4.dp))
        Box(modifier = Modifier.weight(1f)) {
            NameOPTextField("Luis Angel Cervantes Hernandez")
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
        Box(modifier = Modifier.weight(1.3f)) {
            ClavePMTextField(claveManifest)
        }
        Spacer(modifier = Modifier.size(4.dp))
        Box(
            modifier = Modifier
                .weight(1f)
            //.background(Color(0xFFf9f9f9)),
        ) {
            EmpresaTextField(ruta)
        }
    }
}

@Composable
fun NameOPTextField(guia: String) {
    Column() {
        TextLabelNameOp()
        BasicTextField(
            value = guia,
            readOnly = true,
            onValueChange = {
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
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
    }
}

@Composable
fun ClavePMTextField(claveManifest: String) {
    Column() {
        TextLabelClavePM()
        BasicTextField(
            value = "$claveManifest-XXX",
            readOnly = true,
            onValueChange = {
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
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
fun EmpresaTextField(ruta: String) {
    Column() {
        TextLabelEmpresa()
        BasicTextField(
            value = ruta,
            readOnly = true,
            onValueChange = {
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
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
    Text(text = "Empleado", fontWeight = FontWeight.Bold)
}

@Composable
fun TextLabelClavePM() {
    Text(text = "Pre-folio", fontWeight = FontWeight.Bold)
}

@Composable
fun TextLabelEmpresa() {
    Text(text = "Ruta", fontWeight = FontWeight.Bold)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListGuide(
    modifier: Modifier,
    cmViewModel: CMViewModel,
    mapListGuide: Map<String, String>,
    countGuide: Int,
//    qrcontent: String
) {
    val listguides = listOf(
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
                                text = "${it.second}",
                                //modifier =modifier.padding(horizontal = 4.dp),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
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
                        IconButton(
                            onClick = {
                                cmViewModel.onRemoveguideList(it.first, it.second)
                            },
                            modifier = modifier
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = null,
                                modifier = modifier.size(30.dp),
                                tint = Color.Red
                            )
                        }
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
                    text = "Guia", fontWeight = FontWeight.SemiBold
                )
            }
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Acciones", fontWeight = FontWeight.SemiBold
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
                    text = "Total guias: $countGuide", fontWeight = FontWeight.SemiBold
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
    cmViewModel: CMViewModel,
    isLoadBtnEnable: Boolean
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column() {
            val StyleBox = (Modifier
                // .weight(1f)
                .height(70.dp))
            ButtonScanner(StyleBox, cmViewModel, navigationController, scanLauncher)
            Spacer(modifier = Modifier.size(8.dp))
            ButtonLoadServer(
                StyleBox,
                isLoadBtnEnable,
                cmViewModel,
                navigationController
            )

        }
    }
}

@Composable
fun ButtonScanner(
    modifier: Modifier,
    cmViewModel: CMViewModel,
    navigationController: NavHostController,
    scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = { cmViewModel.initScanner(scanLauncher = scanLauncher) },
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
                    .weight(1f)
                    .size(62.dp),
                tint = Color.White
            )
            Text(
                text = "Abrir Scanner",
                fontSize = 20.sp,
                modifier = Modifier.weight(2f)
            )
        }
    }
}

@Composable
fun ButtonLoadServer(
    modifier: Modifier,
    isLoadBtnEnable: Boolean,
    rdViewModel: CMViewModel,
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
                    .size(60.dp),
                tint = Color.White
            )
            Text(
                text = "Registrar en sistema",
                fontSize = 20.sp,
                modifier = Modifier.weight(2f),
            )
        }
    }
}


@Composable
fun selectRuta(
    cmViewModel: CMViewModel,
    selectedOption: String,
    navigationController: NavHostController,
    isDialogRuta: Boolean,
    isSelectbtn: Boolean
) {
    if (isDialogRuta) {
        Dialog(onDismissRequest = { }) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(24.dp)
                    .fillMaxWidth()
                    .height(350.dp)
            ) {

                ConfirmarEntregaDialog(
                    cmViewModel,
                    selectedOption
                )
                Spacer(modifier = Modifier.size(4.dp))
                ButtonsConfirmation(cmViewModel, navigationController, isSelectbtn)
            }
        }
    }
}

@Composable
fun ConfirmarEntregaDialog(
    dgsViewModel: CMViewModel,
    selectedOption: String,
) {

    val items = remember {
        mutableStateListOf(
            "Zihuatanejo",
            "Ixtapa",
            "Petatlan"
        )
    }

    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = Color(0xFF4425a7),
        text = "MANIFIESTO"
    )
    Spacer(modifier = Modifier.size(16.dp))
    Text(text = "Ruta")
    DromMenu(selectedOption, dgsViewModel, items) { dgsViewModel.onValueChanged(selected = it) }
    Spacer(modifier = Modifier.size(14.dp))

    // Text(text = "")
    // Recibe(parents) { dgsViewModel.onValueChangedRecibe(nameparent = it) }

}

@Composable
fun DromMenu(
    selectedOption: String,
    dgsViewModel: CMViewModel,
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
fun ButtonsConfirmation(
    cmViewModel: CMViewModel,
    navigationController: NavHostController,
    isSelectbtn: Boolean,
) {
    Row() {
        TextButton(onClick = { cmViewModel.backScreen(navigationController) }) {
            Text(text = "Cancelar")
        }
        TextButton(
            onClick = { cmViewModel.continueSetGuides() },
            enabled = isSelectbtn
        ) {
            Text(text = "Continuar")
        }
    }
}

@Composable
fun AlertDialogLoadGuides(
    show: Boolean,
    cmViewModel: CMViewModel,
    message: String,
    navigationController: NavHostController
) {
    if (show) {
        AlertDialog(onDismissRequest = { cmViewModel.onAlertDialog() },
            title = { Text(text = "Advertencia") },
            text = { Text(text = message) },
            confirmButton = {
                TextButton(onClick = { cmViewModel.create(navigationController) }) {
                    Text(text = "Continuar")
                }
            },
            dismissButton = {
                TextButton(onClick = { cmViewModel.onAlertDialog() }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}
