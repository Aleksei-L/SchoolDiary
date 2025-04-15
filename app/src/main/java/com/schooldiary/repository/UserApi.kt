package com.schooldiary.repository

import com.schooldiary.data.LoginResponse
import com.schooldiary.data.ScheduleResponse
import com.schooldiary.data.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("Users/authenticate")
    suspend fun loginUser(
        @Body userData: User
    ): LoginResponse

    @GET("Schedules/ByClassId/{classId}")
    suspend fun getScheduleByClassId(
        @Path("classId") classId: String
    ): ScheduleResponse
}
