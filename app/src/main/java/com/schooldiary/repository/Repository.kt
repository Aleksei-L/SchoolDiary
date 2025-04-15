package com.schooldiary.repository

import android.util.Log
import com.schooldiary.data.LoginResponse
import com.schooldiary.data.ScheduleResponse
import com.schooldiary.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val api: UserApi
) {
    suspend fun loginUser(userData: User): LoginResponse? = withContext(Dispatchers.IO) {
        try {
            val response = api.loginUser(userData)
            Log.i(this@Repository.javaClass.name, "Response from server: $response")
            return@withContext response
        } catch (e: Exception) {
            Log.e(this@Repository.javaClass.name, "Login for user $userData failed")
            Log.e(this@Repository.javaClass.name, e.stackTraceToString())
            return@withContext null
        }
    }

    suspend fun getScheduleByClassId(classId: String): ScheduleResponse? = withContext(Dispatchers.IO) {
        try {
            val response = api.getScheduleByClassId(classId)
            Log.i(this@Repository.javaClass.name, "Response from server: $response")
            return@withContext response
        } catch (e: Exception) {
            Log.e(this@Repository.javaClass.name, "Can't load schedule for $classId class")
            Log.e(this@Repository.javaClass.name, e.stackTraceToString())
            return@withContext null
        }
    }
}
