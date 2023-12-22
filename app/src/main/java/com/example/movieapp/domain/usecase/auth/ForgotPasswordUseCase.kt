package com.example.movieapp.domain.usecase.auth

import com.example.movieapp.domain.repository.auth.FirebaseAuthentication

class ForgotPasswordUseCase(
    private val firebaseAuthentication: FirebaseAuthentication
) {
    suspend operator fun invoke(email: String) {
        firebaseAuthentication.forgot(email)
    }
}