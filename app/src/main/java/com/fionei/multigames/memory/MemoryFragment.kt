package com.fionei.multigames.memory

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.SharedPreferences
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

class MemoryFragment(/*private val tableSize: Int, private val isAlwaysVisible: Boolean*/) :
    Fragment() {
    private lateinit var memoryBinding: FragmentMemoryBinding
    private val viewModel: MemoryViewModel by viewModels()

    //temporary
    private val tableSize = 3

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
    }

    private fun startWin() {
        stopTimer()
        openDialog(maxTime - 1)
    }

    private fun stopTimer() {

    }

    private fun openDialog(time: Int) {
        val normalTime: String = time.toNormalTime
        val preferences = requireContext().getSharedPreferences(RECORDS, MODE_PRIVATE)
        with(preferences.edit()) {
            putInt(MEMORY_RECORD_KEY, maxTime)
            apply()
        }
        val recordTime: Int = preferences.getInt(MEMORY_RECORD_KEY, maxTime)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        dialog.apply {
            setTitle("Congratulations!!")
            val text = "You won $tableSize x $tableSize table in $normalTime.\n"
            val subText = if (time < recordTime) {
                with(preferences.edit()) {
                    putInt(MEMORY_RECORD_KEY, time)
                    apply()
                }
                "Now your personal best is $normalTime"
            } else {
                "Your personal best is still ${recordTime.toNormalTime}"
            }
            setMessage(text + subText)
            setPositiveButton("Play again") { dialog, which ->
                dialog.dismiss()
                viewModel.generateNewTable(tableSize)
            }
            setNegativeButton("Back to menu") { dialog, which ->
                dialog.dismiss()
                findNavController().popBackStack()
            }
        }
        dialog.show()
    }

}