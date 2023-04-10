package com.fionei.multigames.memory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fionei.multigames.databinding.FragmentMemoryBinding
import java.lang.Integer.min

class MemoryFragment(/*private val tableSize: Int, private val isAlwaysVisible: Boolean*/) :
    Fragment() {
    private lateinit var memoryBinding: FragmentMemoryBinding
    private val viewModel: MemoryViewModel by viewModels()

    //temporary
    private val tableSize = 5

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
        viewModel.massiveOfNumbers.observe(viewLifecycleOwner) {
            memoryBinding.mainTable.setTable(tableSize, it)
        }
    }

}