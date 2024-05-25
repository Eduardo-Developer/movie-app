package com.example.movieapp.presenter.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieapp.util.FirebaseHelper
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentLoginBinding
import com.example.movieapp.presenter.main.activity.MainActivity
import com.example.movieapp.util.StateView
import com.example.movieapp.util.hideKeyboard
import com.example.movieapp.util.initToolbar
import com.example.movieapp.util.isEmailValid
import com.example.movieapp.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding?.toolbar)
        initListener()
    }

    private fun initListener() {
        binding?.btnLogin?.setOnClickListener { validadeData() }

        binding?.progressLoading?.let {
            Glide.with(requireContext())
                .load(R.drawable.loading)
                .into(it)
        }

        binding?.btnForgot?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotFragment)
        }
    }

    private fun validadeData() {
        val email = binding?.editEmail?.text.toString()
        val password = binding?.editPassword?.text.toString()

        hideKeyboard()
        if (email.isEmailValid()) {
            if (password.isNotEmpty()) {
                login(email, password)
            } else {
                showSnackBar(message = R.string.text_password_empty_login_fragment)
            }
        } else {
            showSnackBar(message = R.string.text_email_empty_login_fragment)
        }
    }

    private fun login(email: String, password: String) {
        viewModel.login(email, password).observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    binding?.progressLoading?.isVisible = true
                }
                is StateView.Success -> {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
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