package com.example.workyapp.domain.login

import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend operator fun invoke() = loginRepository.retrieveToken()
}