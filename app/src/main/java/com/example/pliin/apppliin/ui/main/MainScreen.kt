package com.example.pliin.apppliin.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pliin.R
import com.example.pliin.navigation.AppScreen
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay

@Composable
fun MainScreen(navigationController: NavHostController) {
    LaunchedEffect(key1 = true){
        delay(5000)
        navigationController.popBackStack()
        navigationController.navigate(AppScreen.LoginScreen.route)
    }
    Screen()
}

@Preview(showSystemUi = true)
@Composable
fun Screen(){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF4c51c6))
        ,contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.pliin_logo_blanco),
                contentDescription = "PLIIN",
                modifier = Modifier.size(250.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            
            CircularProgressIndicator(color = Color.White, strokeWidth = 4.dp)
        }
    }
}