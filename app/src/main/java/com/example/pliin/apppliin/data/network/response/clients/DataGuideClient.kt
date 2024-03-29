package com.example.pliin.apppliin.data.network.response.clients


import com.example.pliin.apppliin.data.model.deliverymodel.GetDataGuideDRModel
import com.example.pliin.apppliin.data.model.queryguide.QueryGuidePliinModel
import com.example.pliin.apppliin.data.model.responseregisterdelivery.ResponseRegisterDeliveryModel
import com.example.pliin.apppliin.data.model.responserudmodel.ResponseRUDModel
import com.example.pliin.apppliin.data.model.responseupdatestatus.ResponseUpdateStatusModel
import com.example.pliin.apppliin.data.network.dto.addguidemanifest.AddGuideToManifest
import com.example.pliin.apppliin.data.network.dto.createguidecod.RegisterGuideCodDto
import com.example.pliin.apppliin.data.network.dto.datospqt.DatosPqtDto
import com.example.pliin.apppliin.data.network.dto.direccionguide.DireccionGuideDto
import com.example.pliin.apppliin.data.network.dto.getGuideCod.GetGuideCodDto
import com.example.pliin.apppliin.data.network.dto.queryguidescanner.createstatus.CreateStatusGuideDto
import com.example.pliin.apppliin.data.network.dto.insertguide.InsertGuideDto
import com.example.pliin.apppliin.data.network.dto.intentoentrega.TryingDeliveryDto
import com.example.pliin.apppliin.data.network.dto.queryguidescanner.QueryGuideDto
import com.example.pliin.apppliin.data.network.dto.queyguidereception.QueryGuidePliinDto
import com.example.pliin.apppliin.data.network.dto.registerdelivery.RegisterDeliveryDto
import com.example.pliin.apppliin.data.network.dto.updatepago.UpdatePagoDto
import com.example.pliin.apppliin.data.network.dto.updatestatus.UpdatStatusDto
import okhttp3.MultipartBody
import okhttp3.Request
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


interface DataGuideClient {

    //EndPoint para consulta una guia en un manifiesto
    @POST("/fmi/data/v2/databases/PLIIN/layouts/GuiasAPI/_find/")
    suspend fun getDataguide(
        @Header("Authorization") bearer: String,
        @Body query: QueryGuideDto,
    ): Response<GetDataGuideDRModel>

    //EndPoint para consulta una guia en ula tabla cods
    @POST("/fmi/data/v2/databases/PLIIN/layouts/CodAPI/_find/")
    suspend fun getGuideCod(
        @Header("Authorization") bearer: String,
        @Body query: GetGuideCodDto,
    ): Response<GetDataGuideDRModel>

    @POST("/fmi/data/v2/databases/PLIIN/layouts/CodAPI/records/")
    suspend fun registerGuideCod(
        @Header("Authorization") bearer: String,
        @Body query: RegisterGuideCodDto,
    ): Response<ResponseRUDModel>

    //EndPoint para consulta una guia en un manifiesto de arrastre
    @POST("/fmi/data/v2/databases/PLIIN/layouts/APITestArratre/_find/")
    suspend fun guideArrastreValidate(
        @Header("Authorization") bearer: String,
        @Body query: QueryGuideDto,
    ): Response<GetDataGuideDRModel>

    //Endpomiont para consultar si la guia existe en el sistema
    @POST("/fmi/data/v2/databases/PLIIN/layouts/guiasSystemAPI/_find/")
    suspend fun queryGuide(
        @Header("Authorization") bearer: String,
        @Body query: QueryGuidePliinDto,
    ): Response<QueryGuidePliinModel>

    //Endpomiont para consultar si la guia tiene registrada una direccion
    @POST("/fmi/data/v2/databases/PLIIN/layouts/DireccionesApi/_find/")
    suspend fun queryGuideDireccion(
        @Header("Authorization") bearer: String,
        @Body query: QueryGuidePliinDto,
    ): Response<QueryGuidePliinModel>

    //Endpomiont para consultar si la guia tiene datos del paquete registrados
    @POST("/fmi/data/v2/databases/PLIIN/layouts/DatosPqtAPI/_find/")
    suspend fun queryGuideDatosPqt(
        @Header("Authorization") bearer: String,
        @Body query: QueryGuidePliinDto,
    ): Response<QueryGuidePliinModel>

    //ENdPoint para crera un registro de entrega o devolucion de una guia
    @POST("/fmi/data/v2/databases/PLIIN/layouts/relacionEntrega/records/")
    suspend fun createDeliveryGuide(
        @Header("Authorization") bearer: String,
        @Body Guide: RegisterDeliveryDto
    ): Response<ResponseRegisterDeliveryModel>

    //ENdPoint para subir una foto en el contenedro de la entrega registrada

   @Multipart
    @POST("/fmi/data/v2/databases/PLIIN/layouts/relacionEntrega/records/{recordId}/containers/{contenedorName}/1")
    suspend fun setDeliveryGuidePhoto(
       @Header("Authorization") bearer: String,
       @Path("recordId") recordId: String,
       @Part upload: MultipartBody.Part,
       @Path("contenedorName")campo: String,
    ): Response<ResponseRegisterDeliveryModel>

    @POST
    suspend fun setDeliveryGuidePhoto2(request: Request): Response<ResponseRegisterDeliveryModel>



    //Enpoint para agregar una guia al sistema
    @POST("/fmi/data/v2/databases/PLIIN/layouts/manifiestoPaquetes/records/")
    suspend fun insertGuide(
        @Header("Authorization") bearer: String,
        @Body Guide: InsertGuideDto
    ): Response<ResponseRUDModel>

    //Enpoint para agregar una guia a un manifiesto
    @POST("/fmi/data/v2/databases/PLIIN/layouts/APIRGuiasManifest/records/")
    suspend fun addGuideManifest(
        @Header("Authorization") bearer: String,
        @Body Guide: AddGuideToManifest
    ): Response<ResponseRUDModel>

    //Enpoint para agregar la direecion de una guia
    @POST("/fmi/data/v2/databases/PLIIN/layouts/direcciones/records/")
    suspend fun addDireccionGuide(
        @Header("Authorization") bearer: String,
        @Body Guide: DireccionGuideDto
    ): Response<ResponseRUDModel>

    //Enpoint para agregar lso datos del paquete medidas y peso
    @POST("/fmi/data/v2/databases/PLIIN/layouts/DatosPaquetes/records/")
    suspend fun addDatosPqtGuide(
        @Header("Authorization") bearer: String,
        @Body Guide: DatosPqtDto
    ): Response<ResponseRUDModel>


    //Enpoint para actualizar el status de una guia
    @PATCH("/fmi/data/v2/databases/PLIIN/layouts/{presentacion}/records/{recordId}/")
    suspend fun updateStatus(
        @Header("Authorization") bearer: String,
        @Path("recordId") recordId: String,
        @Path("presentacion") presentation: String,
        @Body status: UpdatStatusDto
    ): Response<ResponseUpdateStatusModel>

    //Enpoint para actualizar el status de una guia
    @PATCH("/fmi/data/v2/databases/PLIIN/layouts/{presentacion}/records/{recordId}/")
    suspend fun updatePago(
        @Header("Authorization") bearer: String,
        @Path("recordId") recordId: String,
        @Path("presentacion") presentation: String,
        @Body pago: UpdatePagoDto
    ): Response<ResponseUpdateStatusModel>

    //Enpoint para crear un nuevo status de la guia
    @POST("/fmi/data/v2/databases/PLIIN/layouts/estatusGuias/records/")
    suspend fun createStatus(
        @Header("Authorization") bearer: String,
        @Body Guide: CreateStatusGuideDto
    ): Response<ResponseUpdateStatusModel>

    //EndPoint crear un un status de intento de entrega
    @POST("/fmi/data/v2/databases/PLIIN/layouts/IntentosEntregas/records/")
    suspend fun tryDelivery(
        @Header("Authorization") bearer: String,
        @Body query: TryingDeliveryDto
    ): Response<ResponseUpdateStatusModel>


}

