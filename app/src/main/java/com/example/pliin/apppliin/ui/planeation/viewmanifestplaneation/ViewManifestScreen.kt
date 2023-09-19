package com.example.pliin.apppliin.ui.planeation.viewmanifestplaneation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.Data
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.FieldData
import kotlinx.coroutines.delay


@Composable
fun ViewManifestScreen(navigationController: NavHostController, vmfViewModel: VMFViewModel) {

    val isLoadingP: String by vmfViewModel.isLoadingPlaneation.observeAsState("Loading")
    val listManifest: List<Data> by vmfViewModel.listManifest.observeAsState(listOf())
    val claveManifest:String by vmfViewModel.claveManifest.observeAsState("")
    val enableLoadManifest: Boolean by vmfViewModel.enableLoadManifest.observeAsState(true)
    val optionsDialog: Boolean by vmfViewModel.optionsDialog.observeAsState(false)

//    if (enableLoadManifest) {
//
//    }

    Box() {
        Column() {
            Header(
                Modifier
                    .weight(0.2f)
                    .background(Color(0xFF4425a7)), navigationController,
                vmfViewModel
            )
            when(isLoadingP){
                "Loading" -> {
                    LoadingPlaneation(
                        Modifier
                            .weight(2.8f),
                        vmfViewModel
                    )
                    LaunchedEffect(key1 = true) {
                        delay(2000)
                        vmfViewModel.loadManifest()
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
                            .padding(horizontal = 2.dp), listManifest,vmfViewModel
                    )
                    Footer(
                        Modifier
                            .weight(0.6f)
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }

    dialogOptions(optionsDialog,vmfViewModel,claveManifest,navigationController)

}

@Composable
fun LoadingPlaneation(modifier: Modifier, vmfViewModel: VMFViewModel) {
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
fun Header(modifier: Modifier, navigationController: NavHostController, mfViewModel: VMFViewModel) {
    TopAppBar(modifier = modifier.fillMaxWidth(),
        title = { Text(text = "Manifiestos")},
        backgroundColor = Color(0xFF4425a7),
        contentColor = Color.White,
        elevation = 4.dp,
        navigationIcon ={
            IconButton(onClick = { mfViewModel.navigate(navigationController) }) {
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
fun Body(modifier: Modifier, listManifest: List<Data>, mfViewModel: VMFViewModel) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column() {
            manifestList(modifier.weight(3f), listManifest,mfViewModel)
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun manifestList(
    modifier: Modifier,
    listManifest: List<Data>,
    mfViewModel: VMFViewModel,
    //cmViewModel: CMViewModel,
    // mapListGuide: Map<String, String>,
//    countGuide: Int,
//    qrcontent: String
) {
    val listmanifest = listOf(
        "LCA20230605UPS999",
        "LCA20230604UPS888",
        "LCA20230603UPS777",

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
        items(listManifest) {
            itemManifest(modifier = modifier, manifest = it.fieldData!!,mfViewModel,it)
        }
    }
}

@Composable
fun itemManifest(modifier: Modifier, manifest: FieldData, mfViewModel: VMFViewModel, data: Data) {
    Card(
        Modifier
            .fillMaxWidth()
            .clickable {
                mfViewModel.clickManifest(
                    "${manifest.clavePrincipal}",
                    "${data.recordId}",
                    "${manifest.nombreOperador}",
                    "${manifest.ruta}",
                    "${manifest.totaolGuias}"
                )
                Log.i("Clic", "Le di clic al texto  ${manifest.clavePrincipal}")
            },
        // border = BorderStroke(1.dp, Color(0xFF4425a7))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(horizontal = 4.dp, vertical = 16.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
//                    .height(100.dp)
                    .weight(0.1f)
                    .background(Color(0xFF4425a7))
//                            .padding(start = 8.dp)
                , contentAlignment = Alignment.Center
            ) {
               /* Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color(0xFF4425a7))
//                            .padding(start = 8.dp)
                )*/
              /*  Text(
                    text = "*",
                    color= Color.White,
                    //modifier =modifier.padding(horizontal = 4.dp),
                    fontWeight = FontWeight.Normal, fontSize = 16.sp
                )*/

            }
            Box(
                modifier = Modifier.weight(2f)
            ) {
                Column() {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                       /* Icon(
                            imageVector = Icons.Rounded.ArrowRight,
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .padding(0.dp),
                            tint = Color(0xFF4425a7)
                        )*/
                        Text(
                            text = "${manifest.clavePrincipal}",
                            //modifier =modifier.padding(horizontal = 4.dp),
                            fontWeight = FontWeight.Normal, fontSize = 16.sp
                        )
                    }
                    Box(
                        Modifier
                            .fillMaxWidth()
//                            .padding(start = 8.dp)
                        , contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${manifest.ruta}",
                            //modifier =modifier.padding(horizontal = 4.dp),
                            fontWeight = FontWeight.Normal, fontSize = 16.sp
                        )
                    }
                    Box(
                        Modifier
                            .fillMaxWidth()
//                            .padding(start = 8.dp)
                        , contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${manifest.fecha}",
                            //modifier =modifier.padding(horizontal = 4.dp),
                            fontWeight = FontWeight.Normal, fontSize = 16.sp
                        )
                    }
                }
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
//                            .padding(start = 8.dp)
                , contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${manifest.totalpqt}",
                    //modifier =modifier.padding(horizontal = 4.dp),
                    fontWeight = FontWeight.Normal, fontSize = 16.sp
                )
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
            modifier = Modifier.padding(vertical = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Manifiesto", fontWeight = FontWeight.SemiBold
                )
            }
         /*   Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Fecha", fontWeight = FontWeight.SemiBold
                )
            }*/
            Spacer(modifier = Modifier.size(2.dp))
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Guias", fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun dialogOptions(
    optionsDialog: Boolean,
    mfViewModel: VMFViewModel,
    claveManifest: String,
    navigationController: NavHostController
) {
    if (optionsDialog){
        Dialog(onDismissRequest = { mfViewModel.onOptionDialog()}) {
            Column(modifier= Modifier
                .background(Color.White)
                .padding(20.dp)
                .fillMaxWidth()
                .height(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = claveManifest,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = Color(0xFF4425a7),
                )
                Divider()
                Spacer(modifier = Modifier.size(8.dp))
               /* Button(onClick = { *//*TODO*//* },
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(text = "Ver")
                }*/
                Spacer(modifier =Modifier.size(2.dp))
                Button(onClick = { mfViewModel.viewEditManifest(navigationController)},
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(text = "Ver")
                }
                Spacer(modifier =Modifier.size(8.dp))
                Box(modifier = Modifier.align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.BottomEnd) {
                    TextButton(onClick = { mfViewModel.onOptionDialog()},
                    ) {
                        Text(text = "Cerrar")
                    }
                }

            }
        }
    }
}


@Composable
fun Footer(padding: Modifier) {
}