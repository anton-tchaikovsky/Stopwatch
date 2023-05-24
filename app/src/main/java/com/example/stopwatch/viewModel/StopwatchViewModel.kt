package com.example.stopwatch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.stopwatch.interactor.StopwatchStateOrchestrator

class StopwatchViewModel(
    private val stopwatchStateOrchestrator: StopwatchStateOrchestrator = StopwatchStateOrchestrator()
) : ViewModel() {

    val tickerLiveData: LiveData<String> = stopwatchStateOrchestrator.ticker.asLiveData()

    fun onStart() {
        stopwatchStateOrchestrator.start()
    }

    fun onPause() {
        stopwatchStateOrchestrator.pause()
    }

    fun onStop() {
        stopwatchStateOrchestrator.stop()
    }

    fun onReset() {
        stopwatchStateOrchestrator.clearValue()
    }

}