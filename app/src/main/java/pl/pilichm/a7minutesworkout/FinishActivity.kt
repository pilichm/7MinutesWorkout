package pl.pilichm.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar

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
    }
}