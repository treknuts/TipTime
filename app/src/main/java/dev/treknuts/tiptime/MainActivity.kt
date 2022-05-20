package dev.treknuts.tiptime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import dev.treknuts.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize app with no tip
        binding.satisfactionIcon.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_24)
        binding.satisfactionIcon.contentDescription =
            R.string.icon_option_fifteen_percent_hint.toString()
        displayTip(0.0)

        // Update tip when text input changes
        binding.costOfServiceEditText.addTextChangedListener {
            calculateTip()
        }

        // update tip when percentage changes
        binding.tipOptions.setOnCheckedChangeListener { _, _ ->  calculateTip() }

        // update tip when rounding option changes
        binding.switchRoundUp.setOnCheckedChangeListener { _, _ -> calculateTip() }
    }

    private fun calculateTip(){
        val cost = binding.costOfServiceEditText.text.toString().toDoubleOrNull()

        if (cost == null) {
            binding.tipAmount.text = ""
            displayTip(0.0)
            return
        }

        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> {
                binding.satisfactionIcon.setImageResource(R.drawable.ic_baseline_sentiment_very_satisfied_24)
                binding.satisfactionIcon.contentDescription =
                    R.string.icon_option_twenty_percent_hint.toString()
                0.20
            }
            R.id.option_eighteen_percent -> {
                binding.satisfactionIcon.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)
                binding.satisfactionIcon.contentDescription =
                    R.string.icon_option_eighteen_percent_hint.toString()
                0.18
            }
            else -> {
                binding.satisfactionIcon.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_24)
                binding.satisfactionIcon.contentDescription =
                    R.string.icon_option_fifteen_percent_hint.toString()
                0.15
            }
        }

        var tip = cost * tipPercentage
        if (binding.switchRoundUp.isChecked) {
            tip = ceil(tip)
        }

        displayTip(tip)
    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipAmount.text = getString(R.string.tip_amount, formattedTip)
    }
}