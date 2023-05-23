package com.example.stopwatch

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DataSource {

    private val dataBase = DataBase

    fun getData(): Flow<Int> =
        flow {
            while (true) {
                emit(dataBase.getValue())
                delay(1000)
            }
        }.flowOn(Dispatchers.Default)
            .catch {
        Log.d("@@@", "$it")
    }


}