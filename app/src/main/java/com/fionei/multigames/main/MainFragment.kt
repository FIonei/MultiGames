package com.fionei.multigames.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.fionei.multigames.R
import com.fionei.multigames.databinding.FragmentMainBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mainBinding: FragmentMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        navController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mainBinding = FragmentMainBinding.inflate(inflater, container, false)

        return mainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainBinding.memoryCard.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_memoryFragment)
        }
        mainBinding.doubleMemoryCard.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_doubleMemoryFragment)
        }
        mainBinding.linesCard.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_linesFragment)
        }
        mainBinding.tarotCard.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_tarotFragment)
        }
        mainBinding.seaBattleCard.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_seaBattleFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}