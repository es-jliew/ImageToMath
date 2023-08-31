package com.essoft.imagetomath.ui.presentation.homeScreen

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.essoft.imagetomath.model.ResultModel
import com.essoft.imagetomath.repo.HomeRepo
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepo: HomeRepo): ViewModel() {
    //UI state
    private var _extractedText = MutableStateFlow(ResultModel("", "", null))
    val extractedText = _extractedText.asStateFlow()

    fun getTextFromCapturedImage(bitmap: Bitmap) {
        viewModelScope.launch {
            homeRepo
                .getTextFromCameraImage(bitmap)
                .collect { text ->
                    //extractedText.value = text
                }
        }
    }

    fun getTextFromLocalImage(inputImage: InputImage) {
        viewModelScope.launch {
            homeRepo
                .getTextFromLocalImage(inputImage)
                .collect { resultModel ->
                    _extractedText.value = resultModel
                    Log.d("Math", "Math Expression: ${resultModel.mathExpression}")
                    Log.d("Math", "Math Result: ${resultModel.mathExpressionResult}")
                }
        }
    }
}