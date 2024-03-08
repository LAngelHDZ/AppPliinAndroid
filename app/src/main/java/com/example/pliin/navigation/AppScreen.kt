package com.example.pliin.navigation

sealed class AppScreen(val route:String){

    object MainLoadScreen : AppScreen("MainLoadScreen")
    object FailLoadScreen : AppScreen("FailLoadScreen")
    object LoginScreen : AppScreen("LoginScreen")
    object AppMainScreen : AppScreen("MainAppScreen/{nameEmployee}/{area}"){
        fun createRoute(
            nameEmployee:String,
            area:String
        )= "MainAppScreen/$nameEmployee/$area"
    }

    object EditManifestScreen : AppScreen("EditManifestScreen/{nameEmployee}/{idRecord}/{route}/{claveManifest}"){
        fun createRoute(
            nameEmployee: String,
            idRecord:String,
            route:String,
            claveManifest:String,
        )= "EditManifestScreen/$nameEmployee/$idRecord/$route/$claveManifest"
    }

    object ValidateManifestScreen : AppScreen("ValidateManifestScreen/{nameEmployee}/{idRecord}/{route}/{claveManifest}"){
        fun createRoute(
            nameEmployee: String,
            idRecord:String,
            route:String,
            claveManifest:String,
        )= "ValidateManifestScreen/$nameEmployee/$idRecord/$route/$claveManifest"
    }
    object ValidationArrastreScreen : AppScreen("ValidationArrastreScreen")
    object ReceptionGuideScreen : AppScreen("ReceptionGuideScreen/{function}") {
        fun createRoute(
            function: String
        ) = "ReceptionGuideScreen/$function"
    }

    /*  object ManifiestoMainScreen : AppScreen("ManifiestoMainScreen"){
          fun createRoute(
              area: String
          )="ManifiestoMainScreen/$area"
      }*/

    object ManifiestoMainScreen : AppScreen("ManifiestoMainScreen")
    object ReasignacionGuideScreen : AppScreen("ReasignacionGuideScreen")
    object ManifestScreen : AppScreen("ManifestScreen")
    object MenuGuideScreen : AppScreen("MenuGuideScreen")
    object CreateManifestScreen : AppScreen("CreateManifestScreen/{area}") {
        fun createRoute(
            area: String
        ) = "CreateManifestScreen/$area"
    }

    object RegisterDeliveryScreen : AppScreen("RegisterDeliveryScreen")
    object LoadingDeliveryScreen : AppScreen("LoadingDeliveryScreen")
    object DataGuideScannerScreen :
        AppScreen("DataGuideScannerScreen/{idGuia}/{idPreM}/{nombre}/{direccion}/{telefono}/{cods}/{status}/{empresa}/{ruta}/{pesokg}/{valorGuia}/{recordId}/{statusIntento}") {
        fun createRoute(
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
        ) =
            "DataGuideScannerScreen/$idGuia/$idPreM/$nombre/$direccion/$telefono/$cods/$status/$empresa/$ruta/$pesokg/$valorGuia/$recordId/$statusIntento"
    }
}