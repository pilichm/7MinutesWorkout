package pl.pilichm.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_back_confirmation.*
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var textToSpeech: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private var exerciseAdaper: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        val toolbarExerciseActivity: Toolbar = findViewById(R.id.toolbar_exercise_activity)
        setSupportActionBar(toolbarExerciseActivity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbarExerciseActivity.setNavigationOnClickListener {
            setUpCustomDialogForBackButton()
        }

        exerciseList = Constants.defaultExerciseList()
        setUpRestView()

        textToSpeech = TextToSpeech(this, this)

        setUpExerciseStatusRecyclerView()

    }

    private fun setRestProgressBar(){
        progressBar.progress = restProgress

        restTimer = object : CountDownTimer(REST_TIMER_DURATION*1000L, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress += 1
                progressBar.progress = REST_TIMER_DURATION - restProgress
                tvTimer.text = (REST_TIMER_DURATION - restProgress).toString()
            }

            override fun onFinish() {
                setUpExerciseView()
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdaper!!.notifyDataSetChanged()
            }
        }.start()
    }

    private fun setUpExerciseProgressBar(){
        exerciseProgress = 0
        val exerciseProgressBar: ProgressBar = findViewById(R.id.progressBarExercise)
        exerciseProgressBar.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(EXERCISE_TIMER_DURATION*1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress += 1
                exerciseProgressBar.progress = EXERCISE_TIMER_DURATION - exerciseProgress
                tvExerciseTimer.text = (EXERCISE_TIMER_DURATION - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList?.size!! - 1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdaper!!.notifyDataSetChanged()
                    setUpRestView()
                } else {
                    finish()
                    val intent = Intent(applicationContext, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    private fun setUpRestView(){
        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        } catch (e: Exception){
            e.printStackTrace()
        }

        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE

        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }

        setRestProgressBar()
        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition+1].getName()
    }

    private fun setUpExerciseView(){
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE

        if (exerciseTimer!=null){
            exerciseTimer!!.cancel()
            restProgress = 0
        }
        setUpExerciseProgressBar()

        currentExercisePosition += 1

        speakOut(exerciseList!![currentExercisePosition].getName())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
    }

    override fun onDestroy() {
        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }

        if (exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if (textToSpeech!=null){
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }

        if (player!=null){
            player!!.stop()
        }

        super.onDestroy()
    }

    companion object {
        private const val REST_TIMER_DURATION = 1
        private const val EXERCISE_TIMER_DURATION = 1
    }

    override fun onInit(status: Int) {
        if (status==TextToSpeech.SUCCESS){
            val result = textToSpeech!!.setLanguage(Locale.US)
            if (result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TextToSpeech", "Specified language not supported!")
            }
        } else {
            Log.e("TextToSpeech", "Initialization failed!")
        }
    }

    private fun speakOut(text: String){
        textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setUpExerciseStatusRecyclerView(){
        rvExerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdaper = ExerciseStatusAdapter(exerciseList!!, this)
        rvExerciseStatus.adapter = exerciseAdaper
    }

    private fun setUpCustomDialogForBackButton(){
        val customDialog = Dialog(applicationContext)
        customDialog.setContentView(R.layout.dialog_back_confirmation)

        buttonYes.setOnClickListener {
            customDialog.dismiss()
            finish()
        }

        buttonNo.setOnClickListener {
            customDialog.dismiss()
        }

        if (!isFinishing){
            customDialog.show()
        }
    }
}