package com.schooldiary.repository

import com.schooldiary.data.User
import com.schooldiary.data.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("Users/authenticate")
    suspend fun loginUser(
        @Body userData:User
    ): UserResponse
}
