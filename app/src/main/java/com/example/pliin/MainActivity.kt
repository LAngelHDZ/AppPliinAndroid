package com.example.pliin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
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
import com.example.pliin.apppliin.ui.mainloading.MLViewModel
import com.example.pliin.apppliin.ui.mainloading.MainLoadScreen
import com.example.pliin.apppliin.ui.manifest.ManifiestoMainScreen
import com.example.pliin.apppliin.ui.manifest.createmanifest.CMViewModel
import com.example.pliin.apppliin.ui.manifest.createmanifest.CreateManifestScreen
import com.example.pliin.apppliin.ui.manifest.editmanifest.EditManifestScreen
import com.example.pliin.apppliin.ui.manifest.mymanifest.MFViewModel
import com.example.pliin.apppliin.ui.manifest.mymanifest.ManifestScreen
import com.example.pliin.apppliin.ui.manifest.reasignacionguide.ReasignacionGuideScreen
import com.example.pliin.apppliin.ui.planeation.planetionday.PDViewModel
import com.example.pliin.apppliin.ui.planeation.planetionday.PlaneationDayScreen
import com.example.pliin.apppliin.ui.planeation.viewmanifestplaneation.VMFViewModel
import com.example.pliin.apppliin.ui.planeation.viewmanifestplaneation.ViewManifestScreen
import com.example.pliin.apppliin.ui.registerdelivery.RDViewModel
import com.example.pliin.apppliin.ui.registerdelivery.RegisterDeliveryScreen
import com.example.pliin.navigation.AppScreen
import com.example.pliin.ui.theme.PliinTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val mainAppViewModel: MainAppViewModel by viewModels()
    private val mlViewModel: MLViewModel by viewModels()
    private val rdViewModel: RDViewModel by viewModels()
    private val dgsViewModel: DGSViewModel by viewModels()
    private val rgsViewModel: RGViewModel by viewModels()
    private val vaViewModel: VAViewModel by viewModels()
    private val cmViewModel: CMViewModel by viewModels()
    private val mfViewModel: MFViewModel by viewModels()
    private val pdViewModel: PDViewModel by viewModels()
    private val vmfViewModel: VMFViewModel by viewModels()
    private lateinit var connectionLiveData: NetworkConectivity

    private val requestPermissionLauncher =
       registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
           if (isGranted) {
               Toast.makeText(this,"Permiso concedido", Toast.LENGTH_SHORT).show()
           } else {
               Toast.makeText(this,"Permiso denegado, la app necesita de este permiso para su correcta funcionalidad", Toast.LENGTH_SHORT).show()
           }
       }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE

        if (!isWritePermissionGranted()) {
            requestPermissionLauncher.launch(permission)
        }

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

    fun isWritePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
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
                MainLoadScreen(navigationController, isNetworkAvailable, mlViewModel)
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
            composable(route = AppScreen.AppMainScreen.route,
            arguments = listOf(
                navArgument("nameEmployee") { type = NavType.StringType },
                navArgument("area") {type = NavType.StringType},
            )
            ) {backStackEntry ->
                MainAppScreen(navigationController,
                    backStackEntry.arguments?.getString("nameEmployee") ?: "",
                    backStackEntry.arguments?.getString("area") ?: "",
                    mainAppViewModel
                )
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
            composable(
                route = AppScreen.ManifiestoMainScreen.route,
            ) {
                ManifiestoMainScreen(navigationController)
            }

            //Ruta de Screeen de la planeaciones del dia
            composable(
                route = AppScreen.PlaneationDayScreen.route,
                arguments = listOf(
                    navArgument("folioManifest") { type = NavType.StringType },
                    navArgument("route") { type = NavType.StringType },
                    navArgument("totaguides") { type = NavType.StringType },
                    navArgument("idRecord") { type = NavType.StringType },
                    )
            ) { backStackEntry ->
                PlaneationDayScreen(navigationController,pdViewModel,
                    backStackEntry.arguments?.getString("folioManifest") ?: "",
                    backStackEntry.arguments?.getString("ruta") ?: "",
                    backStackEntry.arguments?.getString("totalguides") ?: "",
                    backStackEntry.arguments?.getString("idrecord") ?: ""
                    )
            }

            //Ruta de Screen de meenu principal de guias
            composable(route = AppScreen.MenuGuideScreen.route) {
                MenuGuideScreen(navigationController)
            }

            //Ruta de Screen de meenu principal de guias
            composable(route = AppScreen.ViewManifiestoScreen.route) {
                ViewManifestScreen(navigationController,vmfViewModel)
            }

            //Ruta para redirigirse a la screen de reasignación de guias
            composable(route= AppScreen.ReasignacionGuideScreen.route){
                ReasignacionGuideScreen(navigationController)
            }
            //Ruta de Screen de validacion de guias que lleguan a almacenes foraneos
            composable(route = AppScreen.ValidationArrastreScreen.route) {
                ValidationArrastreScreen(vaViewModel, navigationController)
            }
            //Ruta de Screen para crear un manifiesto
            composable(
                route = AppScreen.CreateManifestScreen.route,
                arguments = listOf(navArgument("area") { type = NavType.StringType })
            ) { backStackEntry ->
                CreateManifestScreen(
                    navigationController,
                    backStackEntry.arguments?.getString("area") ?: "",
                    cmViewModel
                )
            }

            //Ruta de Screen para editar un manifiesto
            composable(
                route = AppScreen.EditManifestScreen.route,
                arguments = listOf(
                    navArgument("nameEmployee") { type = NavType.StringType },
                    navArgument("idRecord") { type = NavType.StringType },
                    navArgument("route") { type = NavType.StringType },
                    navArgument("claveManifest") { type = NavType.StringType },
                )
            ) { backStackEntry ->
                EditManifestScreen(
                    navigationController,
                    backStackEntry.arguments?.getString("nameEmployee") ?: "",
                    backStackEntry.arguments?.getString("idRecord") ?: "",
                    backStackEntry.arguments?.getString("route") ?: "",
                    backStackEntry.arguments?.getString("claveManifest") ?: ""
                )
            }

            composable(route = AppScreen.ManifestScreen.route) {
                ManifestScreen(navigationController, mfViewModel)
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
            composable(
                route = AppScreen.DataGuideScannerScreen.route,
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
                ),
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
                    backStackEntry.arguments?.getString("statusIntento") ?: "",
                )
            }
        }
    }
}

