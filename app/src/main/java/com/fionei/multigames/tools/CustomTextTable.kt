package com.fionei.multigames.tools

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
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

        val density = Resources.getSystem().displayMetrics.density
        tableSize = typedArray.getInteger(R.styleable.CustomTextTable_size, 0)

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
                        cell.textCell.text = (line * tableSize + column).toString()
                        lineView.addView(cell.root)
                        cell.root.setOnClickListener {
                            onCorrectClick(line, column)
                        }
                    }
                    root.addView(lineView)
                }
                root.requestLayout()
            }
        }
    }

    /**@param line is started with 0
     * @param column is started with 0
     **/
    fun onCorrectClick(line: Int, column: Int) {
        listOfCells[line * tableSize + column].apply {
            this.root.isClickable = false
            this.root.setBackgroundColor(context.getColor(R.color.grey))
            this.textCell.alpha = 0.5f
        }
    }

    /**@param line is started with 0
     * @param column is started with 0
     **/
    fun onIncorrectClick(line: Int, column: Int) {
        listOfCells[line * tableSize + column].apply {
            this.root.isClickable = false
            this.root.setBackgroundColor(Color.RED)
            postDelayed({
                this.root.isClickable = true
                this.root.setBackgroundColor(0)
            }, 1000)
        }
    }
}