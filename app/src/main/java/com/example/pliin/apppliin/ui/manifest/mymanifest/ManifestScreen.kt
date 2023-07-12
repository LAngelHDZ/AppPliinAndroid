package com.example.pliin.apppliin.ui.manifest.mymanifest

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.material.icons.rounded.Assignment
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.Data
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.FieldData


@Composable
fun ManifestScreen(navigationController: NavHostController, mfViewModel: MFViewModel) {


    val listManifest: List<Data> by mfViewModel.listManifest.observeAsState(listOf())
    val enableLoadManifest: Boolean by mfViewModel.enableLoadManifest.observeAsState(true)

    if (enableLoadManifest) {
        mfViewModel.loadManifest()
    }

    Box() {
        Column() {
            Header(
                Modifier
                    .weight(0.2f)
                    .background(Color(0xFF4425a7)), navigationController
            )
            Body(
                Modifier
                    .weight(2.2f)
                    .padding(horizontal = 2.dp), listManifest
            )
            Footer(
                Modifier
                    .weight(0.6f)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            )
        }
    }

}

@Composable
fun Header(modifier: Modifier, navigationController: NavHostController) {
    TopAppBar(modifier = modifier.fillMaxWidth(),
        title = { Text(text = "Manifiestos") },
        backgroundColor = Color(0xFF4425a7),
        contentColor = Color.White,
        elevation = 4.dp,
        navigationIcon = {
            IconButton(onClick = { navigationController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Rounded.Cancel,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                )
            }
        })
}

@Composable
fun Body(modifier: Modifier, listManifest: List<Data>) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column() {
            manifestList(modifier.weight(3f), listManifest)
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun manifestList(
    modifier: Modifier,
    listManifest: List<Data>,
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
            itemManifest(modifier = modifier, manifest = it.fieldData!!)
        }
    }
}

@Composable
fun itemManifest(modifier: Modifier, manifest: FieldData) {
    Card(
        Modifier
            .fillMaxWidth()
            .clickable {
                Log.i("Clic", "Le di clic al texto  ${manifest.clavePrincipal}")
            },
        // border = BorderStroke(1.dp, Color(0xFF4425a7))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(horizontal = 4.dp, vertical = 16.dp)
        ) {
            Box(
                modifier = Modifier.weight(2f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowRight,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(0.dp),
                        tint = Color(0xFF4425a7)
                    )
                    Text(
                        text = "${manifest.clavePrincipal}",
                        //modifier =modifier.padding(horizontal = 4.dp),
                        fontWeight = FontWeight.Normal, fontSize = 16.sp
                    )
                }
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1.5f)
//                            .padding(start = 8.dp)
                , contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${manifest.fecha}",
                    //modifier =modifier.padding(horizontal = 4.dp),
                    fontWeight = FontWeight.Normal, fontSize = 16.sp
                )
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
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Fecha", fontWeight = FontWeight.SemiBold
                )
            }
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
fun Footer(padding: Modifier) {
}