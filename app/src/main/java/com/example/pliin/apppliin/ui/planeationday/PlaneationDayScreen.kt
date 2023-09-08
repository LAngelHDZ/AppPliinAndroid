package com.example.pliin.apppliin.ui.planeationday

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.CloudDownload
import androidx.compose.material.icons.rounded.Delete
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
import com.example.pliin.apppliin.domain.model.GuideItem
import com.example.pliin.apppliin.ui.manifest.editmanifest.HeadTable
import kotlinx.coroutines.delay

//@Preview(showSystemUi = true)
@Composable
fun PlaneationDayScreen(navigationController: NavHostController, pdViewModel: PDViewModel) {
    val isLoadingP: String by pdViewModel.isLoadingPlaneation.observeAsState("Loading")

    val folioManifest: String by pdViewModel.folioManifest.observeAsState("")
    val rutaManifest: String by pdViewModel.rutaManifest.observeAsState("")
    val totalGuides: String by pdViewModel.totalGuides.observeAsState("")
    val statusManifest: String by pdViewModel.statusManifest.observeAsState("")

    val listGuides:List<GuideItem> by pdViewModel.listGuide.observeAsState(emptyList())

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
                        statusManifest,
                        listGuides
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
    statusManifest: String,
    listGuides: List<GuideItem>
) {
    Box(modifier = modifier.fillMaxWidth()) {

        Column {
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
            ListGuide( Modifier
                .weight(3f),
                listGuides)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListGuide(
    modifier: Modifier,
    listGuides: List<GuideItem>,
//    EMViewModel: EMViewModel,
//    mapListGuide: Map<String, String>,
//    countGuide: Int,
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
        items(listGuides) { item ->
            Card(
                modifier.fillMaxWidth(),
                // border = BorderStroke(1.dp, Color(0xFF4425a7))
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(horizontal = 4.dp)
                ){
                    Box(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Icon(
//                                imageVector = Icons.Rounded.ArrowRight,
//                                contentDescription = null,
//                                modifier = modifier
//                                    .size(15.dp),
//                                tint = Color(0xFF4425a7)
//                            )
                            Text(
                                text = "${item.idGuia}",
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
////                                EMViewModel.onRemoveguideList(it.first, it.second)
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
            BtnAplicarManifest(styleBoxBtn,pdViewModel)
            Spacer(modifier = Modifier.size(4.dp))
            BtnFinalizarManifest(styleBoxBtn,pdViewModel)
        }
    }
}


@Composable
fun BtnFinalizarManifest(modifier: Modifier, pdViewModel: PDViewModel) {
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
fun BtnAplicarManifest(modifier: Modifier, pdViewModel: PDViewModel) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = {pdViewModel.downloadManifest() },
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

