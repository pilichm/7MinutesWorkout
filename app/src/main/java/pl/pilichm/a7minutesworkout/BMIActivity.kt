package pl.pilichm.a7minutesworkout

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputLayout
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private val METRIC_UNIT_VIEW = "METRIC_UNIT_VIEW"
    private val US_UNITS_VIEW = "US_UNITS_VIEW"
    private var currentVisibleView: String = METRIC_UNIT_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        val toolbarBMIActivity: Toolbar = findViewById(R.id.toolbarBMIActivity)
        val btnCalculateUnits: Button = findViewById(R.id.btnCalculateUnits)
        val etMetricUnitWeight: AppCompatEditText = findViewById(R.id.etMetricUnitWeight)
        val etMetricUnitHeight: AppCompatEditText = findViewById(R.id.etMetricUnitHeight)
        val rgUnits: RadioGroup = findViewById(R.id.rgUnits)

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
                    val etUSUnitWeight: AppCompatEditText = findViewById(R.id.etUSUnitWeight)
                    val etUSUnitHeightFeet: AppCompatEditText = findViewById(R.id.etUSUnitHeightFeet)
                    val etUSUnitHeightInch: AppCompatEditText = findViewById(R.id.etUSUnitHeightInch)

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

        makeVisibleMetricUnitsView()

        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId==R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUSUnitsView()
            }
        }
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        val etMetricUnitWeight: AppCompatEditText = findViewById(R.id.etMetricUnitWeight)
        val etMetricUnitHeight: AppCompatEditText = findViewById(R.id.etMetricUnitHeight)

        if (etMetricUnitWeight.text.toString().isEmpty()
            ||etMetricUnitHeight.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun validateUSUnits(): Boolean {
        var isValid = true
        val etUSUnitWeight: AppCompatEditText = findViewById(R.id.etUSUnitWeight)
        val etUSUnitHeightFeet: AppCompatEditText = findViewById(R.id.etUSUnitHeightFeet)
        val etUSUnitHeightInch: AppCompatEditText = findViewById(R.id.etUSUnitHeightInch)

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

        val tvBMIValue: TextView = findViewById(R.id.tvBMIValue)
        val tvBMIType: TextView = findViewById(R.id.tvBMIType)
        val tvBMIDescription: TextView = findViewById(R.id.tvBMIDescription)
        val llDisplayBMIResult: LinearLayout = findViewById(R.id.llDisplayBMIResult)

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

    private fun makeVisibleMetricUnitsView(){
        val tilMetricUnitWeight: TextInputLayout = findViewById(R.id.tilMetricUnitWeight)
        val tilMetricUnitHeight: TextInputLayout = findViewById(R.id.tilMetricUnitHeight)

        val tilUSUnitWeight: TextInputLayout = findViewById(R.id.tilUSUnitWeight)
        val llUSUnitsHeight: LinearLayout = findViewById(R.id.llUSUnitsHeight)
        val llDisplayBMIResult: LinearLayout = findViewById(R.id.llDisplayBMIResult)

        val etUSUnitWeight: AppCompatEditText = findViewById(R.id.etUSUnitWeight)
        val etUSUnitHeightFeet: AppCompatEditText = findViewById(R.id.etUSUnitHeightFeet)
        val etUSUnitHeightInch: AppCompatEditText = findViewById(R.id.etUSUnitHeightInch)

        currentVisibleView = METRIC_UNIT_VIEW

        etUSUnitWeight.text!!.clear()
        etUSUnitHeightFeet.text!!.clear()
        etUSUnitHeightInch.text!!.clear()

        tilMetricUnitWeight.visibility = View.VISIBLE
        tilMetricUnitHeight.visibility = View.VISIBLE

        tilUSUnitWeight.visibility = View.GONE
        llUSUnitsHeight.visibility = View.GONE

        llDisplayBMIResult.visibility = View.GONE
    }

    private fun makeVisibleUSUnitsView(){
        val tilMetricUnitWeight: TextInputLayout = findViewById(R.id.tilMetricUnitWeight)
        val tilMetricUnitHeight: TextInputLayout = findViewById(R.id.tilMetricUnitHeight)

        val tilUSUnitWeight: TextInputLayout = findViewById(R.id.tilUSUnitWeight)
        val llUSUnitsHeight: LinearLayout = findViewById(R.id.llUSUnitsHeight)
        val llDisplayBMIResult: LinearLayout = findViewById(R.id.llDisplayBMIResult)

        val etMetricUnitWeight: AppCompatEditText = findViewById(R.id.etMetricUnitWeight)
        val etMetricUnitHeight: AppCompatEditText = findViewById(R.id.etMetricUnitHeight)

        val tilUsUnitHeightFeet: TextInputLayout = findViewById(R.id.tilUsUnitHeightFeet)
        val tilUsUnitHeightInch: TextInputLayout = findViewById(R.id.tilUsUnitHeightInch)

        val llUsUnitsView: LinearLayout = findViewById(R.id.llUsUnitsView)
        llUsUnitsView.visibility = View.VISIBLE

        tilUsUnitHeightFeet.visibility = View.VISIBLE
        tilUsUnitHeightInch.visibility = View.VISIBLE

        currentVisibleView = US_UNITS_VIEW

        etMetricUnitWeight.text!!.clear()
        etMetricUnitHeight.text!!.clear()

        tilMetricUnitWeight.visibility = View.GONE
        tilMetricUnitHeight.visibility = View.GONE

        tilUSUnitWeight.visibility = View.VISIBLE
        llUSUnitsHeight.visibility = View.VISIBLE

        llDisplayBMIResult.visibility = View.GONE
    }
}