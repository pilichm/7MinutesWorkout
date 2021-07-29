package pl.pilichm.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar

class ExerciseActivity : AppCompatActivity() {
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePostion = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        val toolbarExerciseActivity = findViewById<Toolbar>(R.id.toolbar_exercise_activity)
        setSupportActionBar(toolbarExerciseActivity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbarExerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        exerciseList = Constants.defaultExerciseList()
        setUpRestView()
    }

    private fun setRestProgressBar(){
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvTimer = findViewById<TextView>(R.id.tvTimer)
        progressBar.progress = restProgress

        restTimer = object : CountDownTimer(REST_TIMER_DURATION*1000L, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress += 1
                progressBar.progress = REST_TIMER_DURATION - restProgress
                tvTimer.text = (REST_TIMER_DURATION - restProgress).toString()
            }

            override fun onFinish() {
                setUpExerciseView()
            }
        }.start()
    }

    private fun setUpExerciseProgressBar(){
        val exerciseProgressBar = findViewById<ProgressBar>(R.id.progressBarExercise)
        val tvExerciseTimer = findViewById<TextView>(R.id.tvExerciseTimer)
        exerciseProgressBar.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(EXERCISE_TIMER_DURATION*1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress += 1
                exerciseProgressBar.progress = EXERCISE_TIMER_DURATION - exerciseProgress
                tvExerciseTimer.text = (EXERCISE_TIMER_DURATION - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePostion < exerciseList?.size!! - 1){
                    setUpRestView()
                } else {
                    Toast.makeText(applicationContext, "END", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun setUpRestView(){
        val llRestView = findViewById<LinearLayout>(R.id.llRestView)
        val llExerciseView = findViewById<LinearLayout>(R.id.llExerciseView)
        val tvUpcomingExerciseName = findViewById<TextView>(R.id.tvUpcomingExerciseName)

        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE

        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }

        setRestProgressBar()
        tvUpcomingExerciseName.text = exerciseList!![currentExercisePostion+1].getName()
    }

    private fun setUpExerciseView(){
        val llRestView = findViewById<LinearLayout>(R.id.llRestView)
        val llExerciseView = findViewById<LinearLayout>(R.id.llExerciseView)
        val ivImage = findViewById<ImageView>(R.id.ivImage)
        val tvExerciseName = findViewById<TextView>(R.id.tvExerciseName)

        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE

        if (exerciseTimer!=null){
            exerciseTimer!!.cancel()
            restProgress = 0
        }
        setUpExerciseProgressBar()

        currentExercisePostion += 1
        tvExerciseName.text = exerciseList!![currentExercisePostion].getName()
        ivImage.setImageResource(exerciseList!![currentExercisePostion].getImage())
    }

    override fun onDestroy() {
        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }
        super.onDestroy()
    }

    companion object {
        private const val REST_TIMER_DURATION = 10
        private const val EXERCISE_TIMER_DURATION = 30
    }
}