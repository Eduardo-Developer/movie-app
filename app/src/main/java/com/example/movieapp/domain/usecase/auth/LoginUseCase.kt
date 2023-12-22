package com.example.movieapp.domain.usecase.auth

import com.example.movieapp.domain.repository.auth.FirebaseAuthentication

class LoginUseCase(
    private val firebaseAuthentication: FirebaseAuthentication
) {
    suspend operator fun invoke(email: String, password: String) {
        firebaseAuthentication.login(email, password)
    }
}