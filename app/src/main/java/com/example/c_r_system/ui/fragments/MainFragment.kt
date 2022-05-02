package com.example.c_r_system.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.c_r_system.R
import com.example.c_r_system.databinding.FragmentMainBinding

import com.example.c_r_system.ui.activities.MainActivity


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        init()
        return binding.root

    }

    private fun init() {





    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}