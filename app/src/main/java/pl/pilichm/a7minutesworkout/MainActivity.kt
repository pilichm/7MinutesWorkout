package pl.pilichm.a7minutesworkout

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val llStart: LinearLayout = findViewById(R.id.ll_start)
        val llBMI: LinearLayout = findViewById(R.id.llBMI)
        val llHistory: LinearLayout = findViewById(R.id.llHistory)

        llStart.setOnClickListener {
            startActivity(Intent(applicationContext, ExerciseActivity::class.java))
        }

        llBMI.setOnClickListener {
            startActivity(Intent( applicationContext, BMIActivity::class.java))
        }

        llHistory.setOnClickListener {
            startActivity(Intent(applicationContext, HistoryActivity::class.java))
        }
    }
}