package com.example.calctest

import kotlin.math.floor

class CalcLogic {

    private  fun doSimpleOperation(expression: String):String {
        var buff = expression
        var firstNum = ""
        var secondNum = ""
        var sign = '#'
        for(i in 1 until buff.length) {
            if(((buff[i] < '0')||('9' < buff[i]))&&(buff[i]!='.')&&(buff[i]!='(')&&(buff[i]!=')')) {
                sign = buff[i]
                buff = buff.replaceRange(i, i+1,"#")
                firstNum = buff.substringBefore('#')
                secondNum = buff.substringAfter('#')
                break
            }
        }
        for(i in firstNum.length - 1 downTo  0) {
            if ((firstNum[i] == '(') || (firstNum[i] == ')')) {
                firstNum = firstNum.removeRange(i, i + 1)
            }
        }
        for(i in secondNum.length - 1 downTo 0) {
            if((secondNum[i]=='(')||(secondNum[i]==')')) {
                secondNum = secondNum.removeRange(i,i+1)
            }
        }

        var result = 0.0
        if(sign == '+') {
            result =  (firstNum.toDouble() + secondNum.toDouble()).toDouble()
        }
        if(sign == '-') {
            result =  (firstNum.toDouble() - secondNum.toDouble()).toDouble()
        }
        if(sign == '*') {
            result = (firstNum.toDouble() * secondNum.toDouble()).toDouble()
        }
        if(sign == '/') {
            result =  (firstNum.toDouble() / secondNum.toDouble()).toDouble()
        }

        if(result>=0) {
            return "+$result"
        }
        return result.toString()
    }

    private fun doOperationsByRegex(expression:String, regular:Regex):String {
        var result = expression
        var matchResult = regular.find(result)
        while (matchResult != null) {
            result = result.replaceRange(matchResult.range.first, matchResult.range.last + 1,
                doSimpleOperation(matchResult.value))
            matchResult = regular.find(result)
        }

        return result
    }

    private fun calculateSimpleExpression(expression: String):String {
        var result = expression
        var regular = Regex("([\\-+]?[\\d.]+[*/][\\-+]?[\\d.]+)|([-+]?[\\d.]+\\*\\([+\\-]?[\\d.]+\\))")
        result = doOperationsByRegex(result, regular)
        regular = Regex("[\\-+]?[\\d.]+[\\-+][\\d.]+")
        result = doOperationsByRegex(result, regular)
        return result
    }

    /* I don't know why I wrote it function, but it's there */
    private fun brackets(expression: String):String {
        var result = expression
        val regular = Regex("\\([\\-+]?[\\d.]+([*/+\\-][\\d.]+)+\\)")
        var matchResult = regular.find(result)
        while(matchResult != null) {
            result = result.replaceRange(matchResult.range.first, matchResult.range.last + 1,
                "1*" + calculateSimpleExpression(matchResult.value))
            matchResult = regular.find(result)
        }
        return result
    }

    fun calculate(expression:String):String {
        var result = expression
        result = brackets(result)
        result = calculateSimpleExpression(result)
        if(result[0] == '+') {
            result = result.removeRange(0, 1)
        }
        if((result.toDouble() - floor(result.toDouble())) == 0.0) {
            result = result.substringBefore('.')
        }
        return result
    }
}
