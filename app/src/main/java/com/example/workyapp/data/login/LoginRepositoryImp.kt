package com.example.workyapp.data.login

import android.content.Context
import android.util.Base64.*
import com.example.workyapp.R
import com.example.workyapp.domain.login.LoginRepository
import com.example.workyapp.util.Result
import com.example.workyapp.util.SessionManager
import com.example.workyapp.util.safeApiCall
import retrofit2.Retrofit
import android.util.Base64.DEFAULT
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class LoginRepositoryImp @Inject constructor(
    private val retrofit: Retrofit,
    @ApplicationContext context: Context
) : LoginRepository {

    private val sessionManager = SessionManager(context)

    companion object {
        const val AUTHORIZATION_CODE = "authorization_code"
        const val REDIRECT_URI = "http://localhost"
        const val BASIC = "Basic "
        private val CLIENT_ID = "59jGJJlAKtPacQ"
        private val SECRET_KEY = ""
    }

    override suspend fun retrieveToken() =
        safeApiCall(
            call = { retrieveTokenCall() },
            errorMessage = R.string.error_polite
        )

    private suspend fun retrieveTokenCall(): Result<Unit> {
        val credentials = "$CLIENT_ID:$SECRET_KEY"
        val base64: String = encodeToString(credentials.toByteArray(), DEFAULT)
        val basic = BASIC + base64
        val response = retrofit
            .create(LoginService::class.java)
            .retrieveToken(
                basic,
                sessionManager.fetchAuthToken() ?: "",
                AUTHORIZATION_CODE,
                REDIRECT_URI
            )
        if (response.isSuccessful) {
            return Result.Success(Unit)
        }
        return Result.Error(R.string.error_polite)
    }
}