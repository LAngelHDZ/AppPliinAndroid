package com.example.pliin.apppliin.domain.repository

import android.content.Context
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner

interface CustomCameraX {

    suspend fun captureAndSaveImage(context: Context)

    suspend fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    )

    suspend fun getDirectoryPhoto(): String
}