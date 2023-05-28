package com.example.stopwatch.interactor

import com.example.stopwatch.model.data.entity.StopwatchState
import com.example.stopwatch.model.repository.ITimestampRepository

class ElapsedTimeCalculator(private val timestampRepository: ITimestampRepository) {

    fun calculateElapsedTime(state: StopwatchState.Running): Long {
        val currentITimestamp = timestampRepository.getCurrentMilliseconds()
        val timePassedSinceStart = if (currentITimestamp > state.startTime) {
            currentITimestamp - state.startTime
        } else
            0
        return timePassedSinceStart + state.elapsedTime
    }

}