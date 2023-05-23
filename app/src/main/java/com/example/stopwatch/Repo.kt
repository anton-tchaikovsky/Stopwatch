package com.example.stopwatch

import kotlinx.coroutines.flow.map

class Repo {
    private val dataSource = DataSource()

    fun getData()=
        dataSource.getData().map {
            Data(it.toString())
        }

}