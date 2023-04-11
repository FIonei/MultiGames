package com.fionei.multigames.memory

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fionei.multigames.databinding.FragmentMemoryBinding
import com.fionei.multigames.tools.*
import java.lang.Integer.min

class MemoryFragment : Fragment() {
    private lateinit var memoryBinding: FragmentMemoryBinding
    private val viewModel: MemoryViewModel by viewModels()

    //temporary
    private val tableSize = 5 //TODO: get this from settings

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        memoryBinding = FragmentMemoryBinding.inflate(inflater, container, false)
        memoryBinding.root.post {
            val size: Int = min(memoryBinding.root.measuredHeight, memoryBinding.root.measuredWidth)
            memoryBinding.mainTable.layoutParams.height = size
            memoryBinding.mainTable.layoutParams.width = size
            memoryBinding.root.requestLayout()
        }
        //TODO: add ability to change min/max values from settings
        viewModel.generateNewTable(tableSize)
        return memoryBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.massiveOfNumbers.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                memoryBinding.mainTable.setTable(tableSize, list)
                for (value in list) {
                    memoryBinding.mainTable.setOnCellClick(
                        list.indexOf(value),
                        viewModel.isCorrectClick(value),
                        viewModel.onClick(value)
                    )
                }
            }
        }
        viewModel.currentPriority.observe(viewLifecycleOwner) {
            if (it == win) {
                startWin()
            } else {
                val position = viewModel.massiveOfNumbers.value?.indexOf(it)
                if (position != null) {
                    memoryBinding.mainTable.setOnCellClick(position, true, viewModel.onClick(it))
                }
            }
        }
        viewModel.timerTime.observe(viewLifecycleOwner) {
            memoryBinding.timer.text = it.toTimerTime
        }
    }

    private fun startWin() {
        viewModel.stopTimer()
        openDialog(viewModel.timerTime.value ?: -10)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startTimer()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopTimer()
    }

    private fun openDialog(time: Long) {
        if (time < 0) {
            //TODO: show dialog to user with error text
            throw Exception("something went wrong with timer")
        }
        val normalTime: String = time.toNormalTime
        val preferences = requireContext().getSharedPreferences(RECORDS, MODE_PRIVATE)
        val memoryKey = MEMORY_RECORD_KEY + '_' + tableSize
        val recordTime: Long = preferences.getLong(memoryKey, maxTime)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        //TODO: add translations
        dialog.apply {
            setTitle("Congratulations!!")
            val text = "You won $tableSize x $tableSize table in $normalTime.\n"
            val subText = if (time < recordTime) {
                with(preferences.edit()) {
                    putLong(memoryKey, time)
                    apply()
                }
                "Now your personal best is $normalTime"
            } else {
                "Your personal best is still ${recordTime.toNormalTime}"
            }
            setMessage(text + subText)
            setPositiveButton("Play again") { dialog, _ ->
                dialog.dismiss()
                viewModel.generateNewTable(tableSize)
            }
            setNegativeButton("Back to menu") { dialog, _ ->
                dialog.dismiss()
                findNavController().popBackStack()
            }
        }
        dialog.show()
    }
}