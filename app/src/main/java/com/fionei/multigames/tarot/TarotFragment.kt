package com.fionei.multigames.tarot

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fionei.multigames.R

class TarotFragment : Fragment() {

    companion object {
        fun newInstance() = TarotFragment()
    }

    private lateinit var viewModel: TarotViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tarot, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TarotViewModel::class.java)
        // TODO: Use the ViewModel
    }

}