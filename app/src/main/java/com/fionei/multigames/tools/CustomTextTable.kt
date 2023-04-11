package com.fionei.multigames.tools

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.fionei.multigames.R
import com.fionei.multigames.databinding.CustomTextTableBinding
import com.fionei.multigames.databinding.TableItemTextBinding
import java.lang.Integer.min

class CustomTextTable(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var binding: CustomTextTableBinding =
        CustomTextTableBinding.inflate(LayoutInflater.from(context), this, false)

    private var listOfCells: MutableList<TableItemTextBinding> = mutableListOf()
    private var tableSize = 0

    init {
        addView(binding.root)
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextTable, 0, 0
        )

        tableSize = typedArray.getInteger(R.styleable.CustomTextTable_tableSize, 0)

        binding.root.post {
            val totalTableSize = min(binding.root.measuredWidth, binding.root.measuredHeight)
            val cellSize = kotlin.math.floor(totalTableSize.toDouble() / tableSize).toInt()
            binding.apply {
                for (line in 0 until tableSize) {//add columns
                    val lineView = LinearLayout(context)
                    for (column in 0 until tableSize) {
                        val cell = TableItemTextBinding.inflate(LayoutInflater.from(context))
                        cell.root.layoutParams = FrameLayout.LayoutParams(cellSize, cellSize)
                        listOfCells.add(cell)
                        lineView.addView(cell.root)
                    }
                    root.addView(lineView)
                }
                root.requestLayout()
            }
        }
    }

    fun setTable(newSize: Int, values: List<Any>) {
        if (tableSize == newSize) {
            setTable(values)
        } else {
            tableSize = newSize
            val density = Resources.getSystem().displayMetrics.density


            binding.apply {
                root.removeAllViews()
                listOfCells.clear()
                for (line in 0 until tableSize) {//add columns
                    val lineView = LinearLayout(context)
                    for (column in 0 until tableSize) {
                        val cell = TableItemTextBinding.inflate(LayoutInflater.from(context))
                        listOfCells.add(cell)
                        lineView.addView(cell.root)
                    }
                    root.addView(lineView)
                }
                setTextInTable(values)
            }

            binding.root.post {
                val totalTableSize = min(binding.root.measuredWidth, binding.root.measuredHeight)
                val cellSize = kotlin.math.floor(totalTableSize.toDouble() / tableSize).toInt()
                layoutParams.width = cellSize * tableSize + 4 * density.toInt()
                layoutParams.height = cellSize * tableSize + 4 * density.toInt()
                for (cell in listOfCells) {
                    cell.root.layoutParams = LayoutParams(cellSize, cellSize)
                    cell.root.requestLayout()
                }
            }
        }
    }

    private fun setTable(newValues: List<Any>) {
        clearAllCells()
        setTextInTable(newValues)
        binding.root.requestLayout()
    }

    fun setCellClickListener(line: Int, column: Int, onClick: OnClickListener) {
        val cellPosition = line * tableSize + column
        if (cellPosition >= listOfCells.size) return
        listOfCells[cellPosition].root.setOnClickListener(onClick)
    }

    private fun setTextInTable(list: List<Any>) {
        for (i in list.indices) {
            try {
                val text = list[i].toString()
                listOfCells[i].textCell.text = text
            } catch (e: Exception) {
                break
            }
        }
    }

    fun setOnCellClick(position: Int, isCorrect: Boolean, onClick: OnClickListener?) {
        listOfCells[position].root.setOnClickListener {
            if (isCorrect) {
                onCorrectClick(position)
            } else {
                onIncorrectClick(position)
            }
            onClick?.onClick(it)
        }
    }

    private fun onCorrectClick(position: Int) {
        listOfCells[position].apply {
            this.root.isClickable = false
            this.textCell.setBackgroundColor(context.getColor(R.color.green))
        }
    }

    private fun onIncorrectClick(position: Int) {
        listOfCells[position].apply {
            this.root.isClickable = false
            this.textCell.setBackgroundColor(Color.RED)
            postDelayed({
                this.root.isClickable = true
                this.textCell.setBackgroundColor(0)
            }, 500)
        }
    }
    private fun clearAllCells() {
        for (cell in listOfCells) {
            cell.root.isClickable = true
            cell.textCell.setBackgroundColor(0)
        }
    }
}