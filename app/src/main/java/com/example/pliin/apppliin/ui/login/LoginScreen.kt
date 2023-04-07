package com.example.pliin.apppliin.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.pliin.R


@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navigationController: NavHostController) {
    //Declaro un box el que contendra los elementos en pantalla con un modficador que ocupe el espacion en pantalla disponible
    Box(Modifier.fillMaxSize()) {
        val isSesionDialog: Boolean by loginViewModel.isSesionDialog.observeAsState(false)
        //Pinta en pantalla los componenetes visuales del head de la aplicaciòn con un aliniaciòn Arriba Central
        Header(
            Modifier.align(Alignment.TopCenter)
        )
        Body(
            Modifier
                .align(Alignment.Center)
                .padding(top = 8.dp), loginViewModel, navigationController
        )
        Footer(
            Modifier.align(Alignment.BottomCenter)
        )

        SesionDialog(show = isSesionDialog, loginViewModel)
    }
}

@Composable
fun SesionDialog(show: Boolean, loginViewModel: LoginViewModel) {
    if (show) {
        AlertDialog(onDismissRequest = { loginViewModel.onSesionDialog() },
            title = { Text(text = "Advertencia") },
            text = { Text(text = "El usuario o contraseña son incorrectos") },
            confirmButton = {
                TextButton(onClick = { loginViewModel.onSesionDialog() }) {
                    Text(text = "Intentar nuevamente")
                }
            }
        )
    }
}

@Composable
fun Header(modifier: Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Logo()
        TextBienvenida()
    }
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.pliin_logo_morado),
        contentDescription = "PLIIN",
        modifier = Modifier.size(200.dp)
    )
}

@Composable
fun TextBienvenida() {
    Text(
        text = "¡Hola!",
        fontSize = 30.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(76, 81, 198)
    )
}

@Composable
fun Body(
    modifier: Modifier,
    loginViewModel: LoginViewModel,
    navigationController: NavHostController,
) {
    val user: String by loginViewModel.user.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")
    val isLoginEnable: Boolean by loginViewModel.isLoginEnable.observeAsState(false)
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        TextSesion()
        Spacer(modifier = Modifier.size(24.dp))
        TextFieldUser(user) {
            loginViewModel.onLoginChanged(user = it, password = password)
        }
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldPassword(password) {
            loginViewModel.onLoginChanged(user = user, password = it)
        }
        Spacer(modifier = Modifier.size(18.dp))
        LoginBoton(
            isLoginEnable, loginViewModel, navigationController
        )
    }

}

@Composable
fun TextSesion() {
    Text(
        text = "Iniciar sesiòn",
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(76, 81, 198)
    )
}

@Composable
fun TextFieldUser(user: (String), onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = user,
        onValueChange = { onTextChanged(it) },
        label = { Text(text = "Usuario") },
        shape = RoundedCornerShape(40),
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
fun TextFieldPassword(password: (String), onTextChanged: (String) -> Unit) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        label = { Text(text = "Contraseña") },
        shape = RoundedCornerShape(40),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(96, 127, 243),
            unfocusedBorderColor = Color(76, 81, 198),
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisibility) {
                Icons.Filled.VisibilityOff

            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, contentDescription = "Show password")
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}

@Composable
fun LoginBoton(
    loginEnable: Boolean,
    loginViewModel: LoginViewModel,
    navigationController: NavHostController,
) {
    Button(
        modifier = Modifier
            .width(180.dp)
            .height(60.dp),
        onClick = { loginViewModel.onLoginSelected(navigationController) },
        enabled = loginEnable,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(96, 127, 243),
            contentColor = Color.White,
            disabledBackgroundColor = Color(0xFF91a6f3),
            disabledContentColor = Color.White
        )
    ) {
        Text(
            text = "Acceder",
            fontSize = 20.sp,
        )
    }
}

@Composable
fun Footer(modifier: Modifier) {
    Text(
        text = "Copyright C-Pliin x Coco Estudio 2023",
        modifier = modifier,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(76, 81, 198)
    )
}