package pl.pilichm.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val toolbarHistoryActivity: Toolbar = findViewById(R.id.toolbarHistoryActivity)
        setSupportActionBar(toolbarHistoryActivity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "HISTORY"

        toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        getAllCompletedDays()
    }

    private fun getAllCompletedDays(){
        val handler = SqlLiteOpenHelper(this, null)
        val completedDaysArrList = handler.getAllCompletedDays()
        val tvHistory: TextView = findViewById(R.id.tvHistory)
        val rvHistory: RecyclerView = findViewById(R.id.rvHistory)
        val tvNoDataAvailable: TextView = findViewById(R.id.tvNoDataAvailable)

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