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

        llStart.setOnClickListener {
            val intent = Intent( this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        llBMI.setOnClickListener {
            val intent = Intent( this, BMIActivity::class.java)
            startActivity(intent)
        }
    }
}