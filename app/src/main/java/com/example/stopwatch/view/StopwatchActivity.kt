package com.example.stopwatch.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.stopwatch.R
import com.example.stopwatch.databinding.ActivityStopwatchBinding
import com.example.stopwatch.databinding.StopwatchViewBinding
import com.example.stopwatch.model.data.entity.Timer
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

        stopwatchViewModel.listLiveData.forEach {
            it.value.observe(this) { textTime ->
                renderData(it.key, textTime)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // сохраняем состояние buttonStartReset
        outState.putCharSequenceArrayList(
            KEY_STATE_START_RESET,
            arrayListOf(binding.firstStopwatchView.buttonStartReset.text,
            binding.secondStopwatchView.buttonStartReset.text)
        )
        super.onSaveInstanceState(outState)
    }

    private fun renderData(timer: Timer, textTime: String) {
        when (timer) {
            Timer.FIRST -> binding.firstStopwatchView.textTime.text = textTime
            Timer.SECOND -> binding.secondStopwatchView.textTime.text = textTime
        }
    }

    private fun initView(savedInstanceState: Bundle?) {
        // восстанавливаем состояние buttonStartReset
        savedInstanceState?.getCharSequenceArrayList(KEY_STATE_START_RESET).let {
            binding.firstStopwatchView.buttonStartReset.text = it?.get(0) ?: getString(R.string.start)
            binding.secondStopwatchView.buttonStartReset.text = it?.get(1) ?: getString(R.string.start)
        }

        binding.firstStopwatchView.initStopwatchView(Timer.FIRST)
        binding.secondStopwatchView.initStopwatchView(Timer.SECOND)
    }

    private fun StopwatchViewBinding.initStopwatchView(timer: Timer) {
        buttonStartReset.setOnClickListener {
            if (buttonStartReset.text == getString(R.string.reset)) {
                buttonStartReset.text = getString(R.string.start)
                stopwatchViewModel.onReset(timer)
            } else
                stopwatchViewModel.onStart(timer)
        }
        buttonPause.setOnClickListener {
            stopwatchViewModel.onPause(timer)
        }
        buttonStop.setOnClickListener {
            stopwatchViewModel.onStop(timer)
            if (textTime.text != TimestampMillisecondsFormatter.DEFAULT_TIME)
                buttonStartReset.text = getString(R.string.reset)
        }
    }

    companion object {
        private const val KEY_STATE_START_RESET = "KeyStateStartReset"
    }

}