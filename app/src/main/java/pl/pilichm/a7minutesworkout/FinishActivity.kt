package pl.pilichm.a7minutesworkout

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import pl.pilichm.a7minutesworkout.databinding.ActivityFinishBinding
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFinishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnFinish.setOnClickListener {
            onBackPressed()
        }

        binding.btnFinish.setOnClickListener {
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