package com.example.pliin.apppliin.ui.planeationday

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.CloudDownload
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

//@Preview(showSystemUi = true)
@Composable
fun PlaneationDayScreen(navigationController: NavHostController, pdViewModel: PDViewModel) {
    val isLoadingP: String by pdViewModel.isLoadingPlaneation.observeAsState("Loading")

    val folioManifest: String by pdViewModel.folioManifest.observeAsState("")
    val rutaManifest: String by pdViewModel.rutaManifest.observeAsState("")
    val totalGuides: String by pdViewModel.totalGuides.observeAsState("")
    val statusManifest: String by pdViewModel.statusManifest.observeAsState("")

    Box() {
        Column {
            Header(
                Modifier
                    .weight(0.2f)
                    .background(Color(0xFF4425a7)),
                navigationController,
                pdViewModel
            )
            when (isLoadingP) {

                "Loading" -> {
                    LoadingPlaneation(
                        Modifier
                            .weight(2.8f),
                        pdViewModel
                    )
                    LaunchedEffect(key1 = true) {
                        delay(2000)
                        pdViewModel.LoadManifestPlaneation()
                    }
                }

                "NoFound" -> {
                    NoFoundPlaneation(
                        Modifier
                            .weight(2.8f)
                    )
                }
                else -> {
                    Body(
                        Modifier
                            .weight(2.2f)
                            .padding(horizontal = 2.dp, vertical = 4.dp),
                        folioManifest,
                        rutaManifest,
                        totalGuides,
                        statusManifest
                    )
                    Footer(
                        Modifier
                            .weight(0.6f)
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        pdViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingPlaneation(modifier: Modifier, pdViewModel: PDViewModel) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()

    }
}

@Composable
fun NoFoundPlaneation(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(text = "No se ha encontrado una planeacion ",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(96, 127, 243),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun Header(modifier: Modifier, navigationController: NavHostController, pdViewModel: PDViewModel) {
    TopAppBar(modifier = modifier.fillMaxWidth(),
        title = { Text(text = "Planeación") },
        backgroundColor = Color(0xFF4425a7),
        contentColor = Color.White,
        elevation = 4.dp,
        navigationIcon = {
            IconButton(onClick = { pdViewModel.navigation(navigationController)}) {
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
    folioManifest: String,
    rutaManifest: String,
    totalGuides: String,
    statusManifest: String
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Card(
            modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            border = BorderStroke(2.dp, Color.White),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                FolioManifest(folioManifest)
                Spacer(modifier = Modifier.size(8.dp))
                RutaManifest(rutaManifest)
                Spacer(modifier = Modifier.size(8.dp))
                InfoManifest(totalGuides)
                Spacer(modifier = Modifier.size(8.dp))
                /*StatusManifest()
                Spacer(modifier = Modifier.size(8.dp))*/
            }
        }
    }
}

@Composable
fun FolioManifest(folioManifest: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(96, 127, 243),
            contentColor = Color.White
        ) {
            Text(
                text = "Manifiesto",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }
        Box() {
            Text(
                text = folioManifest,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Divider(color = Color.LightGray)
    }
}

@Composable
fun RutaManifest(rutaManifest: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(96, 127, 243),
            contentColor = Color.White
        ) {
            Text(
                text = "Ruta",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }
        Box() {
            Text(
                text = rutaManifest,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Divider(color = Color.LightGray)
    }
}

@Composable
fun InfoManifest(totalGuides: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(96, 127, 243),
            contentColor = Color.White
        ) {
            Text(
                text = "Guias asignadas",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }
        Box() {
            Text(
                text = totalGuides,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Divider(color = Color.LightGray)
    }
}

@Composable
fun StatusManifest() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(96, 127, 243),
            contentColor = Color.White
        ) {
            Text(
                text = "Estado",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }
        Box() {
            Text(
                text = "APLICADO",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Divider(color = Color.LightGray)
    }
}

@Composable
fun Footer(modifier: Modifier, pdViewModel: PDViewModel) {
    val styleBoxBtn = (Modifier
        // .weight(1f)
        .height(55.dp))
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {
            BtnAplicarManifest(styleBoxBtn)
            Spacer(modifier = Modifier.size(4.dp))
            BtnFinalizarManifest(styleBoxBtn)
        }
    }
}


@Composable
fun BtnFinalizarManifest(modifier: Modifier) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = { },
        enabled = true,
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
                imageVector = Icons.Rounded.Done,
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .size(40.dp),
                tint = Color.White
            )
            Text(
                text = "Finalizar",
                fontSize = 14.sp,
                modifier = Modifier.weight(2f),
            )
        }
    }
}

@Composable
fun BtnAplicarManifest(modifier: Modifier) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = { },
        enabled = true,
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
                imageVector = Icons.Rounded.CloudDownload,
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .size(40.dp),
                tint = Color.White
            )
            Text(
                text = "Aplicar planeación",
                fontSize = 14.sp,
                modifier = Modifier.weight(2f),
            )
        }
    }
}

