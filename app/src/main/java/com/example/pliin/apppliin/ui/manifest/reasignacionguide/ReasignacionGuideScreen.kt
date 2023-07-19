package com.example.pliin.apppliin.ui.manifest.reasignacionguide

import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pliin.R
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun ReasignacionGuideScreen(navigationController: NavHostController,raViewModel: RAViewModel = hiltViewModel()) {
    //raViewModel.reset()
    val isSesionDialog: Boolean by raViewModel.isSesionDialog.observeAsState(false)
    val isLoadingDataGuide: Boolean by raViewModel.isLoadingDataGuide.observeAsState(false)
    val qrcontent: String by raViewModel.contentQR.observeAsState("")
    val guia: String by raViewModel.guia.observeAsState("")
    val messageGuideValidate: String by raViewModel.messageGuideValidate.observeAsState("")
    val isSearchEnable: Boolean by raViewModel.isSearchEnable.observeAsState(false)
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
    ) { result ->
        if (result.contents != null) {
            Log.i("quide scanner", "${result.contents}")
            raViewModel.getContentQR(result.contents, navigationController)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        if (isLoadingDataGuide) {
            ScreenConfirmation(
                Modifier.align(
                    Alignment.Center
                )
            )
        } else {
            Header(
                Modifier
                    .align(Alignment.TopCenter)
                    .background(Color(0xFF4425a7)), navigationController,
                raViewModel
            )
            Body(
                Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 8.dp),
                raViewModel,
                navigationController,
                guia,
                isSearchEnable,
                scanLauncher
            )

            AlertDialogGuide(show = isSesionDialog, raViewModel, messageGuideValidate)
            //textqr(rdViewModel,Modifier.align(Alignment.BottomCenter),qrcontent)
        }
    }
}

@Composable
fun ScreenConfirmation(
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Rounded.LocalShipping,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = Color(0xFF4c51c6)
        )
        Spacer(modifier = Modifier.size(8.dp))
        CircularProgressIndicator(color = Color(0xFF4c51c6), strokeWidth = 4.dp)
    }
}

@Composable
fun Header(modifier: Modifier, navigationController: NavHostController, raViewModel: RAViewModel) {
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
                raViewModel
            )
            HeadText(
                modifier = modifier
                    .weight(3f)
                    .padding(end = 55.dp)
            )
        }
    }
}

@Composable
fun HeadText(modifier: Modifier) {
    Text(
        modifier = modifier.padding(vertical = 16.dp),
        text = "Reasignar guia",
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    )
}

@Composable
fun HeadButton(
    modifier: Modifier,
    navigationController: NavHostController,
    raViewModel: RAViewModel
) {
    IconButton(onClick = { raViewModel.navigation(navigationController) }, modifier = modifier) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = null,
            modifier = modifier.size(50.dp),
            tint = Color.White
        )
    }
}

@Composable
fun Body(
    modifier: Modifier,
    raViewModel: RAViewModel,
    navigationController: NavHostController,
    guia: String,
    isSearchEnable: Boolean,
    scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>
) {
    Column(modifier.padding(bottom = 80.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        BodyLogo()
        TextFieldGuia(guia) {
            raViewModel.onSearchChanged(guia = it)
        }
        GroupButton(
            modifier.fillMaxWidth(),
            isSearchEnable,
            raViewModel,
            navigationController,
            guia,
            scanLauncher
        )
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
fun  TextFieldGuia(guia: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value =guia,
        onValueChange = { onTextChanged(it)},
        label = { Text(text = "Ingrese la guia") },
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
fun GroupButton(
    modifier: Modifier,
    isSearchEnable: Boolean,
    raViewModel: RAViewModel,
    navigationController: NavHostController,
    guia: String,
    scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>
) {

    Row(modifier = modifier.padding(top = 10.dp)) {
        val StyleBox = (Modifier
            .weight(1f)
            .height(70.dp))
        ButtonSearch(StyleBox, isSearchEnable, guia, raViewModel, navigationController)
        Spacer(modifier = Modifier.size(8.dp))
        ButtonScanner(StyleBox, raViewModel, navigationController, guia, scanLauncher)
    }
}

@Composable
fun ButtonSearch(
    modifier: Modifier,
    isSearchEnable: Boolean,
    guia: String,
    raViewModel: RAViewModel,
    navigationController: NavHostController
) {

    Button(
        modifier = modifier,
        onClick = { raViewModel.getContentQR(guia, navigationController) },
        enabled = isSearchEnable,
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(96, 127, 243),
            contentColor = Color.White,
            disabledBackgroundColor = Color(0xFF91a6f3),
            disabledContentColor = Color.White
        )
    ) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                modifier = Modifier
                    .weight(1.5f)
                    .size(60.dp),
                tint = Color.White
            )
            Text(
                text = "Buscar",
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
fun ButtonScanner(
    modifier: Modifier,
    raViewModel: RAViewModel,
    navigationController: NavHostController,
    guia: String,
    scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>
) {
    /*
        val scanLauncher = rememberLauncherForActivityResult(
            contract = ScanContract(),
        ) { result ->
            if (result.contents != null) {
                Log.i("quide scanner","${result.contents}")
                rdViewModel.getContentQR(result.contents,navigationController)
            }
        }*/
    Button(
        modifier = modifier,
        onClick = { raViewModel.initScanner(scanLauncher = scanLauncher) },
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(96, 127, 243),
            contentColor = Color.White
        )
    ) {
        Column(verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Rounded.QrCodeScanner,
                contentDescription = null,
                modifier = Modifier
                    .weight(1.5f)
                    .size(62.dp),
                tint = Color.White
            )
            Text(
                text = "Escanear",
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun AlertDialogGuide(show: Boolean, raViewModel: RAViewModel, message: String) {
    if (show) {
        AlertDialog(onDismissRequest = { raViewModel.onAlertDialog() },
            title = { Text(text = "Advertencia") },
            text = { Text(text = message) },
            confirmButton = {
                TextButton(onClick = { raViewModel.onAlertDialog() }) {
                    Text(text = "Cerrar")
                }
            }
        )
    }
}

@Composable
fun textqr(raViewModel: RAViewModel, Modifier: Modifier, qrcontent: String) {
    Box(modifier = Modifier.padding(bottom = 100.dp),
    ){
        Text(text = "Contenido del QR ${qrcontent}")
    }
}