package com.example.pliin.apppliin.ui.guides.receptionguide

import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pliin.R
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.delay


@Composable
fun ReceptionGuideScreen(
    rgViewModel: RGViewModel,
    navigationController: NavHostController,
    functionScreen: String
) {
    val mapListGuide: Map<String, String> by rgViewModel.mapListGuide.observeAsState(
        mutableMapOf()
    )
    val progressCircular: Float by rgViewModel.progressCircularLoad.observeAsState(0f)
    val countGuide: Int by rgViewModel.countGuides.observeAsState(0)
    val isSesionDialog: Boolean by rgViewModel.isSesionDialog.observeAsState(false)
    val isDialogLoadGuides: Boolean by rgViewModel.isDialogLoadEnable.observeAsState(false)
    val isLoadingDataGuide: Boolean by rgViewModel.isLoadingDataGuide.observeAsState(false)
    val isGuideRegisted: Boolean by rgViewModel.isGuideRegisted.observeAsState(false)
    val qrcontent: String by rgViewModel.contentQR.observeAsState("")
    val guia: String by rgViewModel.guia.observeAsState("")
    val messageGuideValidate: String by rgViewModel.messageGuideValidate.observeAsState("")
    val isSearchEnable: Boolean by rgViewModel.isSearchEnable.observeAsState(false)
    val isLoadBtnEnable: Boolean by rgViewModel.isisLoadBtnEnable.observeAsState(false)
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),

        ) { result ->
        if (result.contents != null) {
            Log.i("quide scanner", "${result.contents}")
            rgViewModel.getContentQR(result.contents, navigationController)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        if (isLoadingDataGuide) {
            ScreenConfirmation(
                Modifier.align(
                    Alignment.Center
                ),
                isGuideRegisted,
                rgViewModel,
                countGuide,
            )
        } else {
            Header(
                Modifier
                    .align(Alignment.TopCenter)
                    .background(Color(0xFF4425a7)), navigationController,
                rgViewModel, functionScreen
            )
            Body(
                Modifier
                    .align(Alignment.Center)
                // .padding(horizontal = 8.dp)
                ,
                rgViewModel,
                navigationController,
                guia,
                isSearchEnable,
                scanLauncher,
                mapListGuide,
                countGuide,
                qrcontent
            )
            Footer(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 8.dp, vertical = 5.dp),
                rgViewModel,
                navigationController,
                guia,
                isSearchEnable,
                scanLauncher,
                isLoadBtnEnable
            )

            AlertDialogGuide(
                show = isSesionDialog,
                rgViewModel,
                messageGuideValidate
            )
            AlertDialogLoadGuides(
                show = isDialogLoadGuides,
                rgViewModel,
                messageGuideValidate, guia, navigationController
            )
            //textqr(rdViewModel,Modifier.align(Alignment.BottomCenter),qrcontent)
        }
    }
}

@Composable
fun ScreenConfirmation(
    modifier: Modifier,
    isGuideRegisted: Boolean,
    rgViewModel: RGViewModel,
    countGuide: Int,
) {
    val countRegisterGuide: Int by rgViewModel.countRegisterGuide.observeAsState(0)
    val progressCircular: Float by rgViewModel.progressCircularLoad.observeAsState(0.1f)
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
                rgViewModel.guideregistedOk()
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
            rgViewModel.loadingOk(countRegisterGuide, countGuide)
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
fun Header(
    modifier: Modifier,
    navigationController: NavHostController,
    rdViewModel: RGViewModel,
    functionScreen: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            HeadButton(
                modifier = modifier
                    .weight(1f)
                    .padding(end = 8.dp), navigationController,
                rdViewModel
            )
            HeadText(
                modifier = modifier
                    .weight(3f)
                    .padding(end = 55.dp),
                functionScreen
            )
        }
    }
}

@Composable
fun HeadText(modifier: Modifier, functionScreen: String) {
    Text(
        modifier = modifier.padding(vertical = 16.dp),
        text = functionScreen,
        fontSize = 30.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    )
}

@Composable
fun HeadButton(
    modifier: Modifier,
    navigationController: NavHostController,
    rgViewModel: RGViewModel
) {
    IconButton(onClick = { rgViewModel.navigation(navigationController) }, modifier = modifier) {
        Icon(
            imageVector = Icons.Rounded.TurnLeft,
            contentDescription = null,
            modifier = modifier.size(50.dp),
            tint = Color.White
        )
    }
}

@Composable
fun Body(
    modifier: Modifier,
    rdViewModel: RGViewModel,
    navigationController: NavHostController,
    guia: String,
    isSearchEnable: Boolean,
    scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>,
    mapListGuide: Map<String, String>,
    countGuide: Int,
    qrcontent: String
) {
    Column(modifier.padding(bottom = 130.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        /*  Box(modifier = modifier.weight(1f)) {
              //  BodyLogo()
          }*/
        CardHead(modifier)
        Spacer(modifier = modifier.size(4.dp))
        Card(
            //modifier = modifier.weight(1f)
        ) {
            ListGuide(modifier, mapListGuide, rdViewModel, countGuide, qrcontent)
        }
        Spacer(modifier = modifier.size(4.dp))
        //  TotalGuias(countGuide, qrcontent)


    }
}

@Composable
fun CardHead(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .weight(1f)
            //.background(Color.LightGray)
            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Lista de guias",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
        Divider(modifier = modifier.fillMaxWidth(), color = Color(0xFF4425a7))

    }
}

@Composable
fun TotalGuias(countGuide: Int, guia: String) {
    Row(modifier = Modifier.padding(bottom = 6.dp)) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Guias: $countGuide", fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.size(2.dp))
        Box(modifier = Modifier.weight(3f)) {
            Text(text = "Guia escaneada: $guia")
        }


    }
}

@Composable
fun Footer(
    modifier: Modifier,
    rgViewModel: RGViewModel,
    navigationController: NavHostController,
    guia: String,
    isSearchEnable: Boolean,
    scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>,
    isLoadBtnEnable: Boolean
) {
    Box(modifier = modifier.padding(top = 4.dp)) {
        Column() {
            TexfieldButon(guia, rgViewModel, modifier, isSearchEnable, navigationController)
            GroupButton(
                modifier.fillMaxWidth(),
                isSearchEnable,
                rgViewModel,
                navigationController,
                guia,
                scanLauncher,
                isLoadBtnEnable
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListGuide(
    modifier: Modifier,
    mapListGuide: Map<String, String>,
    rdViewModel: RGViewModel,
    countGuide: Int,
    qrcontent: String
) {
    val listguides = listOf(
        "1Z0437452832J899778",

        )
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .height(480.dp),
        //contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        stickyHeader {
            TotalGuias(countGuide, qrcontent)
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

                    Box(modifier = modifier.weight(2f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowRight,
                                contentDescription = null,
                                modifier = modifier.size(50.dp),
                                tint = Color(0xFF4425a7)
                            )
                            Spacer(modifier = modifier.size(1.dp))
                            Text(
                                text = "${it.second}",
                                //modifier =modifier.padding(horizontal = 4.dp),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        }
                    }

                    Box(
                        modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        IconButton(
                            onClick = { rdViewModel.onRemoveguideList(it.first, it.second) },
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
fun BodyLogo() {
    Image(
        painter = painterResource(id = R.drawable.salter_logo_02),
        contentDescription = "SALTER",
        Modifier.size(150.dp)
    )
}

@Composable
fun TexfieldButon(
    guia: String,
    rgViewModel: RGViewModel,
    modifier: Modifier,
    isSearchEnable: Boolean,
    navigationController: NavHostController
) {
    Box() {
        Row() {
            TextFieldGuia(guia, isSearchEnable, rgViewModel, navigationController) {
                rgViewModel.onSearchChanged(guia = it)
            }

            // ButtonAddGuide(modifier,isSearchEnable,guia,rgViewModel,navigationController)

        }

    }
}

@Composable
fun TextFieldGuia(
    guia: String, isSearchEnable: Boolean,
    rdViewModel: RGViewModel,
    navigationController: NavHostController, onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = guia,
        onValueChange = { onTextChanged(it) },
        label = { Text(text = "Ingrese la guia") },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
        trailingIcon = {

            Button(
                enabled = isSearchEnable,
                onClick = { rdViewModel.getContentQR(guia, navigationController) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(96, 127, 243),
                    contentColor = Color.White,
                    disabledBackgroundColor = Color(0xFF91a6f3),
                    disabledContentColor = Color.White
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Agregar")
                    Icon(
                        imageVector = Icons.Rounded.AddCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp),
                        // tint = Color(0xFF4425a7)
                    )
                }

            }
        },
    )

}

@Composable
fun GroupButton(
    modifier: Modifier,
    isSearchEnable: Boolean,
    rdViewModel: RGViewModel,
    navigationController: NavHostController,
    guia: String,
    scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>,
    isLoadBtnEnable: Boolean
) {
    Column(modifier = modifier.padding(top = 10.dp)) {
        val StyleBox = (Modifier
            // .weight(1f)
            .height(70.dp))
        ButtonScanner(StyleBox, rdViewModel, scanLauncher)
        Spacer(modifier = Modifier.size(8.dp))
//        ButtonLoadServer(StyleBox, isLoadBtnEnable, guia, rdViewModel, navigationController)

    }
}

@Composable
fun ButtonAddGuide(
    modifier: Modifier,
    isSearchEnable: Boolean,
    guia: String,
    rdViewModel: RGViewModel,
    navigationController: NavHostController
) {
    Button(
        modifier = modifier,
        onClick = { rdViewModel.getContentQR(guia, navigationController) },
        enabled = isSearchEnable,
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(96, 127, 243),
            contentColor = Color.White,
            disabledBackgroundColor = Color(0xFF91a6f3),
            disabledContentColor = Color.White
        )
    ) {

        Icon(
            imageVector = Icons.Rounded.AddCircle,
            contentDescription = null,
            modifier = Modifier
                .weight(1.5f)
                .size(10.dp),
            tint = Color.White
        )
    }
}

@Composable
fun ButtonLoadServer(
    modifier: Modifier,
    isLoadBtnEnable: Boolean,
    guia: String,
    rdViewModel: RGViewModel,
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
fun ButtonScanner(
    modifier: Modifier,
    rdViewModel: RGViewModel,
    scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = { rdViewModel.initScanner(scanLauncher = scanLauncher) },
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
fun AlertDialogGuide(show: Boolean, loginViewModel: RGViewModel, message: String) {
    if (show) {
        AlertDialog(onDismissRequest = { loginViewModel.onAlertDialog() },
            title = { Text(text = "Advertencia") },
            text = { Text(text = message) },
            confirmButton = {
                TextButton(onClick = { loginViewModel.onAlertDialog() }) {
                    Text(text = "Cerrar")
                }
            }
        )
    }
}

@Composable
fun AlertDialogLoadGuides(
    show: Boolean,
    rdViewModel: RGViewModel,
    message: String,
    guia: String,
    navigationController: NavHostController
) {
    if (show) {
        AlertDialog(onDismissRequest = { rdViewModel.onAlertDialog() },
            title = { Text(text = "Advertencia") },
            text = { Text(text = message) },
            confirmButton = {
                TextButton(onClick = { rdViewModel.LoadGuideServer(guia, navigationController) }) {
                    Text(text = "Continuar")
                }
            },
            dismissButton = {
                TextButton(onClick = { rdViewModel.onAlertDialog() }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}

@Composable
fun textqr(rdViewModel: RGViewModel, Modifier: Modifier, qrcontent: String) {
    Box(
        modifier = Modifier.padding(bottom = 100.dp),
    ) {
        Text(text = "Contenido del QR ${qrcontent}")
    }
}