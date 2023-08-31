package com.essoft.imagetomath.utils

import com.essoft.imagetomath.constants.ParserConstant
import com.essoft.imagetomath.model.ResultModel
import com.ezylang.evalex.Expression
import java.math.BigDecimal

object MathParser: IMathParser {
    override fun evaluateMathExpression(mathExpression: String): ResultModel {
        val expression = Expression(formatMathExpression(mathExpression))

        return try {
            expression.validate()
            val result = expression.evaluate()

            ResultModel(ParserConstant.SUCCESS, mathExpression, result.numberValue)
        } catch (e: Exception) {
            e.printStackTrace()
            ResultModel(ParserConstant.FAIL, mathExpression, BigDecimal.ZERO)
        }
    }

    private fun formatMathExpression(rawString: String): String {
        return rawString.replace(" ","")
    }
}