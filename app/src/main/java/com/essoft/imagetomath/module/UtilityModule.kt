package com.essoft.imagetomath.module

import com.essoft.imagetomath.utils.IMathParser
import com.essoft.imagetomath.utils.MathParser
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import org.koin.dsl.bind
import org.koin.dsl.module

val utilityModule = module {
    single { MathParser } bind (IMathParser::class)
    single { provideTextRecognizer() }
}

fun provideTextRecognizer(): TextRecognizer {
    return TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
}
