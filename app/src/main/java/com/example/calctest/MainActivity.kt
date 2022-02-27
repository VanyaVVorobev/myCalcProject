package com.example.calctest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        for(id in binding.numbersGroup.referencedIds) {
            val currentButton = findViewById<Button>(id)
            currentButton.setOnClickListener{onClickNumber(currentButton.text.toString())}
        }

        for(id in binding.signsGroup.referencedIds) {
            val currentButton = findViewById<Button>(id)
            currentButton.setOnClickListener{onClickSign(currentButton.text.toString())}
        }

        binding.percentButton.setOnClickListener{ onClickPercent() }
        binding.changeSignButton.setOnClickListener{ onClickChangeSign() }
        binding.pointButton.setOnClickListener{ onClickPoint() }
        binding.equalButton.setOnClickListener{ onClickEqual() }
        binding.acButton.setOnClickListener{ screenFlush() }
    }

    private fun onClickNumber(number:String) {
        binding.expressionText.text = myCalc.addNum(binding.expressionText.text.toString(), number)
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
    private fun onClickSign(sign: String) {
        if(myCalc.canPressSign(binding.expressionText.text.toString())) {
            Toast.makeText(applicationContext,
                "this action is not possible", Toast.LENGTH_LONG).show()
        }
        else {
            binding.expressionText.text = myCalc.addSign(binding.expressionText.text.toString(), sign)
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