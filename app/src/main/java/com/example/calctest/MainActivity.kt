package com.example.calctest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.calctest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myCalc = CalcLogic()
    private var isPointClicked = false
    private var isSignLast = false
    private var isPointLast = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for(id in binding.buttonsGroup.referencedIds) {
            val currentButton = findViewById<Button>(id)
            currentButton.setOnClickListener(listener)
        }

    }

    val listener = View.OnClickListener { view->
        when(view.id) {
            binding.oneButton.id -> onClickNumber(view)
            binding.twoButton.id -> onClickNumber(view)
            binding.threeButton.id -> onClickNumber(view)
            binding.fourButton.id -> onClickNumber(view)
            binding.fiveButton.id -> onClickNumber(view)
            binding.sixButton.id -> onClickNumber(view)
            binding.sevenButton.id -> onClickNumber(view)
            binding.eightButton.id -> onClickNumber(view)
            binding.nineButton.id -> onClickNumber(view)
            binding.zeroButton.id -> onClickNumber(view)

            binding.plusButton.id -> onClickSign(view)
            binding.minusButton.id -> onClickSign(view)
            binding.dividerButton.id -> onClickSign(view)
            binding.multiplyButton.id -> onClickSign(view)

            binding.percentButton.id -> onClickPercent()
            binding.changeSignButton.id -> onClickChangeSign()
            binding.pointButton.id -> onClickPoint()
            binding.equalButton.id -> onClickEqual()
            binding.acButton.id -> screenFlush()
        }
    }

    fun onClickNumber(view: View) {
        val buff = binding.expressionText.text.toString() + (view as Button).text
        binding.expressionText.text = buff

        isSignLast = false
        isPointLast = false
    }

    fun onClickPoint() {
        if((binding.expressionText.text.isEmpty())||(isSignLast)||(isPointClicked)) {
            Toast.makeText(applicationContext,
                "this action is not possible", Toast.LENGTH_LONG).show()
        }
        else {
            val buff = binding.expressionText.text.toString() + "."
            binding.expressionText.text = buff

            isPointLast = true
            isPointClicked = true
        }
    }

    fun onClickSign(view: View) {
        if((binding.expressionText.text.isEmpty())||(isSignLast)||(isPointLast)) {
            Toast.makeText(applicationContext,
                "this action is not possible", Toast.LENGTH_LONG).show()
        }
        else {
            val buff = binding.expressionText.text.toString() + (view as Button).text
            binding.expressionText.text = buff

            isSignLast = true
            isPointClicked = false
        }
    }

    fun onClickChangeSign() {
        if((binding.expressionText.text.isEmpty())||(isSignLast)) {
            Toast.makeText(applicationContext,
                "this action is not possible", Toast.LENGTH_LONG).show()
        }
        else {
            val regular = Regex("\\(?[+\\-]?[0-9.]+\\)?$")
            val matchResult = regular.find(binding.expressionText.text)
            if(matchResult != null) {
                val buff: String
                if(matchResult.value[0]=='-') {
                    buff = matchResult.value.substringAfter('-')
                    binding.expressionText.text =
                        binding.expressionText.text.replaceRange(matchResult.range.first,
                            matchResult.range.last + 1, "+$buff")
                }
                else {
                    buff = matchResult.value.substringAfter('+')
                    binding.expressionText.text =
                        binding.expressionText.text.replaceRange(matchResult.range.first,
                            matchResult.range.last + 1, "-$buff")
                }
            }
            if(binding.expressionText.text[0] == '+') {
                var buff = binding.expressionText.text.toString()
                buff = buff.substringAfter('+')
                binding.expressionText.text = buff
            }
        }
    }

    fun onClickPercent() {
        if((binding.expressionText.text.isEmpty())||(isSignLast)||(isPointLast)) {
            Toast.makeText(applicationContext,
                "this action is not possible", Toast.LENGTH_LONG).show()
        }
        else {
            val buff = binding.expressionText.text.toString() + "/100"
            binding.expressionText.text = buff

            isPointClicked = false
        }
    }

    fun screenFlush() {
        binding.expressionText.text = ""

        isPointClicked = false
        isSignLast = false
        isPointLast = false
    }

    fun onClickEqual() {
        if((isPointLast)||(isSignLast)) {
            Toast.makeText(applicationContext,
                "this action is not possible", Toast.LENGTH_LONG).show()
        }
        else {
            binding.expressionText.text = myCalc.calculate(binding.expressionText.text.toString())
            if (binding.expressionText.text.contains('.')) {
                isPointClicked = true
            }
        }
    }
}