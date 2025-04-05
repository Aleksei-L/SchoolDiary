package com.schooldiary.repository

import android.util.Log
import com.schooldiary.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val api: UserApi
) {
    suspend fun loginUser(userData: User): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = api.loginUser(userData)
            Log.i(this@Repository.javaClass.name, "Response from server: $response")
            return@withContext response.message == "Пользователь успешно аутентифицирован"
        } catch (e: Exception) {
            Log.e(this@Repository.javaClass.name, "Login for user $userData failed")
            Log.e(this@Repository.javaClass.name, e.stackTraceToString())
            return@withContext false
        }
    }
}
