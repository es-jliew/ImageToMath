package com.essoft.imagetomath.repo

import android.graphics.Bitmap
import android.util.Log
import com.essoft.imagetomath.constants.ParserConstant
import com.essoft.imagetomath.model.ResultModel
import com.essoft.imagetomath.utils.MathParser
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class HomeRepo(private val recognizer: TextRecognizer, private val mathParser: MathParser): IHomeRepo {
    override fun getTextFromCameraImage(bitmap: Bitmap): Flow<String> {
        return callbackFlow {
            val inputImage = InputImage.fromBitmap(bitmap, 0)

            recognizer.process(inputImage)
                .addOnSuccessListener { result ->
                    launch {
                        send(result.text)
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }

            awaitClose {  }
        }
    }

    override fun getTextFromLocalImage(inputImage: InputImage): Flow<ResultModel> {
        return callbackFlow {
            //val inputImage = InputImage.fromBitmap(bitmap, 0)

            Log.d("ML Kit", "Processing...")
            recognizer.process(inputImage)
                .addOnSuccessListener { result ->
                    val blocks = result.textBlocks
                    var isBreak = false

                    for (block in blocks) {
                        if(isBreak){
                            break
                        }

                        for(line in block.lines){
                            val lineText = line.text
                            Log.d("Math Expression", lineText)
                            val resultModel = MathParser.evaluateMathExpression(lineText)

                            if(resultModel.code == ParserConstant.SUCCESS) {
                                Log.d("ML Kit", "Success")
                                launch {
                                    send(resultModel)
                                }
                                isBreak = true
                                break
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    Log.d("ML Kit", "Fail")
                    it.printStackTrace()
                }

            awaitClose {  }
        }
    }
}