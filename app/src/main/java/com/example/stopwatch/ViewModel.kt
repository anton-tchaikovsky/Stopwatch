package com.example.stopwatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class ViewModel: ViewModel() {
    private val repo = Repo()

    val liveData: LiveData<Data> = repo.getData().asLiveData()

}