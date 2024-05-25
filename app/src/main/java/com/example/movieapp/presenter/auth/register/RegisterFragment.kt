package com.example.movieapp.presenter.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentRegisterBinding
import com.example.movieapp.util.FirebaseHelper
import com.example.movieapp.util.StateView
import com.example.movieapp.util.hideKeyboard
import com.example.movieapp.util.initToolbar
import com.example.movieapp.util.isEmailValid
import com.example.movieapp.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding?.toolbar)
        initListener()
    }

    private fun initListener() {
        binding?.btnRegister?.setOnClickListener { validadeData() }

        binding?.progressLoading?.let {
            Glide.with(requireContext())
                .load(R.drawable.loading)
                .into(it)
        }
    }

    private fun validadeData() {
        val email = binding?.editEmail?.text.toString()
        val password = binding?.editPassword?.text.toString()

        hideKeyboard()
        if (email.isEmailValid()) {
            if (password.isNotEmpty()) {
                register(email, password)
            } else {
                showSnackBar(message = R.string.invalid_password_register_fragment)
            }
        } else {
           showSnackBar(message = R.string.invalid_email_register_fragment)
        }

    }

    private fun register(email: String, password: String) {
        viewModel.register(email, password).observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    binding?.progressLoading?.isVisible = true
                }
                is StateView.Success -> {
                    showSnackBar(message = R.string.text_register_success_register_fragment)
                }
                is StateView.Error -> {
                    binding?.progressLoading?.isVisible = false
                    showSnackBar(message = FirebaseHelper.validError(stateView.message ?: ""))
                }

                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}