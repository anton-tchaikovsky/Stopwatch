package com.example.stopwatch.interactor

import com.example.stopwatch.model.data.entity.StopwatchState
import com.example.stopwatch.model.repository.ITimestampRepository
import com.example.stopwatch.model.repository.TimestampRepository

class StopwatchStateCalculator(
    private val timestampRepository: ITimestampRepository = TimestampRepository(),
    val elapsedTimeCalculator: ElapsedTimeCalculator = ElapsedTimeCalculator(timestampRepository)
) {

    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running {
        return when (oldState) {
            is StopwatchState.Paused -> StopwatchState.Running(
                timestampRepository.getCurrentMilliseconds(),
                oldState.elapsedTime
            )

            is StopwatchState.Running -> oldState
        }
    }

    fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused {
        return when (oldState) {
            is StopwatchState.Paused -> oldState
            is StopwatchState.Running -> StopwatchState.Paused(
                elapsedTimeCalculator.calculateElapsedTime(
                    oldState
                )
            )
        }
    }
}