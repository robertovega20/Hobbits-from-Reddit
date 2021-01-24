package com.example.workyapp.data.login.model

data class TokenAccessResponse(
    val access_token: String?,
    val token_type: String?,
    val expires_in: String?,
    val scope: String?,
    val state: String?
)