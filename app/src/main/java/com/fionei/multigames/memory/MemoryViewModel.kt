package com.fionei.multigames.memory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MemoryViewModel : ViewModel() {

    private var _massiveOfNumbers = MutableLiveData<List<Int>>()
    val massiveOfNumbers: LiveData<List<Int>>
        get() = _massiveOfNumbers

    fun generateNewTable(size: Int) {
        val totalSize = size*size
        _massiveOfNumbers.value = (1.. totalSize).shuffled().take(totalSize)
    }

    fun generateNewTable(size: Int, min: Int, _max: Int) {
        val totalSize = size*size
        val max =
            if (_max - min > totalSize) _max
            else min + size * size - 1
        _massiveOfNumbers.value = (min..max).shuffled().take(totalSize)
    }
}