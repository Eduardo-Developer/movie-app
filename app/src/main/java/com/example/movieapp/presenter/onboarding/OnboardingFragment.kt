package com.example.movieapp.presenter.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentForgotBinding
import com.example.movieapp.databinding.FragmentOnboardBinding

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOnboardBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding?.btnStart?.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment_to_authentication)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}