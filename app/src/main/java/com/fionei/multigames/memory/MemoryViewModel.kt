package com.fionei.multigames.memory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MemoryViewModel : ViewModel() {

    private var _massiveOfNumbers = MutableLiveData<List<Int>>()
    val massiveOfNumbers: LiveData<List<Int>>
        get() = _massiveOfNumbers

    //generate size^2 random numbers in 1..size^2
    fun generateNewTable(size: Int) {
        val totalSize = size*size
        _massiveOfNumbers.value = (0..totalSize).shuffled().take(totalSize)
    }

    //generate size^2 random numbers in min..max
    fun generateNewTable(size: Int, min: Int, _max: Int) {
        val totalSize = size*size
        val max =
            if (_max - min > totalSize) _max
            else min + size * size - 1
        _massiveOfNumbers.value = (min..max).shuffled().take(totalSize)
    }
}