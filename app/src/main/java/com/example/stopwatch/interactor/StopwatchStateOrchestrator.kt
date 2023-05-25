package com.example.stopwatch.interactor

import com.example.stopwatch.model.data.entity.Timer
import com.example.stopwatch.utils.TimestampMillisecondsFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class StopwatchStateOrchestrator(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
) {

    private val listStopwatchStateHolder: MutableMap<Timer, StopwatchStateHolder> =
        mutableMapOf()
    private val listJob: MutableMap<Timer, Job?> =
        mutableMapOf()
    private val listMutableTicker: MutableMap<Timer, MutableStateFlow<String>> = createLitMutableTicker()

    fun start(timer: Timer) {
       listStopwatchStateHolder.getOrPut(timer){
            StopwatchStateHolder()
        }.start()
        val job = listJob.getOrPut(timer) {
            startJob(timer)
        }
        if (job == null)
            listJob.replace(timer, startJob(timer))
    }

    private fun startJob(timer: Timer) =
        scope.launch {
            while (isActive) {
                delay(20)
                listMutableTicker[timer]?.value =
                listStopwatchStateHolder[timer]?.getStringTimeRepresentation()?:TimestampMillisecondsFormatter.DEFAULT_TIME
            }
        }

    fun pause(timer: Timer) {
        listStopwatchStateHolder[timer]?.pause()
        listJob[timer]?.cancel()
        listJob.replace(timer, null)
    }

    fun stop(timer: Timer) {
        listStopwatchStateHolder[timer]?.stop()
        listStopwatchStateHolder.remove(timer)
        listJob[timer]?.cancel()
        listJob.remove(timer)

    }

    private fun createLitMutableTicker(): MutableMap<Timer, MutableStateFlow<String>> {
        val listTicker: MutableMap<Timer, MutableStateFlow<String>> = mutableMapOf()
        Timer.values().forEach {
            listTicker[it] = MutableStateFlow(TimestampMillisecondsFormatter.DEFAULT_TIME)
        }
        return listTicker
    }

     fun getListTicker(): Map<Timer,StateFlow<String>> = listMutableTicker

    fun clearValue(timer: Timer) {
        listMutableTicker[timer]?.value = TimestampMillisecondsFormatter.DEFAULT_TIME
    }

}


