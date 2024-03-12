package com.example.pliin.apppliin.ui.guides

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
import androidx.navigation.NavHostController
import com.example.pliin.R
import com.example.pliin.navigation.AppScreen

@Composable
fun MenuGuideScreen(navigationController: NavHostController) {

    Box(modifier = Modifier.fillMaxSize()) {
        Header(
            Modifier
                .align(Alignment.TopCenter)
                .background(Color(0xFF4425a7))
        )
        Body(
            Modifier.align(Alignment.Center),
            navigationController
        )
        Footer(
            Modifier.align(Alignment.BottomCenter),
            navigationController
        )
    }
}

@Composable
fun Header(modifier: Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = modifier.padding(vertical = 16.dp),
            text = "Menu guias",
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

@Composable
fun Body(modifier: Modifier, navigationController: NavHostController) {
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
        GroupButton(styleButton, navigationController)
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
fun GroupButton(modifier: Modifier, navigationController: NavHostController) {
    val spaces = (Modifier.size(20.dp))
    ButtonRegisterGuide(modifier, navigationController)
    Spacer(modifier = spaces)
    ButtonRecolection(modifier, navigationController)
    Spacer(modifier = spaces)
    ButtonValidateArrastre(modifier, navigationController)
    //  Spacer(modifier = spaces)
    //   ButtonConsultDelivery(modifier)

}

@Composable
fun ButtonRegisterGuide(modifier: Modifier, navigationController: NavHostController) {
    Button(
        onClick = { navigationController.navigate(AppScreen.ReceptionGuideScreen.createRoute("Registro de Guias")) },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Icon(
            imageVector = Icons.Rounded.PostAdd,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color(76, 81, 198)
        )
        Text(
            text = "Registrar",
            fontSize = 18.sp,
        )
    }
}

@Composable
fun ButtonRecolection(modifier: Modifier, navigationController: NavHostController) {
    Button(
        onClick = { navigationController.navigate(AppScreen.ReceptionGuideScreen.createRoute("Recolección")) },
        modifier = modifier,
        enabled = false,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Icon(
            imageVector = Icons.Rounded.Checklist,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color(0xFF4c51c6)
        )
        Text(
            text = "Recolección",
            fontSize = 18.sp,
            modifier = Modifier.padding(end = 28.dp)
        )
    }
}

@Composable
fun ButtonValidateArrastre(modifier: Modifier, navigationController: NavHostController) {
    Button(
        modifier = modifier,
        //enabled = false,
        onClick = { navigationController.navigate(AppScreen.ValidationArrastreScreen.route) },
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
            text = "Validar",
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
            imageVector = Icons.Rounded.Home,
            contentDescription = null,
            modifier = Modifier.size(70.dp),
            tint = Color(76, 81, 198)
        )
        Text(
            text = "Menu principal",
            fontSize = 18.sp,
        )
    }
}