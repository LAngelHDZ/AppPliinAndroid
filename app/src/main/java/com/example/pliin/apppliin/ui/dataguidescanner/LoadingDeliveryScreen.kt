package com.example.pliin.apppliin.ui.dataguidescanner

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pliin.navigation.AppScreen
import kotlinx.coroutines.delay


@Composable
fun LoadingDeliveryScreen(
    navigationController: NavHostController,

    ) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = Color(0xFF4c51c6)
            )
            Spacer(modifier = Modifier.size(20.dp))
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center){
                Text(text = "Completado")
            }


            LaunchedEffect(key1 =1){
                delay(2000)
                navigationController.navigate(AppScreen.AppMainScreen.route)
            }

            Spacer(modifier = Modifier.size(20.dp))

           /* Button(
                onClick = { navigationController.navigate(AppScreen.RegisterDeliveryScreen.route) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color(0xFF4c51c6)
                ),
                border = BorderStroke(1.dp, Color(0xFFfd9369))
            ) {
                Text(
                    text = "Nueva entrega",
                    fontSize = 18.sp,
                )
            }*/
            /*
            Spacer(modifier = Modifier.size(8.dp))
            Button(
                onClick = {

                    navigationController.navigate(AppScreen.AppMainScreen.route)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color(0xFF4c51c6)
                ),
                border = BorderStroke(1.dp, Color(0xFFfd9369))
            ) {
                Text(
                    text = "Menu principal",
                    fontSize = 18.sp,
                )
            }*/
        }
    }
}
