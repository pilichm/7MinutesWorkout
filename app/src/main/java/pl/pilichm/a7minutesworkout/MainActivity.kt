package pl.pilichm.a7minutesworkout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.pilichm.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.llStart.setOnClickListener {
            startActivity(Intent(applicationContext, ExerciseActivity::class.java))
        }

        binding.llBMI.setOnClickListener {
            startActivity(Intent( applicationContext, BMIActivity::class.java))
        }

        binding.llHistory.setOnClickListener {
            startActivity(Intent(applicationContext, HistoryActivity::class.java))
        }
    }
}