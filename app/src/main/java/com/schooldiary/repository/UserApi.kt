package com.schooldiary.repository

import com.schooldiary.data.grade.GradeResponse
import com.schooldiary.data.login.LoginResponse
import com.schooldiary.data.login.User
import com.schooldiary.data.schedule.ScheduleResponse
import com.schooldiary.data.translate.TranslateUserToStudentResponse
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

    @GET("Student/userId/{id}")
    suspend fun translateUserIdToStudentId(
        @Path("id") userId: String
    ): TranslateUserToStudentResponse

    @GET("Grade/StudentId/{StudentId}")
    suspend fun getStudentGrades(
        @Path("StudentId") studentId: String
    ): GradeResponse
}
