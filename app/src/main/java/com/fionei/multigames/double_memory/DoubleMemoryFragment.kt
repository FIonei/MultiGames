package com.fionei.multigames.double_memory

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fionei.multigames.R

class DoubleMemoryFragment : Fragment() {

    companion object {
        fun newInstance() = DoubleMemoryFragment()
    }

    private lateinit var viewModel: DoubleMemoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_double_memory, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DoubleMemoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}