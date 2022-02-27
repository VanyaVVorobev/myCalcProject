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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for(id in binding.buttonsGroup.referencedIds) {
            val currentButton = findViewById<Button>(id)
            currentButton.setOnClickListener(listener)
        }

    }

    private val listener = View.OnClickListener { view->
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

    private fun onClickNumber(view: View) {
        binding.expressionText.text = myCalc.addNum(binding.expressionText.text.toString(), view)
    }
    private fun onClickPoint() {
        if(myCalc.canPressPoint(binding.expressionText.text.toString())) {
            Toast.makeText(applicationContext,
                "this action is not possible", Toast.LENGTH_LONG).show()
        }
        else {
            binding.expressionText.text = myCalc.addPoint(binding.expressionText.text.toString())
        }
    }
    private fun onClickSign(view: View) {
        if(myCalc.canPressSign(binding.expressionText.text.toString())) {
            Toast.makeText(applicationContext,
                "this action is not possible", Toast.LENGTH_LONG).show()
        }
        else {
            binding.expressionText.text = myCalc.addSign(binding.expressionText.text.toString(), view)
        }
    }
    private fun onClickChangeSign() {
        if(myCalc.canPressChangerSign(binding.expressionText.text.toString())) {
            Toast.makeText(applicationContext,
                "this action is not possible", Toast.LENGTH_LONG).show()
        }
        else {
            binding.expressionText.text = myCalc.changeSignLastNum(binding.expressionText.text.toString())
        }
    }

    private fun onClickPercent() {
        if(myCalc.canPressPercent(binding.expressionText.text.toString())) {
            Toast.makeText(applicationContext,
                "this action is not possible", Toast.LENGTH_LONG).show()
        }
        else {
            binding.expressionText.text = myCalc.addPercent(binding.expressionText.text.toString())
        }
    }

    private fun screenFlush() {
        binding.expressionText.text = myCalc.screenFlush()
    }

    private fun onClickEqual() {
        if(myCalc.canPressEqual()) {
            Toast.makeText(applicationContext,
                "this action is not possible", Toast.LENGTH_LONG).show()
        }
        else {
            binding.expressionText.text = myCalc.addEqual(binding.expressionText.text.toString())
        }
    }
}