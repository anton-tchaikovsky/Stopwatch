package com.example.stopwatch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.stopwatch.interactor.StopwatchStateOrchestrator
import com.example.stopwatch.model.data.entity.Timer

class StopwatchViewModel(
    private val stopwatchStateOrchestrator: StopwatchStateOrchestrator = StopwatchStateOrchestrator()
) : ViewModel() {

    val listLiveData: Map<Timer,LiveData<String>> = createListLiveData()

    private fun createListLiveData(): Map<Timer,LiveData<String>> {
       val listLiveData = mutableMapOf<Timer,LiveData<String>>()
        stopwatchStateOrchestrator.getListTicker().forEach {
            listLiveData[it.key] = it.value.asLiveData()
        }
       return listLiveData
    }

    fun onStart(timer: Timer) {
        stopwatchStateOrchestrator.start(timer)
    }

    fun onPause(timer: Timer) {
        stopwatchStateOrchestrator.pause(timer)
    }

    fun onStop(timer: Timer) {
        stopwatchStateOrchestrator.stop(timer)
    }

    fun onReset(timer: Timer) {
        stopwatchStateOrchestrator.clearValue(timer)
    }

}