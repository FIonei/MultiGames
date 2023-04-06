package com.fionei.multigames.lines

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fionei.multigames.R

class LinesFragment : Fragment() {

    companion object {
        fun newInstance() = LinesFragment()
    }

    private lateinit var viewModel: LinesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lines, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LinesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}