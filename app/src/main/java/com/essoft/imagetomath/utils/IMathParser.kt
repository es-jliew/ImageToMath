package com.essoft.imagetomath.utils

import com.essoft.imagetomath.model.ResultModel

interface IMathParser {
    fun evaluateMathExpression(mathExpression: String): ResultModel
}