package com.example.challengechapter2

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.challengechapter2.databinding.ActivityMainBinding
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculator.setOnClickListener { calculateTip() }
        binding.etInput.setOnKeyListener{view, keyCode, _ -> handleKeyEvent(view, keyCode)}
        binding.tvTipAmount.text = "Tip Amount : $"
    }

    private fun calculateTip() {
        val stringInTextField = binding.etInput.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        val tipPercentage = when {
            binding.rbAmazing.isChecked -> cost * 0.2
            binding.rbGood.isChecked -> cost * 0.18
            else -> cost * 0.15
        }

        var tip = tipPercentage

        if (binding.swTip.isChecked) {
            tip = ceil(tip)
        }
        displayTip(tip)
    }

    private fun displayTip(tip: Double) {
        binding.tvTipAmount.text = getString(R.string.tip_amount, tip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if(keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}