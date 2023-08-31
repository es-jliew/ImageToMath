package com.essoft.imagetomath.repo

import android.graphics.Bitmap
import com.essoft.imagetomath.model.ResultModel
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.flow.Flow

interface IHomeRepo {
    fun getTextFromCameraImage(bitmap: Bitmap): Flow<String>

    fun getTextFromLocalImage(inputImage: InputImage): Flow<ResultModel>
}