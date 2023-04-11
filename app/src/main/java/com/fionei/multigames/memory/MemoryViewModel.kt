package com.fionei.multigames.memory

import android.os.CountDownTimer
import android.view.View.OnClickListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fionei.multigames.tools.maxTime
import com.fionei.multigames.tools.win

class MemoryViewModel : ViewModel() {
    private var _massiveOfNumbers = MutableLiveData<List<Int>>()
    val massiveOfNumbers: LiveData<List<Int>>
        get() = _massiveOfNumbers

    private var priorityList: MutableList<Int> = mutableListOf()

    private var _currentPriority: MutableLiveData<Int> = MutableLiveData(0)
    val currentPriority: LiveData<Int>
        get() = _currentPriority

    private var _timerTime = MutableLiveData<Long>(0)
    val timerTime: LiveData<Long> = _timerTime
    private var isTimerActive: Boolean = false
    private var isTimerStopped: Boolean = true

    fun generateNewTable(size: Int) {
        val totalSize = size * size
        priorityList = (1..totalSize).take(totalSize).toMutableList()
        _massiveOfNumbers.value = priorityList.shuffled()
        _currentPriority.value = priorityList[0]
        resetTimer()
    }

    fun isCorrectClick(value: Int): Boolean {
        return value == _currentPriority.value
    }

    fun onClick(value: Int): OnClickListener = OnClickListener {
        if (value == currentPriority.value) {
            priorityList.remove(currentPriority.value)
            if (priorityList.size == 0) {
                _currentPriority.value = win
            } else {
                _currentPriority.value = priorityList[0]
            }
        }
    }

    fun generateNewTable(size: Int, min: Int, _max: Int) {
        val totalSize = size * size
        val max =
            if (_max - min > totalSize) _max
            else min + size * size - 1
        _massiveOfNumbers.value = (min..max).shuffled().take(totalSize)
    }

    //the lower values -> the worse timer's work
    val interval = 19L
    fun startTimer() {
        if (isTimerActive) return
        if (!isTimerStopped) {
            Thread.sleep(interval)
        }
        isTimerActive = true
        isTimerStopped = false
        object : CountDownTimer(maxTime * 1000, interval) {
            override fun onTick(millisUntilFinished: Long) {
                if (isTimerActive) _timerTime.value = _timerTime.value?.plus(interval)
                else onFinish()
            }

            override fun onFinish() {
                this.cancel()
                isTimerStopped = true
            }
        }.start()
    }

    fun stopTimer() {
        isTimerActive = false
    }

    private fun resetTimer() {
        _timerTime.value = 0
        startTimer()
    }
}