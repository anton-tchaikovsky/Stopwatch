package com.example.stopwatch.model.repository

import com.example.stopwatch.model.data_source.ITimestampProvider
import com.example.stopwatch.model.data_source.TimestampProvider

class TimestampRepository (private val timestampProvider: ITimestampProvider = TimestampProvider()): ITimestampRepository {

    override fun getCurrentMilliseconds(): Long =
        timestampProvider.getCurrentMilliseconds()

}