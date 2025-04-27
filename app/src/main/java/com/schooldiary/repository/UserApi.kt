package com.schooldiary.repository

import com.schooldiary.data.createdata.DataCreatedResponse
import com.schooldiary.data.createdata.DataForCreate
import com.schooldiary.data.grade.GradeResponse
import com.schooldiary.data.login.LoginResponse
import com.schooldiary.data.login.User
import com.schooldiary.data.schedule.ScheduleResponse
import com.schooldiary.data.schedule.UpdateHomework
import com.schooldiary.data.studentinfo.StudentInfoResponse
import com.schooldiary.data.teacherInfo.TeacherInfoResponse
import com.schooldiary.data.translate.TranslateUserToStudentResponse
import com.schooldiary.data.users.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("Users/authenticate")
    suspend fun loginUser(
        @Body userData: User
    ): LoginResponse

    @GET("Schedules/ByClassId/{classId}/{weekId}")
    suspend fun getScheduleByClassId(
        @Path("classId") classId: String,
        @Path("weekId") weekId: String
    ): ScheduleResponse

    @GET("Schedules/ByTeacher/{userId}/{weekId}")
    suspend fun getScheduleForTeacher(
        @Path("userId") userId: String,
        @Path("weekId") weekId: String
    ): ScheduleResponse

    @GET("Student/userId/{id}")
    suspend fun translateUserIdToStudentId(
        @Path("id") userId: String
    ): TranslateUserToStudentResponse

    @GET("Grade/StudentId/{StudentId}")
    suspend fun getStudentGrades(
        @Path("StudentId") studentId: String
    ): GradeResponse

    @PATCH("Lesson/UpdateHomeworkByLessonId")
    suspend fun updateHomework(
        @Body homeworkData: UpdateHomework
    )

    @GET("Student/GeneralInfo/{id}")
    suspend fun getStudentInfo(
        @Path("id") studentId: String
    ): StudentInfoResponse

    @GET("Teacher/GeneralInfo/{userId}")
    suspend fun getTeacherInfo(
        @Path("userId") userId: String
    ): TeacherInfoResponse

    @POST("Users/CreateNewUser")
    suspend fun createNewUser(
        @Body data: DataForCreate
    ): Response<DataCreatedResponse>

    @GET("Users")
    suspend fun getAllUsers(): UserResponse
}
