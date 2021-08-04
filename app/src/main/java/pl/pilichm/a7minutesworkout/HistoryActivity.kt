package pl.pilichm.a7minutesworkout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(toolbarHistoryActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "HISTORY"

        toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        rgHistoryType.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId==R.id.rbHistoryList){
                llHistoryListView.visibility = View.VISIBLE
                tvChart.visibility = View.GONE
            } else {
                llHistoryListView.visibility = View.GONE
                tvChart.visibility = View.VISIBLE
            }
        }

        getAllCompletedDays()
    }

    private fun getAllCompletedDays(){
        val handler = SqlLiteOpenHelper(this, null)
        val completedDaysArrList = handler.getAllCompletedDays()

        if (completedDaysArrList.isNotEmpty()){
            tvHistory.visibility = View.VISIBLE
            rvHistory.visibility = View.VISIBLE
            tvNoDataAvailable.visibility = View.GONE

            rvHistory.layoutManager = LinearLayoutManager(this)
            rvHistory.adapter = HistoryAdapter(this, completedDaysArrList)
        } else {
            tvHistory.visibility = View.GONE
            rvHistory.visibility = View.GONE
            tvNoDataAvailable.visibility = View.VISIBLE
        }
    }
}