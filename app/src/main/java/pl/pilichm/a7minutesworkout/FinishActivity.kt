package pl.pilichm.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        val toolbarFinishActivity: Toolbar = findViewById(R.id.toolbarFinishActivity)
        val btnFinish: Button = findViewById(R.id.btnFinish)

        setSupportActionBar(toolbarFinishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btnFinish.setOnClickListener {
            onBackPressed()
        }

        btnFinish.setOnClickListener {
            finish()
        }

        addDateToDB()
    }

    private fun addDateToDB(){
        val calendar = Calendar.getInstance()
        val dataTime = calendar.time
        Log.i("FinishActivity", "ADDING $dataTime")

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dataTime)
        val handler = SqlLiteOpenHelper(this, null)
        handler.addDate(date)
        Log.i("FinishActivity", "Date added")
    }
}