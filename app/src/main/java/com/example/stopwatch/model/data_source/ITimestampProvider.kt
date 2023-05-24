package com.example.stopwatch.model.data_source

interface ITimestampProvider {

    fun getCurrentMilliseconds(): Long

}