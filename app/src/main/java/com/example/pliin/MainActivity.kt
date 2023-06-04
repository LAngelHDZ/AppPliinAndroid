package com.example.pliin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pliin.apppliin.core.network.NetworkConectivity
import com.example.pliin.apppliin.ui.appmain.MainAppScreen
import com.example.pliin.apppliin.ui.appmain.MainAppViewModel
import com.example.pliin.apppliin.ui.dataguidescanner.DGSViewModel
import com.example.pliin.apppliin.ui.dataguidescanner.DataGuideScannerScreen
import com.example.pliin.apppliin.ui.dataguidescanner.LoadingDeliveryScreen
import com.example.pliin.apppliin.ui.failload.FailLoadScreen
import com.example.pliin.apppliin.ui.guides.MenuGuideScreen
import com.example.pliin.apppliin.ui.guides.receptionguide.RGViewModel
import com.example.pliin.apppliin.ui.guides.receptionguide.ReceptionGuideScreen
import com.example.pliin.apppliin.ui.guides.validationarrastre.VAViewModel
import com.example.pliin.apppliin.ui.guides.validationarrastre.ValidationArrastreScreen
import com.example.pliin.apppliin.ui.login.LoginScreen
import com.example.pliin.apppliin.ui.login.LoginViewModel
import com.example.pliin.apppliin.ui.mainloading.MainLoadScreen
import com.example.pliin.apppliin.ui.manifest.ManifiestoMainScreen
import com.example.pliin.apppliin.ui.manifest.createmanifest.CMViewModel
import com.example.pliin.apppliin.ui.manifest.createmanifest.CreateManifestScreen
import com.example.pliin.apppliin.ui.registerdelivery.RDViewModel
import com.example.pliin.apppliin.ui.registerdelivery.RegisterDeliveryScreen
import com.example.pliin.navigation.AppScreen
import com.example.pliin.ui.theme.PliinTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val mainAppViewModel: MainAppViewModel by viewModels()
    private val rdViewModel: RDViewModel by viewModels()
    private val dgsViewModel: DGSViewModel by viewModels()
    private val rgsViewModel: RGViewModel by viewModels()
    private val vaViewModel: VAViewModel by viewModels()
    private val cmViewModel: CMViewModel by viewModels()
    private lateinit var connectionLiveData: NetworkConectivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = NetworkConectivity(this)
        setContent {
            PliinTheme {
                val isNetworkAvailable = connectionLiveData.observeAsState(false)
                    .value
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigation(isNetworkAvailable)
                }
            }
        }
    }

    @Composable
    fun AppNavigation(isNetworkAvailable: Boolean) {
        val navigationController = rememberNavController()
        NavHost(
            navController = navigationController,
            startDestination = AppScreen.MainLoadScreen.route
        ) {
            //Ruta de Screen principal de carga de la app
            composable(route = AppScreen.MainLoadScreen.route) {
                MainLoadScreen(navigationController, isNetworkAvailable)
            }
            //Ruta de Screen de error de conexion  a internet
            composable(route = AppScreen.FailLoadScreen.route) {
                FailLoadScreen(navigationController)
            }
            //Ruta de Screen de login
            composable(route = AppScreen.LoginScreen.route) {
                LoginScreen(loginViewModel = loginViewModel, navigationController)
            }
            //Ruta de Screen de menu principal de la app
            composable(route = AppScreen.AppMainScreen.route) {
                MainAppScreen(navigationController, mainAppViewModel)
            }
            //Ruta de Screen de Registrar entregas o intentos de entrega de paquetes
            composable(route = AppScreen.RegisterDeliveryScreen.route) {
                RegisterDeliveryScreen(rdViewModel = rdViewModel, navigationController)
            }
            //Ruta de Screen de carga de la entrega de paquetes enlazada la Screen de RegisterDeliveryScreen
            composable(route = AppScreen.LoadingDeliveryScreen.route) {
                LoadingDeliveryScreen(navigationController)
            }
            //Ruta de Screeen de Menu principal de manifiestos
            composable(route = AppScreen.ManifiestoMainScreen.route) {
                ManifiestoMainScreen(navigationController)
            }
            //Ruta de Screen de meenu principal de guias
            composable(route = AppScreen.MenuGuideScreen.route) {
                MenuGuideScreen(navigationController)
            }
            //Ruta de Screen de validacion de guias que lleguan a almacenes foraneos
            composable(route = AppScreen.ValidationArrastreScreen.route) {
                ValidationArrastreScreen(vaViewModel, navigationController)
            }
            //Ruta de Screen para crear un manifiesto
            composable(route = AppScreen.CreateManifestScreen.route) {
                CreateManifestScreen(cmViewModel, navigationController)
            }

            //Ruta de Screen para registrar guias al sistema Pliin que llegan de UPS a Salter
            composable(
                route = AppScreen.ReceptionGuideScreen.route,
                arguments = listOf(navArgument("function") { type = NavType.StringType })
            ) { backStackEntry ->
                ReceptionGuideScreen(
                    rgsViewModel, navigationController,
                    backStackEntry.arguments?.getString("function") ?: ""
                )
            }

            //Ruta de Screen que muiestra la informaciòn de una guia cuando se scanea para eregfsitrarla como entregado o intento de entrega
            composable(route = AppScreen.DataGuideScannerScreen.route,
                arguments = listOf(
                    navArgument("idGuia") { type = NavType.StringType },
                    navArgument("idPreM") { type = NavType.StringType },
                    navArgument("nombre") { type = NavType.StringType },
                    navArgument("direccion") { type = NavType.StringType },
                    navArgument("telefono") { type = NavType.StringType },
                    navArgument("cods") { type = NavType.StringType },
                    navArgument("status") { type = NavType.StringType },
                    navArgument("empresa") { type = NavType.StringType },
                    navArgument("ruta") { type = NavType.StringType },
                    navArgument("pesokg") { type = NavType.StringType },
                    navArgument("valorGuia") { type = NavType.StringType },
                    navArgument("recordId") { type = NavType.StringType },
                    navArgument("statusIntento") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                DataGuideScannerScreen(
                    dgsViewModel = dgsViewModel,
                    navigationController,
                    backStackEntry.arguments?.getString("idGuia") ?: "",
                    backStackEntry.arguments?.getString("idPreM") ?: " ",
                    backStackEntry.arguments?.getString("nombre") ?: "",
                    backStackEntry.arguments?.getString("direccion") ?: "",
                    backStackEntry.arguments?.getString("telefono") ?: "",
                    backStackEntry.arguments?.getString("cods") ?: "",
                    backStackEntry.arguments?.getString("status") ?: "",
                    backStackEntry.arguments?.getString("empresa") ?: "",
                    backStackEntry.arguments?.getString("ruta") ?: "",
                    backStackEntry.arguments?.getString("pesokg") ?: "0",
                    backStackEntry.arguments?.getString("valorGuia") ?: "",
                    backStackEntry.arguments?.getString("recordId") ?: "",
                    backStackEntry.arguments?.getString("statusIntento") ?: ""
                )
            }
        }
    }
}

