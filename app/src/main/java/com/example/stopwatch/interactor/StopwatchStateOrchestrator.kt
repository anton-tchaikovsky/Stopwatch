package com.example.stopwatch.interactor

import com.example.stopwatch.utils.TimestampMillisecondsFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class StopwatchStateOrchestrator(
    private val stopwatchStateHolder: StopwatchStateHolder = StopwatchStateHolder(),
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
) {

    private var job: Job? = null
    private val mutableTicker = MutableStateFlow(TimestampMillisecondsFormatter.DEFAULT_TIME)
    val ticker: StateFlow<String> = mutableTicker

    fun start() {
        if (job == null)
            startJob()
        stopwatchStateHolder.start()
    }

    private fun startJob() {
        job = scope.launch {
            while (isActive) {
                delay(20)
                mutableTicker.value =
                    stopwatchStateHolder.getStringTimeRepresentation()
            }
        }
    }

    fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
    }

    fun clearValue() {
        mutableTicker.value = TimestampMillisecondsFormatter.DEFAULT_TIME
    }

    private fun stopJob() {
        scope.coroutineContext.cancelChildren()
        job = null
    }

}