package pl.pilichm.a7minutesworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pl.pilichm.a7minutesworkout.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiactivityBinding
    private val METRIC_UNIT_VIEW = "METRIC_UNIT_VIEW"
    private val US_UNITS_VIEW = "US_UNITS_VIEW"
    private var currentVisibleView: String = METRIC_UNIT_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBMIActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "CALCULATE BMI"

        binding.toolbarBMIActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnCalculateUnits.setOnClickListener {
            if (currentVisibleView==METRIC_UNIT_VIEW){
                if (validateMetricUnits()){
                    val height: Float = binding.etMetricUnitHeight.text.toString().toFloat()/100f
                    val weight: Float = binding.etMetricUnitWeight.text.toString().toFloat()

                    val bmi = weight / (height*height)
                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(applicationContext, "Invalid values", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (validateUSUnits()){
                    val weight  = binding.etUSUnitWeight.text.toString().toFloat()
                    val heightFeet = binding.etUSUnitHeightFeet.text.toString()
                    val heightInches = binding.etUSUnitHeightInch.text.toString()

                    val height = heightInches.toFloat() + heightFeet.toFloat() * 12f
                    val bmi = (703f * weight)/(height)
                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(applicationContext, "Invalid values", Toast.LENGTH_SHORT).show()
                }
            }
        }

        makeVisibleBMIView(true)

        binding.rgUnits.setOnCheckedChangeListener { _, checkedId ->
            makeVisibleBMIView(checkedId==R.id.rbMetricUnits)
        }
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding.etMetricUnitWeight.text.toString().isEmpty()
            ||binding.etMetricUnitHeight.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun validateUSUnits(): Boolean {
        var isValid = true

        if (binding.etUSUnitWeight.text.toString().isEmpty()
            ||binding.etUSUnitHeightFeet.text.toString().isEmpty()
            ||binding.etUSUnitHeightInch.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun displayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String

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

        binding.llDisplayBMIResult.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding.tvBMIValue.text = bmiValue
        binding.tvBMIType.text = bmiLabel
        binding.tvBMIDescription.text = bmiDescription
    }

    private fun makeVisibleBMIView(showMetricUnits: Boolean){
        val metricVisibility: Int
        val usVisibility: Int

        if (showMetricUnits){
            currentVisibleView = METRIC_UNIT_VIEW
            metricVisibility = View.VISIBLE
            usVisibility = View.GONE
        } else {
            currentVisibleView = US_UNITS_VIEW
            metricVisibility = View.GONE
            usVisibility = View.VISIBLE
        }

        binding.etUSUnitWeight.text!!.clear()
        binding.etUSUnitHeightFeet.text!!.clear()
        binding.etUSUnitHeightInch.text!!.clear()
        binding.etMetricUnitWeight.text!!.clear()
        binding.etMetricUnitHeight.text!!.clear()

        binding.tilMetricUnitWeight.visibility = metricVisibility
        binding.tilMetricUnitHeight.visibility = metricVisibility

        binding.tilUsUnitHeightFeet.visibility = usVisibility
        binding.tilUsUnitHeightInch.visibility = usVisibility
        binding.tilUSUnitWeight.visibility = usVisibility
        binding.llUSUnitsHeight.visibility = usVisibility
        binding.llUsUnitsView.visibility = usVisibility

        binding.llDisplayBMIResult.visibility = View.GONE
    }
}