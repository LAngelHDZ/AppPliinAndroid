package com.example.pliin.apppliin.ui.appmain

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pliin.R
import com.example.pliin.apppliin.domain.model.avisopagoitem.FieldDataAvisoItem
import com.example.pliin.navigation.AppScreen


@Composable
fun MainAppScreen(navigationController: NavHostController,Employee: String,area:String,mainAppViewModel: MainAppViewModel) {

    val isLogout: Boolean by mainAppViewModel.isLogout.observeAsState(false)
    val isLoged: Boolean by mainAppViewModel.isLoged.observeAsState(true)
    val nameEmployee: String by mainAppViewModel.nameEmployee.observeAsState("")
    val areaEmployee: String by mainAppViewModel.areaEmployee.observeAsState("")
    val showAviso:Int by mainAppViewModel.showAviso.observeAsState(initial = 1)
    val lockedAviso:Int by mainAppViewModel.lockedAviso.observeAsState(initial = 0)
    val avisoMessage: String by mainAppViewModel.avisoMessage.observeAsState("")
    val avisoMessageTitle: String by mainAppViewModel.avisoMessageTitle.observeAsState("")
    val progressUnlocked: Boolean by mainAppViewModel.progressUnlocked.observeAsState(false)
    val countDias:Int by mainAppViewModel.countDias.observeAsState(initial = 0)
    val isUnlokecd: Boolean by mainAppViewModel.isUnlocked.observeAsState(false)
    val data:FieldDataAvisoItem by mainAppViewModel.data.observeAsState(initial = FieldDataAvisoItem(null,0,0,null,0,null,null,null,null))


    if (isLoged){
        if (area.equals("Operador Logistico") || area.equals("Auxiliar Administrativo") ){
            mainAppViewModel.gsaveDataEmployee(Employee,area)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLogout) {
            ScreenConfirmation(
                Modifier.align(
                    Alignment.Center
                )
            )
        } else {
            Header(
                Modifier
                    .align(Alignment.TopStart)
                    .background(Color(0xFF4425a7)),nameEmployee, areaEmployee
            )
            Body(Modifier.align(Alignment.Center), navigationController,areaEmployee)
            Footer(Modifier.align(Alignment.BottomCenter), navigationController, mainAppViewModel)

            AvisoPAgo(showAviso,lockedAviso,avisoMessage,mainAppViewModel,progressUnlocked,countDias,data,isUnlokecd,avisoMessageTitle)
        }
    }
}

@Composable
fun AvisoPAgo(
    showAviso: Int,
    lockedAviso: Int,
    avisoMessage: String,
    mainAppViewModel: MainAppViewModel,
    progressUnlocked: Boolean,
    countDias: Int,
    data: FieldDataAvisoItem,
    isUnlokecd: Boolean,
    avisoMessageTitle: String
) {

    if (showAviso==1 || isUnlokecd){
       /* AlertDialog(modifier = Modifier.fillMaxWidth()

            ,
            onDismissRequest = { },
            title = { HeadModal()},
            text = {

                   if (progressUnlocked){
                       Box(modifier = Modifier
//                           .fillMaxWidth()
                           .padding(top = 10.dp),
                           contentAlignment = Alignment.Center) {
                           Row(
                               modifier = Modifier
//                               .fillMaxWidth()
                                   .padding(vertical = 10.dp),
                               horizontalArrangement = Arrangement.Center,
                               verticalAlignment = Alignment.CenterVertically
                           ) {
                               Text(text = "Verificando...")
                               Spacer(modifier = Modifier.size(4.dp))
                               CircularProgressIndicator()
                           }
                       }
                   }else{
                       if (isUnlokecd){
                           Row(
                               verticalAlignment = Alignment.CenterVertically,
                               horizontalArrangement = Arrangement.Center
                           ){
                               Icon(imageVector = Icons.Rounded.CheckCircle, contentDescription = "CheckList")
                               Spacer(modifier = Modifier.size(4.dp))
                               Text(text = avisoMessage)
                           }

                       }else{
                           Text(text = avisoMessage)

                       }
                   }
            },
            buttons = {
                if (lockedAviso==0){
                    TextButton(onClick = { mainAppViewModel.closeAviso() }) {
                        Text(text = "Cerrar")
                    }
                }else{
                    TextButton(onClick = { mainAppViewModel.verificarUnlocked() }) {
                        Text(text = "Verificar desbloqueo")
                    }
                }
            }
        )*/
    }
}

@Composable
private fun HeadModal(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(brush = Brush.verticalGradient(listOf(Color(0xFF4c51c6), Color.White)))
        ,
        contentAlignment = Alignment.Center){
        Image(
            painter = painterResource(id = R.drawable.pliin_logo_blanco),
            contentDescription = "PLIIN",
            modifier = Modifier.size(70.dp)
        )

    }
}

@Composable
fun LoadWebImage(url: String) {

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
            imageVector = Icons.Rounded.Logout,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = Color(0xFF4c51c6)
        )
        Spacer(modifier = Modifier.size(8.dp))
        CircularProgressIndicator(color = Color(0xFF4c51c6), strokeWidth = 4.dp)
    }
}

@Composable
fun Header(modifier: Modifier, nameEmployee: String, areaEmployee: String) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Photo()
        Spacer(modifier = Modifier.size(8.dp))
        UserData(modifier = modifier, nameEmployee, areaEmployee)
    }

}

@Composable
fun Photo() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .border(1.dp, Color(0xFFfd9369), CircleShape)
            .background(Color.White)
    ) {


        if (false) {
            GetPhoto()
        } else {
            DefaultPhoto()
        }
    }
}

@Composable
fun GetPhoto() {
    // val url = "https://ts2.cn.mm.bing.net/th?id=OIP-C.3-kN66uuTJWHyi4JxZqC6wAAAA"
    val url =
        "http://sidigq.ddns.net/Streaming/MainDB/B208529934F32482AFC22BBCCC7A117CE41DAA352F1C9980CE97DEEEAAD39DBB.jpg?RCType=EmbeddedRCFileProcessor"
    AsyncImage(modifier = Modifier.size(60.dp), model = url, contentDescription = "")

    //LoadWebImage("https://sidigq.ddns.net/Streaming/MainDB/B208529934F32482AFC22BBCCC7A117CE41DAA352F1C9980CE97DEEEAAD39DBB.jpg?RCType=EmbeddedRCFileProcessor")
}

@Composable
fun DefaultPhoto() {
    Icon(
        imageVector = Icons.Rounded.Person,
        contentDescription = null,
        modifier = Modifier.size(60.dp),
        tint = Color(0xFF4c51c6)
    )
}

@Composable
fun UserData(modifier: Modifier, nameEmployee: String, areaEmployee: String) {
    Column(modifier.fillMaxWidth()) {
        Text(
            text = nameEmployee,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = areaEmployee,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

@Composable
fun Body(modifier: Modifier, navigationController: NavHostController, area: String) {
    val styleButton = (Modifier
        .padding(horizontal = 50.dp)
        .height(60.dp)
        .fillMaxWidth())
    Column(modifier = modifier.padding(bottom = 80.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        BodyLogo()
        Spacer(modifier = Modifier.size(16.dp))
        GroupButton(styleButton,navigationController,area)
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

@SuppressLint("SuspiciousIndentation")
@Composable
fun GroupButton(modifier: Modifier, navigationController: NavHostController, area: String) {
    val spaces = (Modifier.size(20.dp))
    ButtonCreateManifest(modifier, navigationController)
    Spacer(modifier = spaces)
    if (area.equals("Operador Logistico")){
        ButtonRegisterDelivery(modifier, navigationController)
        Spacer(modifier = spaces)
    }
    //boton registra las guias al sistema
    ButtonGuides(modifier, navigationController)
    Spacer(modifier = spaces)
    ButtonConsultDelivery(modifier)

}

@Composable
fun ButtonCreateManifest(modifier: Modifier, navigationController: NavHostController) {
    Button(
        onClick = { navigationController.navigate(AppScreen.ManifiestoMainScreen.route) },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Icon(
            imageVector = Icons.Outlined.ContentPaste,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color(76, 81, 198)
        )
        Text(
            text = "Manifiesto",
            fontSize = 18.sp,
        )
    }
}

@Composable
fun ButtonRegisterDelivery(modifier: Modifier, navigationController: NavHostController) {
    Button(
        onClick = { navigationController.navigate(AppScreen.RegisterDeliveryScreen.route) },
        modifier = modifier,
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
            text = "Entregas",
            fontSize = 18.sp,
            modifier = Modifier.padding(end = 28.dp)
        )
    }
}

@Composable
fun ButtonGuides(modifier: Modifier, navigationController: NavHostController) {
    Button(
        modifier = modifier,
        onClick = { navigationController.navigate(AppScreen.MenuGuideScreen.route) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Icon(
            imageVector = Icons.Rounded.StickyNote2,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color(76, 81, 198)
        )
        //  Spacer(Modifier.size(5.dp))
        Text(
            text = "Guias",
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
        enabled = false,
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
            text = "Consultas",
            fontSize = 18.sp,
            modifier = Modifier.padding(0.dp, 0.dp, 13.dp, 0.dp)
        )
    }
}

@Composable
fun Footer(
    modifier: Modifier,
    navigationController: NavHostController,
    mainAppViewModel: MainAppViewModel
) {
    Button(
        modifier = modifier
            .padding(horizontal = 50.dp, vertical = 20.dp)
            .height(60.dp)
            .fillMaxWidth(),
        onClick = {
            mainAppViewModel.logut(navigationController)
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Icon(
            imageVector = Icons.Rounded.Logout,
            contentDescription = null,
            modifier = Modifier.size(70.dp),
            tint = Color(76, 81, 198)
        )
        Text(
            text = "Salir",
            fontSize = 18.sp,
        )
    }
}