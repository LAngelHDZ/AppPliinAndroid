package com.example.pliin.apppliin.ui.manifest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
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


@Composable
fun ManifiestoMainScreen(
    navigationController: NavHostController,
    mmViewModel: MMViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Header(
            Modifier
                .align(Alignment.TopCenter)
                .background(Color(0xFF4425a7))
        )
        Body(
            Modifier.align(Alignment.Center),
            navigationController, mmViewModel
        )
        Footer(
            Modifier.align(Alignment.BottomCenter),
            navigationController
        )
    }
}

@Composable
fun Header(modifier: Modifier) {
    HeadText(modifier = modifier)
}

@Composable
fun HeadText(modifier: Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = modifier.padding(vertical = 16.dp),
            text = "Manifiesto",
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

@Composable
fun Body(modifier: Modifier, navigationController: NavHostController, mmViewModel: MMViewModel) {
    val styleButton = (Modifier
        .padding(horizontal = 50.dp)
        .height(60.dp)
        .fillMaxWidth())
    Column(
        modifier = modifier.padding(bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BodyLogo()
        Spacer(modifier = Modifier.size(16.dp))
        GroupButton(styleButton, navigationController, mmViewModel)
    }
}

@Composable
fun BodyLogo() {
    Image(
        painter = painterResource(id = R.drawable.salter_logo_04),
        contentDescription = "SALTER",
        Modifier.size(180.dp)
    )
}

@Composable
fun GroupButton(
    modifier: Modifier,
    navigationController: NavHostController,
    mmViewModel: MMViewModel
) {
    val spaces = (Modifier.size(20.dp))
    ButtonCreateManifest(modifier, navigationController, mmViewModel)
    Spacer(modifier = spaces)
   // ButtonRegisterDelivery(modifier, navigationController)
    // Spacer(modifier = spaces)
    // ButtonConsult(modifier)
    //  Spacer(modifier = spaces)
    //   ButtonConsultDelivery(modifier)

}

@Composable
fun ButtonCreateManifest(
    modifier: Modifier,
    navigationController: NavHostController,
    mmViewModel: MMViewModel
) {
    Button(
        onClick = { mmViewModel.navigationCreateManifest(navigationController)},
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    ){
        Icon(
            imageVector = Icons.Rounded.PostAdd,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color(76, 81, 198)
        )
        Text(
            text = "Crear",
            fontSize = 18.sp,
        )
    }
}

@Composable
fun ButtonRegisterDelivery(modifier: Modifier, navigationController: NavHostController) {
    Button(
        onClick = {},
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    ){
        Icon(
            imageVector = Icons.Rounded.Checklist,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color(0xFF4c51c6)
        )
        Text(
            text = "Listar",
            fontSize = 18.sp,
            modifier = Modifier.padding(end = 28.dp)
        )
    }
}

@Composable
fun ButtonConsult(modifier: Modifier) {
    Button(
        modifier = modifier,
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Icon(
            imageVector = Icons.Rounded.ManageSearch,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color(76, 81, 198)
        )
        //  Spacer(Modifier.size(5.dp))
        Text(
            text = "Consultar guia",
            fontSize = 18.sp,
            modifier = Modifier.padding(end = 53.dp)
        )
    }
}

@Composable
fun ButtonConsultDelivery(modifier: Modifier) {
    Button(
        modifier = modifier,
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Icon(
            imageVector = Icons.Rounded.ContentPasteSearch,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color(0xFF4c51c6)
        )
        Spacer(Modifier.size(5.dp))
        Text(
            text = "Consultar entregas",
            fontSize = 18.sp,
            modifier = Modifier.padding(0.dp, 0.dp, 13.dp, 0.dp)
        )
    }
}

@Composable
fun Footer(modifier: Modifier, navigationController: NavHostController) {
    Button(
        modifier = modifier
            .padding(horizontal = 50.dp, vertical = 20.dp)
            .height(60.dp)
            .fillMaxWidth(),
        onClick = {
            navigationController.popBackStack()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = null,
            modifier = Modifier.size(70.dp),
            tint = Color(76, 81, 198)
        )
        Text(
            text = "Regresar",
            fontSize = 18.sp,
        )
    }
}