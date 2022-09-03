package pl.pilichm.a7minutesworkout

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import pl.pilichm.a7minutesworkout.Constants.Companion.DB_DATE_FORMAT
import pl.pilichm.a7minutesworkout.databinding.ActivityHistoryBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.math.abs

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistoryActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "HISTORY"

        binding.toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.rgHistoryType.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId== R.id.rbHistoryList){
                binding.llHistoryListView.visibility = View.VISIBLE
                binding.lineChart.visibility = View.GONE
            } else {
                binding.llHistoryListView.visibility = View.GONE
                binding.lineChart.visibility= View.VISIBLE

                setUpChart()
            }
        }

        getAllCompletedDays()
    }

    private fun getAllCompletedDays(){
        val handler = SqlLiteOpenHelper(this, null)
        val completedDaysArrList = handler.getAllCompletedDays()

        if (completedDaysArrList.isNotEmpty()){
            binding.tvHistory.visibility = View.VISIBLE
            binding.rvHistory.visibility = View.VISIBLE
            binding.tvNoDataAvailable.visibility = View.GONE

            binding.rvHistory.layoutManager = LinearLayoutManager(this)
            binding.rvHistory.adapter = HistoryAdapter(this, completedDaysArrList)
        } else {
            binding.tvHistory.visibility = View.GONE
            binding.rvHistory.visibility = View.GONE
            binding.tvNoDataAvailable.visibility = View.VISIBLE
        }
    }

    private fun setUpChart(){
        binding.lineChart.setViewPortOffsets(0f, 0f, 0f, 0f)
        binding.lineChart.setBackgroundColor(Color.rgb(104, 241, 175))
        binding.lineChart.description.isEnabled = false
        binding.lineChart.setTouchEnabled(false)
        binding.lineChart.isDragEnabled = true
        binding.lineChart.setScaleEnabled(true)
        binding.lineChart.setPinchZoom(false)
        binding.lineChart.setDrawGridBackground(false)
        binding.lineChart.maxHighlightDistance = 300f

        val x = binding.lineChart.xAxis
        x.isEnabled = false

        val y = binding.lineChart.axisLeft
        y.setLabelCount(6, false)
        y.textColor = Color.WHITE
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        y.setDrawGridLines(false)
        y.axisLineColor = Color.WHITE

        binding.lineChart.axisRight.isEnabled = false
        binding.lineChart.legend.isEnabled = false

        val vl = getChartDataset(false)

        binding.lineChart.data = LineData(vl)

    }

    /**
     * Function returns values that will be displayed in activity history chart.
     * Can return actual or mock data for development and testing.
     */
    private fun getChartDataset(returnMockData:Boolean=false): LineDataSet {
        val values = ArrayList<Entry>()
        val label = if (returnMockData) "mock" else "actual"

        if (returnMockData) {
            for (i in 1..10){
                values.add(Entry(i.toFloat(), i%3.toFloat()))
            }
        } else {
            val handler = SqlLiteOpenHelper(this, null)
            val completedDaysArrList = handler.getAllCompletedDays()
            val formatter = SimpleDateFormat(DB_DATE_FORMAT)
            val startCalendar = Calendar.getInstance()
            val endCalendar = Calendar.getInstance()

            startCalendar.time = formatter.parse(completedDaysArrList[0]) ?: Date()
            endCalendar.time = formatter.parse(completedDaysArrList[completedDaysArrList.size-1]) ?: Date()

            val numberOfDays = calcTimeDifferenceInDays(startCalendar, endCalendar)
            println("Number of days: $numberOfDays")

            for (currentDay in 0..numberOfDays) {
                var numOfWorkouts = 0
                println("OUT $currentDay")
                for (value in completedDaysArrList){
                    val currentCalendar = Calendar.getInstance()
                    currentCalendar.time = formatter.parse(value) ?: Date()
                    val days = calcTimeDifferenceInDays(startCalendar, currentCalendar)

                    if (days==currentDay) {
                        numOfWorkouts += 1
                    }

                    values.add(Entry(currentDay.toFloat(), numOfWorkouts.toFloat()))
                }

            }
        }

        return createLineDataSet(values, label)
    }

    /**
     * Method return time difference in days between two Calendar objects.
     */
    private fun calcTimeDifferenceInDays(sDate: Calendar, eDate: Calendar): Long {
        val tDiff = abs(eDate.time.time - sDate.time.time)
        return TimeUnit.DAYS.convert(tDiff, TimeUnit.MILLISECONDS)
    }

    /**
     * Function creates line data set from passed values.
     */
    private fun createLineDataSet(entries: ArrayList<Entry>, label: String): LineDataSet {
        val vl = LineDataSet(entries, label)
        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.black
        vl.fillAlpha = R.color.design_default_color_background

        return vl
    }
}