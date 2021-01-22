package com.example.workyapp.util

suspend fun <T : Any?> safeApiCall(call: suspend () -> Result<T>, errorMessage: Int): Result<T> {
    return try {
        call()
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Error(errorMessage)
    }
}