package com.example.stopwatch.model.data_source

class TimestampProvider: ITimestampProvider {

    override fun getCurrentMilliseconds(): Long = System.currentTimeMillis()

}