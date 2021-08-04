package pl.pilichm.a7minutesworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bmiactivity.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private val METRIC_UNIT_VIEW = "METRIC_UNIT_VIEW"
    private val US_UNITS_VIEW = "US_UNITS_VIEW"
    private var currentVisibleView: String = METRIC_UNIT_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        setSupportActionBar(toolbarBMIActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "CALCULATE BMI"

        toolbarBMIActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {
            if (currentVisibleView==METRIC_UNIT_VIEW){
                if (validateMetricUnits()){
                    val height: Float = etMetricUnitHeight.text.toString().toFloat()/100f
                    val weight: Float = etMetricUnitWeight.text.toString().toFloat()

                    val bmi = weight / (height*height)
                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(applicationContext, "Invalid values", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (validateUSUnits()){
                    val weight  = etUSUnitWeight.text.toString().toFloat()
                    val heightFeet = etUSUnitHeightFeet.text.toString()
                    val heightInches = etUSUnitHeightInch.text.toString()

                    val height = heightInches.toFloat() + heightFeet.toFloat() * 12f
                    val bmi = (703f * weight)/(height)
                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(applicationContext, "Invalid values", Toast.LENGTH_SHORT).show()
                }
            }
        }

        makeVisibleBMIView(true)

        rgUnits.setOnCheckedChangeListener { _, checkedId ->
            makeVisibleBMIView(checkedId==R.id.rbMetricUnits)
        }
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (etMetricUnitWeight.text.toString().isEmpty()
            ||etMetricUnitHeight.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun validateUSUnits(): Boolean {
        var isValid = true

        if (etUSUnitWeight.text.toString().isEmpty()
            ||etUSUnitHeightFeet.text.toString().isEmpty()
            ||etUSUnitHeightInch.text.toString().isEmpty()){
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

        llDisplayBMIResult.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        tvBMIValue.text = bmiValue
        tvBMIType.text = bmiLabel
        tvBMIDescription.text = bmiDescription
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

        etUSUnitWeight.text!!.clear()
        etUSUnitHeightFeet.text!!.clear()
        etUSUnitHeightInch.text!!.clear()
        etMetricUnitWeight.text!!.clear()
        etMetricUnitHeight.text!!.clear()

        tilMetricUnitWeight.visibility = metricVisibility
        tilMetricUnitHeight.visibility = metricVisibility

        tilUsUnitHeightFeet.visibility = usVisibility
        tilUsUnitHeightInch.visibility = usVisibility
        tilUSUnitWeight.visibility = usVisibility
        llUSUnitsHeight.visibility = usVisibility
        llUsUnitsView.visibility = usVisibility

        llDisplayBMIResult.visibility = View.GONE
    }
}