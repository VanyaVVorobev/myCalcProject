package com.example.calctest

import kotlin.math.floor

class CalcLogic {
    private var isPointClicked = false
    private var isSignLast = false
    private var isPointLast = false

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
            result =  firstNum.toDouble() + secondNum.toDouble()
        }
        if(sign == '-') {
            result =  firstNum.toDouble() - secondNum.toDouble()
        }
        if(sign == '*') {
            result = firstNum.toDouble() * secondNum.toDouble()
        }
        if(sign == '/') {
            result =  firstNum.toDouble() / secondNum.toDouble()
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

    private fun calculate(expression:String):String {
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

    fun changeSignLastNum (expression: String):String {
        var result = expression
        val regular = Regex("\\(?[+\\-]?[0-9.]+\\)?$")
        val matchResult = regular.find(result)
        if(matchResult != null) {
            val buff: String
            if(matchResult.value[0]=='-') {
                buff = matchResult.value.substringAfter('-')
                result =
                    result.replaceRange(matchResult.range.first,
                        matchResult.range.last + 1, "+$buff")
            }
            else {
                buff = matchResult.value.substringAfter('+')
                result =
                    result.replaceRange(matchResult.range.first,
                        matchResult.range.last + 1, "-$buff")
            }
        }
        if(result[0] == '+') {
            var buff = result
            buff = buff.substringAfter('+')
            result = buff
        }

        return result
    }

    fun canPressPoint (expression: String):Boolean = ((expression.isEmpty())||(isSignLast)||(isPointClicked))
    fun canPressSign (expression: String):Boolean = ((expression.isEmpty())||(isSignLast)||(isPointLast))
    fun canPressChangerSign (expression: String):Boolean = ((expression.isEmpty())||(isSignLast))
    fun canPressEqual ():Boolean = ((isPointLast)||(isSignLast))
    fun canPressPercent(expression: String):Boolean = ((expression.isEmpty())||(isSignLast)||(isPointLast))

    fun addNum (expression: String, number:String):String {
        val result = expression + number
        isSignLast = false
        isPointLast = false
        return result
    }
    fun addPoint (expression: String):String {
        val result = "$expression."
        isPointLast = true
        isPointClicked = true
        return result
    }
    fun addSign (expression: String, sign:String): String {
        val result = expression + sign
        isSignLast = true
        isPointClicked = false
        return result
    }
    fun addPercent (expression: String):String {
        val result = "$expression/100"
        isPointClicked = false
        return result
    }
    fun screenFlush():String {
        val result = ""
        isPointClicked = false
        isSignLast = false
        isPointLast = false
        return result
    }
    fun addEqual(expression: String):String {
        val result =  calculate(expression)
        if (expression.contains('.')) {
            isPointClicked = true
        }
        return result
    }

}
