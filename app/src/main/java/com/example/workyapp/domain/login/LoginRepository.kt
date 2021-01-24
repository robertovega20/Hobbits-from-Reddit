package com.example.workyapp.domain.login

import com.example.workyapp.util.Result

interface LoginRepository {

    suspend fun retrieveToken(): Result<Unit>
}

