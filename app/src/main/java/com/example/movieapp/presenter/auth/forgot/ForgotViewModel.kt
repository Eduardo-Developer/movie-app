package com.example.movieapp.presenter.auth.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.domain.usecase.auth.ForgotPasswordUseCase
import com.example.movieapp.domain.usecase.auth.LoginUseCase
import com.example.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val forgotUseCase: ForgotPasswordUseCase

) : ViewModel() {

    fun forgot(email: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            forgotUseCase.invoke(email)

            emit(StateView.Success(Unit))

        } catch (exception: Exception) {
            emit(StateView.Error(exception.message))
        }
    }
}