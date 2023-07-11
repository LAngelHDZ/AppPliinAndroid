package com.example.pliin.apppliin.ui.dataguidescanner

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.view.PreviewView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.example.pliin.R
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Preview(showSystemUi = true)
@Composable
fun preview() {

}

fun obtenerHoraActual(): String {
    return LocalTime.of(LocalTime.now().hour, LocalTime.now().minute, LocalTime.now().second)
        .toString()
}

fun getdatenow(): String {
    return LocalDate.now().toString()
}

@SuppressLint("SuspiciousIndentation", "UnrememberedMutableState")
@Composable
fun DataGuideScannerScreen(
    dgsViewModel: DGSViewModel,
    navigationController: NavHostController,
    idGuia: String,
    idPreM: String,
    nombre: String,
    direccion: String,
    telefono: String,
    cods: String,
    status: String,
    empresa: String,
    ruta: String,
    pesokg: String,
    valorGuia: String,
    recordId: String,
    statusIntento: String
) {

    /* lateinit var outputDirectory: File
      lateinit var cameraExecutor: ExecutorService

       var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)

       lateinit var photoUri: Uri
      var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)

       val requestPermissionLauncher = registerForActivityResult(
          ActivityResultContracts.RequestPermission()
      ) { isGranted ->
          if (isGranted) {
              Log.i("kilo", "Permission granted")
              shouldShowCamera.value = true
          } else {
              Log.i("kilo", "Permission denied")
          }
      }*/






    Log.i("recordid ene view", "$recordId")
    Log.i("status entrega ene view", "$statusIntento")
    val hora = obtenerHoraActual()
    val date = getdatenow()
    val data = listOf<String>(
        idGuia,
        idPreM,
        nombre,
        direccion,
        telefono,
        cods,
        status,
        empresa,
        ruta,
        pesokg,
        valorGuia,
        recordId,
        hora,
        date,
        statusIntento
    )
    val isAlertDialogExit: Boolean by dgsViewModel.isAlertDialogexit.observeAsState(false)
    val isShowCameraX: Boolean by dgsViewModel.isShowCameraX.observeAsState(false)
    val isAlertDialogConfirmation: Boolean by dgsViewModel.isAlertDialogConfirmation.observeAsState(
        false
    )
    val listStatusIntentos: List<String> by dgsViewModel.listStatusIntentos.observeAsState(listOf())
    val isDeliveryConfirmation: Boolean by dgsViewModel.isDeliveryConfirmation.observeAsState(false)
    val isAnotherParent: Boolean by dgsViewModel.isAnotherParent.observeAsState(false)
    val title: String by dgsViewModel.titleAlertDialog.observeAsState(" ")
    val text: String by dgsViewModel.textAlertDialog.observeAsState(" ")
    val status: String by dgsViewModel.status.observeAsState(" ")
    val nameparents: String by dgsViewModel.nameRecibe.observeAsState(" ")
    val anotherParents: String by dgsViewModel.parentOrFailDelivery.observeAsState(" ")
    val selectedOption: String by dgsViewModel.selectedOption.observeAsState(" ")
    dgsViewModel.getNameCLient(nombre)

    Box(modifier = Modifier.fillMaxSize()) {
        if (isShowCameraX) {
            CameraXview(dgsViewModel, Modifier)
        } else {
            if (isDeliveryConfirmation) {
                ScreenConfirmation(Modifier.align(Alignment.Center))
            } else {
                Header(
                    Modifier
                        .align(Alignment.TopCenter)
                        .background(Color(0xFF4425a7)),
                    navigationController, dgsViewModel
                )
                Body(
                    Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    data
                )
                Footer(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 8.dp, vertical = 16.dp), data, dgsViewModel
                )
                AlertDialogexitScreen(show = isAlertDialogExit, dgsViewModel, navigationController)
                AlertDialogConfirmation(
                    show = isAlertDialogConfirmation,
                    dgsViewModel,
                    navigationController,
                    data[0],
                    data[11],
                    data[14],
                    text,
                    status,
                    nameparents,
                    selectedOption,
                    anotherParents,
                    isAnotherParent,
                    listStatusIntentos
                )
            }
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
fun Header(
    modifier: Modifier, navigationController: NavHostController, dgsViewModel: DGSViewModel,
) {
    HeadrTopAppBar(navigationController, dgsViewModel)
}

@Composable
fun HeadrTopAppBar(navigationController: NavHostController, dgsViewModel: DGSViewModel) {
    TopAppBar(
        title = { Text(text = "Datos Guia") },
        backgroundColor = Color(0xFF4425a7),
        contentColor = Color.White,
        elevation = 4.dp,
        navigationIcon = {
            IconButton(onClick = { dgsViewModel.onAlertDialogExitexchange() }
            ) {
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
fun Body(modifier: Modifier, data: List<String>) {
    Box(
        modifier = modifier
            .fillMaxWidth()

    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            border = BorderStroke(2.dp, Color(0xFF4425a7))
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                headguide(data)
                Spacer(modifier = Modifier.size(5.dp))
                Cods(data)
                Spacer(modifier = Modifier.size(5.dp))
                RutaAndLogo(data)
                Spacer(modifier = Modifier.size(5.dp))
                DateTime(data)
                Spacer(modifier = Modifier.size(5.dp))
                ClientName(data[2])
                Spacer(modifier = Modifier.size(5.dp))
                telefono(data[4])
                Spacer(modifier = Modifier.size(5.dp))
                Direccion(data[3])
            }
        }
    }
}

@Composable
fun headguide(data: List<String>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        // Spacer(modifier = Modifier.size(4.dp))
        Box(modifier = Modifier.weight(1.8f)) {
            StationTextField(data[0])
        }
        Spacer(modifier = Modifier.size(4.dp))
        Box(
            modifier = Modifier
                .weight(1f)
            //.background(Color(0xFFf9f9f9)),
        ) {
            LogoEmpresa(data[7], Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun TextStation() {
    Text(text = "Guia", fontWeight = FontWeight.Bold)
}

@Composable
fun StationTextField(guia: String) {
    Column() {
        TextStation()
        BasicTextField(
            value = guia,
            readOnly = true,
            onValueChange = {
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(all = 4.dp), // inner padding,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun Cods(data: List<String>) {
    Row(modifier = Modifier.fillMaxWidth())
    {
        // Spacer(modifier = Modifier.size(4.dp))
        //    Box(modifier = Modifier.weight(0.3f)) {
        //      Cod(Modifier, data[5])
        // }
        Spacer(modifier = Modifier.size(4.dp))
        Box(modifier = Modifier.weight(1f)) {
            ValueCod(Modifier, data[10])
        }
        Spacer(modifier = Modifier.size(4.dp))
        Box(modifier = Modifier.weight(1f)) {
            Pesoguia(Modifier, data[9])
        }
    }
}

@Composable
fun Cod(Modifier: Modifier.Companion, cod: String) {
    Column(
        // horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Cod", fontWeight = FontWeight.Bold)
        Spacer(modifier = androidx.compose.ui.Modifier.size(4.dp))
        //TextField(modifier = modifier.width(138.dp),value = "ZIHUATANEJO", onValueChange = {})
        BasicTextField(
            // modifier = modifier.width(138.dp) ,
            value = cod, onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = androidx.compose.ui.Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(all = 4.dp), // inner padding
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = androidx.compose.ui.Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun ValueCod(Modifier: Modifier.Companion, valueCod: String) {
    Column(
        // horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Valor", fontWeight = FontWeight.Bold)
        Spacer(modifier = androidx.compose.ui.Modifier.size(4.dp))
        //TextField(modifier = modifier.width(138.dp),value = "ZIHUATANEJO", onValueChange = {})
        BasicTextField(
            // modifier = modifier.width(138.dp) ,
            value = "$$valueCod", onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = androidx.compose.ui.Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(all = 4.dp), // inner padding
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = androidx.compose.ui.Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun Pesoguia(Modifier: Modifier.Companion, peso: String) {
    Column(
        // horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Peso", fontWeight = FontWeight.Bold)
        Spacer(modifier = androidx.compose.ui.Modifier.size(4.dp))
        //TextField(modifier = modifier.width(138.dp),value = "ZIHUATANEJO", onValueChange = {})
        BasicTextField(
            // modifier = modifier.width(138.dp) ,
            value = "$peso Kg", onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = androidx.compose.ui.Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(all = 4.dp), // inner padding
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = androidx.compose.ui.Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun DateTime(data: List<String>) {
    Row(modifier = Modifier.fillMaxWidth())
    {
        // Spacer(modifier = Modifier.size(4.dp))
        Box(modifier = Modifier.weight(1.2f)) {
            Date(Modifier, data[13])
        }
        Spacer(modifier = Modifier.size(4.dp))
        Box(modifier = Modifier.weight(1f)) {
            Time(Modifier, data[12])
        }
        Spacer(modifier = Modifier.size(4.dp))
    }
}

@Composable
fun RutaAndLogo(ruta: List<String>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        // Spacer(modifier = Modifier.size(4.dp))
        Box(modifier = Modifier.weight(1f)) {
            Ruta(ruta[8])
        }
    }
}

@Composable
fun Ruta(ruta: String) {
    Column(
        // horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Ruta", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(4.dp))
        //TextField(modifier = modifier.width(138.dp),value = "ZIHUATANEJO", onValueChange = {})
        BasicTextField(
            // modifier = modifier.width(138.dp) ,
            value = ruta, onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(all = 4.dp), // inner padding
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun LogoEmpresa(logo: String, align: Modifier) {
    val logo = R.drawable.ups
    Image(
        painter = painterResource(logo),
        contentDescription = "UPS",
        modifier = align.size(60.dp)
    )
}

@Composable
fun Date(modifier: Modifier, date: String) {
    Column(
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Fecha", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(4.dp))
        //TextField(modifier = modifier.width(118.dp),value = "", onValueChange = {})
        // DatePickerPreview(modifier)
        BasicTextField(
            //modifier = modifier.width(118.dp),
            value = date, onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(all = 4.dp), // inner padding
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun DatePicker(
    selectedDate: Calendar,
    modifier: Modifier,
    onDateSelected: (Calendar) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
        //.wrapContentHeight()
        //.height(100.dp)
    ) {
        BasicTextField(
            //modifier = modifier.width(118.dp),
            value = "date", onValueChange = {},
            readOnly = true,

            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(all = 4.dp), // inner padding
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.clickable { expanded = true }
                    )
                }
            }

        )


        DropdownMenu(
            modifier = modifier.height(200.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            (2000..Calendar.getInstance().get(Calendar.YEAR)).forEach { year ->
                DropdownMenuItem(onClick = {
                    selectedDate.set(Calendar.DAY_OF_MONTH, year)
                    expanded = false
                    onDateSelected(selectedDate)
                }) {
                    Text(
                        text = year.toString(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun DatePickerPreview(modifier: Modifier) {
    val selectedDate = remember { Calendar.getInstance() }
    Box() {
        DatePicker(selectedDate = selectedDate, modifier) { date ->
            // Handle date selection
        }
    }
}

@Composable
fun Time(modifier: Modifier, time: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hora", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(4.dp))
        // TextField(modifier = modifier.width(100.dp),value = "19:21:20", onValueChange = {})
        BasicTextField(
            //modifier = modifier.width(100.dp),
            value = time,
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(all = 4.dp), // inner padding
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
    }
}


@Composable
fun ClientName(nombre: String) {
    Column() {
        Text(text = "Nombre Cliente", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(4.dp))
        // TextField(modifier = modifier.width(100.dp),value = "19:21:20", onValueChange = {})
        BasicTextField(
            //modifier = modifier.width(100.dp),
            value = nombre,
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(all = 4.dp), // inner padding
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun Direccion(direccion: String) {
    Column() {
        Text(text = "Direccion", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(4.dp))
        // TextField(modifier = modifier.width(100.dp),value = "19:21:20", onValueChange = {})
        BasicTextField(
            modifier = Modifier.height(100.dp),
            value = direccion,
            readOnly = true,
            onValueChange = {},
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(all = 4.dp), // inner padding
                    // verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun telefono(telefono: String) {
    Column() {
        Text(
            text = "Telefono", fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(4.dp))
        // TextField(modifier = modifier.width(100.dp),value = "19:21:20", onValueChange = {})
        BasicTextField(
            // modifier = Modifier.height(100.dp),
            value = telefono,
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        // .padding(horizontal = 64.dp) // margin left and right
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF4425a7),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(all = 4.dp), // inner padding
                    // verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun Footer(modifier: Modifier, data: List<String>, dgsViewModel: DGSViewModel) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CormfirmationButtom(data[0], data[11], dgsViewModel)
        Spacer(modifier = Modifier.size(5.dp))
        DenegationButtom(data[0], data[11], dgsViewModel)
    }
}

@Composable
fun CormfirmationButtom(idguia: String, recordId: String, dgsViewModel: DGSViewModel) {
    Button(
        onClick = {
            dgsViewModel.onAlertDialogConfirmationexchange(
                "Confirmación",
                "Desea Confirmar la entrega?",
                "ENTREGADO"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        ),
        border = BorderStroke(1.dp, Color(0xFFfd9369))
    ) {
        Text(
            text = "Confirmar entrega",
            fontSize = 18.sp,
        )
    }

}

@Composable
fun DenegationButtom(idGuia: String, recordId: String, dgsViewModel: DGSViewModel) {
    Button(
        onClick = {
            dgsViewModel.onAlertDialogConfirmationexchange(
                "Intento de entrega",
                "Desea registra el intento de entrega?",
                "ENTREGA FALLIDA"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        ),
        border = BorderStroke(1.dp, Color(0xFFfd9369))
    ) {
        Text(
            text = "Entrega Fallida",
            fontSize = 18.sp,
        )
    }
}

@Composable
fun AlertDialogexitScreen(
    show: Boolean,
    dgsViewModel: DGSViewModel,
    navigationController: NavHostController
) {
    if (show) {
        AlertDialog(onDismissRequest = {
            dgsViewModel.onAlertDialogexit(
                exitConfirmation = false,
                navigationController
            )
        },
            title = { Text(text = "Advertencia") },
            text = { Text(text = "Desea salir de la confirmacion de entrega?") },
            confirmButton = {
                TextButton(onClick = {
                    dgsViewModel.onAlertDialogexit(
                        exitConfirmation = true,
                        navigationController
                    )
                }) {
                    Text(text = "Si")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    dgsViewModel.onAlertDialogexit(
                        exitConfirmation = false,
                        navigationController
                    )
                }) {
                    Text(text = "No")
                }
            }
        )
    }
}

@Composable
fun AlertDialogConfirmation(
    show: Boolean,
    dgsViewModel: DGSViewModel,
    navigationController: NavHostController,
    idGuia: String,
    recordId: String,
    statusIntento: String,
    text: String,
    status: String,
    nameparents: String,
    selectedOption: String,
    anotherParents: String,
    isAnotherParent: Boolean,
    listStatusIntentos: List<String>,

    ) {
    var expanded by remember { mutableStateOf(false) }
    var columnSize by remember { mutableStateOf(Size.Zero) }

    if (show) {
        Dialog(onDismissRequest = { dgsViewModel.reset() }) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(24.dp)
                    .fillMaxWidth()
                    .height(470.dp)
            ) {

                if (status.equals("ENTREGADO")) {
                    ConfirmarEntregaDialog(
                        dgsViewModel,
                        selectedOption,
                        nameparents,
                        isAnotherParent,
                        anotherParents
                    ) {
                        dgsViewModel.onValueChanged(selected = it)
                        dgsViewModel.onValueChangedRecibe(nameparent = it)

                    }
                } else {
                    ConfirmarDevueltoDialog(
                        dgsViewModel,
                        selectedOption,
                        nameparents,
                        statusIntento,
                        listStatusIntentos
                    ) {
                        dgsViewModel.onValueChanged(selected = it)
                        dgsViewModel.onValueChangedRecibe(nameparent = it)
                    }

                }

                Spacer(modifier = Modifier.size(4.dp))
                ButtonsConfirmation(dgsViewModel, idGuia, recordId, navigationController)
            }
        }
    }
}

@Composable
fun ConfirmarEntregaDialog(
    dgsViewModel: DGSViewModel,
    selectedOption: String,
    parents: String,
    isAnotherParent: Boolean,
    anotherParents: String,
    onTextChanged: (String) -> Unit,
) {
    val options = listOf(
        "Titular",
        "Madre",
        "Padre",
        "Hijo(a)",
        "Sobrino(a)",
        "Hermano(a)",
        "Tio(a)",
        "Esposo(a)",
        "Primo(a)",
        "Otro"
    )

    val items = remember {
        mutableStateListOf(
            "Madre",
            "Padre",
            "Hermano",
            "Hermana",
            "Tio",
            "Tia",
            "Esposo",
            "Esposa",
            "Primo",
            "Prima",
            "Otro"
        )
    }


    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = Color(0xFF4425a7),
        text = "ENTREGA"
    )
    Spacer(modifier = Modifier.size(16.dp))
    Text(text = "Parentesco")
    DromMenu(selectedOption, dgsViewModel, options) { dgsViewModel.onValueChanged(selected = it) }
    Spacer(modifier = Modifier.size(14.dp))
    if (isAnotherParent) {
        Text(text = "Escriba el parentesco")
        AnotherParent(anotherParents) { dgsViewModel.onValueChangedParents(otherparent = it) }
    }
    Text(text = "Quien recibe")
    RecibeOrComment(parents) { dgsViewModel.onValueChangedRecibe(nameparent = it) }
    Spacer(modifier = Modifier.size(14.dp))
    btnSHowCameraX(dgsViewModel)
}

@Composable
fun btnSHowCameraX(dgsViewModel: DGSViewModel) {
    Button(
        onClick = { dgsViewModel.onShowCameraX() },
        modifier = Modifier.fillMaxWidth()

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.PhotoCamera,
                contentDescription = null,
                modifier = Modifier.size(45.dp),
                tint = Color.White
            )
            Text(text = "Take Photo")
        }
    }
}

@Composable
fun CameraXview(dgsViewModel: DGSViewModel, modifier: Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    var previewView: PreviewView

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
        // .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                //.height(screenHeight*0.85f)
                .fillMaxWidth()
            //.background(Color.Gray)
        ) {
            AndroidView(
                factory = {
                    previewView = PreviewView(it)
                    dgsViewModel.showCameraPreview(previewView, lifecycleOwner)
                    previewView
                },
                modifier = Modifier
                    .height(screenHeight * 0.85f)
            )
        }
        Box(
            modifier = Modifier
                .height(screenHeight * 0.15f)
                .background(Color.Black)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = {
                dgsViewModel.captureAndSave(context)
            }) {
                Icon(
                    imageVector = Icons.Rounded.Camera,
                    contentDescription = null,
                    modifier = Modifier.size(45.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun AnotherParent(anotherParents: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = anotherParents,
        onValueChange = { onTextChanged(it) },
        placeholder = { Text(text = "Selected") },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
    )
    Spacer(modifier = Modifier.size(14.dp))
}

@Composable
fun RecibeOrComment(parents: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = parents,
        onValueChange = { onTextChanged(it) },
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
fun DromMenu(
    selectedOption: String,
    dgsViewModel: DGSViewModel,
    listStatusIntentos: List<String>,
    onTextChanged: (String) -> Unit
) {
    var expand by remember { mutableStateOf(false) }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }
    OutlinedTextField(
        value = selectedOption,
        onValueChange = { },
        enabled = false,
        readOnly = true,
        modifier = Modifier
            .clickable { expand = true }
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                textFiledSize = coordinates.size.toSize()
            },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
    )
    Box() {
        DropdownMenu(
            expanded = expand,
            onDismissRequest = { expand = false },
            modifier = Modifier
                .height(200.dp)
                .width(with(LocalDensity.current) { textFiledSize.width.toDp() })
        ) {
            /*  LazyColumn(
                  modifier = Modifier.fillMaxWidth(),
                  contentPadding = PaddingValues(8.dp)
              ) {
                  items(items) { item ->
                      Text(
                          text = item,
                          modifier = Modifier
                              .fillMaxWidth()
                              .clickable {
                                  // Acción al hacer clic en el elemento
                                  println("Elemento clicado: $item")
                                  onTextChanged(item)
                                  expand = false
                              }
                      )
                  }
              }*/

            Log.i("lista dedevoluciones", listStatusIntentos.toString())
            listStatusIntentos.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        expand = false
                        onTextChanged(option)
                    }
                ) {
                    Text(text = option)
                }
            }
        }
    }
}

@Composable
fun ConfirmarDevueltoDialog(
    dgsViewModel: DGSViewModel,
    selectedOption: String,
    parents: String,
    statusIntento: String,
    listStatusIntentos: List<String>,
    onTextChanged: (String) -> Unit,
) {
    dgsViewModel.onChangeListStatusIntentos(statusIntento)
    var options = listOf(
        "No esta",
        "Cerrado",
        "Mudanza",
        "No existe No#",
        "No existe calle",
        "Sin Dinero",
        "Rechazado",
        "Recoleccion",
        "Vacaciones",
        "No hay quien firme"
    )

    val items = remember {
        mutableStateListOf(
            "Madre",
            "Padre",
            "Hermano",
            "Hermana",
            "Tio",
            "Tia",
            "Esposo",
            "Esposa",
            "Primo",
            "Prima",
            "Otro"
        )
    }

    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = Color(0xFF4425a7),
        text = "Intento de entrega"
    )
    Spacer(modifier = Modifier.size(16.dp))
    Text(text = "Motivo")
    DromMenu(selectedOption, dgsViewModel, listStatusIntentos) {
        dgsViewModel.onValueChanged(
            selected = it
        )
    }
    Spacer(modifier = Modifier.size(14.dp))
    Text(text = "Comentario")
    RecibeOrComment(parents) { dgsViewModel.onValueChangedRecibe(nameparent = it) }
    // Text(text = "Quien recibe")
    // Recibe(parents){dgsViewModel.onValueChangedRecibe(nameparent = it)}
}

@Composable
fun ButtonsConfirmation(
    dgsViewModel: DGSViewModel,
    idGuia: String,
    recordId: String,
    navigationController: NavHostController
) {
    Row() {
        TextButton(onClick = { dgsViewModel.reset() }) {
            Text(text = "Cancelar")
        }
        TextButton(onClick = { dgsViewModel.setDelivery(idGuia, recordId, navigationController) }) {
            Text(text = "Continuar")
        }
    }
}