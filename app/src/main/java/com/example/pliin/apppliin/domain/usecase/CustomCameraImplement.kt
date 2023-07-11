package com.example.pliin.apppliin.domain.usecase

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pliin.apppliin.domain.repository.CustomCameraX
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class CustomCameraImplement @Inject constructor(
    private val cameraProvider: ProcessCameraProvider,
    private val selector: CameraSelector,
    private val preview: Preview,
    private val imageAnalysis: ImageAnalysis,
    private val imageCapture: ImageCapture
) : CustomCameraX {

    private val _directory = MutableLiveData<String>()
    val directory: LiveData<String> = _directory

    override suspend fun captureAndSaveImage(
        context: Context
    ) {
        val directorioDestino = File(context.getExternalFilesDir(null), "/storage/")
        if (!directorioDestino.exists()) {
            directorioDestino.mkdirs()
        }

        val name = SimpleDateFormat(
            "yyyy-MM-dd-HH-mm-ss-SSS",
            Locale.ENGLISH
        ).format(System.currentTimeMillis())
        /* val rutaCarpetaPictures = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).absolutePath
         val directorioDestino = File(context.getExternalFilesDir(null), rutaCarpetaPictures)*/
        val archivoDestino = File(directorioDestino, "$name.jpg")
        _directory.value = archivoDestino.absolutePath
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATA, archivoDestino.absolutePath)
            Log.i("Ruta foto", archivoDestino.absolutePath)
            /* if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P){
                 put(MediaStore.Images.Media.RELATIVE_PATH,"Pictures/Pliin")
             }else{

             }*/
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Toast.makeText(
                        context,
                        "Saved image ${outputFileResults.savedUri!!}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        context,
                        "some error occurred ${exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }

    override suspend fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    ) {
        preview.setSurfaceProvider(previewView.surfaceProvider)
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                selector,
                preview,
                imageAnalysis,
                imageCapture
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getDirectoryPhoto(): String {
        return directory.value!!
    }
}