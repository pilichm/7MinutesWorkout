package pl.pilichm.a7minutesworkout

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        val toolbarBMIActivity: Toolbar = findViewById(R.id.toolbarBMIActivity)
        val btnCalculateUnits: Button = findViewById(R.id.btnCalculateUnits)
        val etMetricUnitWeight: AppCompatEditText = findViewById(R.id.etMetricUnitWeight)
        val etMetricUnitHeight: AppCompatEditText = findViewById(R.id.etMetricUnitHeight)

        setSupportActionBar(toolbarBMIActivity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "CALCULATE BMI"

        toolbarBMIActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {
            if (validateMetricUnits()){
                val height: Float = etMetricUnitHeight.text.toString().toFloat()/100f
                val weight: Float = etMetricUnitWeight.text.toString().toFloat()

                val bmi = weight / (height*height)
                displayBMIResult(bmi)
            } else {
                Toast.makeText(applicationContext, "Invalid values", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        val etMetricUnitWeight: AppCompatEditText = findViewById(R.id.etMetricUnitWeight)
        val etMetricUnitHeight: AppCompatEditText = findViewById(R.id.etMetricUnitHeight)

        if (etMetricUnitWeight.text.toString().isNullOrEmpty()
            ||etMetricUnitHeight.text.toString().isNullOrEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun displayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String

        val tvYourBMI: TextView = findViewById(R.id.tvYourBMI)
        val tvBMIValue: TextView = findViewById(R.id.tvBMIValue)
        val tvBMIType: TextView = findViewById(R.id.tvBMIType)
        val tvBMIDescription: TextView = findViewById(R.id.tvBMIDescription)

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        tvYourBMI.visibility = View.VISIBLE
        tvBMIValue.visibility = View.VISIBLE
        tvBMIType.visibility = View.VISIBLE
        tvBMIDescription.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        tvBMIValue.text = bmiValue
        tvBMIType.text = bmiLabel
        tvBMIDescription.text = bmiDescription
    }
}