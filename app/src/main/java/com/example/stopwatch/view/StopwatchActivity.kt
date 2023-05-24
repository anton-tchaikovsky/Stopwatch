package com.example.stopwatch.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.stopwatch.R
import com.example.stopwatch.databinding.ActivityStopwatchBinding
import com.example.stopwatch.utils.TimestampMillisecondsFormatter
import com.example.stopwatch.viewModel.StopwatchViewModel


class StopwatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStopwatchBinding

    private val stopwatchViewModel: StopwatchViewModel by lazy {
        ViewModelProvider(this)[StopwatchViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopwatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView(savedInstanceState)

        stopwatchViewModel.tickerLiveData.observe(this){
            renderData(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // сохраняем состояние buttonStartReset (если reset)
        if (binding.buttonStartReset.text==getString(R.string.reset))
            outState.putCharSequence(KEY_RESET, binding.buttonStartReset.text)
        super.onSaveInstanceState(outState)
    }

    private fun renderData(textTime: String) {
        binding.textTime.text = textTime
    }

    private fun initView(savedInstanceState: Bundle?) {
        binding.run {
            // восстанавливаем состояние buttonStartReset
            savedInstanceState?.let{
                if (it.getCharSequence(KEY_RESET)==getString(R.string.reset))
                    buttonStartReset.text = getString(R.string.reset)
            }
            buttonStartReset.setOnClickListener {
                if (buttonStartReset.text == getString(R.string.reset)){
                    buttonStartReset.text = getString(R.string.start)
                    stopwatchViewModel.onReset()
                } else
                    stopwatchViewModel.onStart()
            }
            buttonPause.setOnClickListener {
                stopwatchViewModel.onPause()
            }
            buttonStop.setOnClickListener {
                stopwatchViewModel.onStop()
                if(textTime.text!=TimestampMillisecondsFormatter.DEFAULT_TIME)
                    buttonStartReset.text = getString(R.string.reset)
            }
        }
    }

    companion object{
        private const val KEY_RESET = "KeyReset"
    }

}