package com.example.stopwatch.interactor

import com.example.stopwatch.model.data.entity.StopwatchState
import com.example.stopwatch.utils.TimestampMillisecondsFormatter

class StopwatchStateHolder(
    private val stopwatchStateCalculator: StopwatchStateCalculator = StopwatchStateCalculator(),
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter = TimestampMillisecondsFormatter()
) {

    private val elapsedTimeCalculator = stopwatchStateCalculator.elapsedTimeCalculator

    private var currentState: StopwatchState = StopwatchState.Paused(0)

    fun start(){
        currentState = stopwatchStateCalculator.calculateRunningState(currentState)
    }

    fun pause(){
        currentState = stopwatchStateCalculator.calculatePausedState(currentState)
    }

    fun stop(){
        currentState = StopwatchState.Paused(0)
    }

    fun getStringTimeRepresentation(): String{
        val elapsedTime = when (val currentState = currentState) {
            is StopwatchState.Paused -> currentState.elapsedTime
            is StopwatchState.Running ->
                elapsedTimeCalculator.calculateElapsedTime(currentState)
        }
            return timestampMillisecondsFormatter.format(elapsedTime)
    }

}