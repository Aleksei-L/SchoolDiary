package com.schooldiary.repository

import com.schooldiary.data.addinglessons.AddLessons
import com.schooldiary.data.addinglessons.AddLessonsResponse
import com.schooldiary.data.classname.ClassNameResponse
import com.schooldiary.data.createdata.DataCreatedResponse
import com.schooldiary.data.createdata.DataForCreate
import com.schooldiary.data.grade.ClassAndSubject
import com.schooldiary.data.grade.CreateGradeByTeacher
import com.schooldiary.data.editdata.EditData
import com.schooldiary.data.editdata.EditDataResponse
import com.schooldiary.data.grade.GradeResponse
import com.schooldiary.data.grade.GradesForTeacherResponse
import com.schooldiary.data.login.LoginResponse
import com.schooldiary.data.login.User
import com.schooldiary.data.room.RoomResponse
import com.schooldiary.data.schedule.ScheduleResponse
import com.schooldiary.data.schedule.UpdateHomework
import com.schooldiary.data.student.AllStudentsResponse
import com.schooldiary.data.studentinfo.StudentInfoResponse
import com.schooldiary.data.subject.SubjectsResponse
import com.schooldiary.data.teacherInfo.TeacherInfoResponse
import com.schooldiary.data.studentinfo.UserInfoResponse
import com.schooldiary.data.subject.SubjectsResponse
import com.schooldiary.data.translate.TranslateUserToStudentResponse
import com.schooldiary.data.users.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @POST("Users/authenticate")
    suspend fun loginUser(
        @Body userData: User
    ): Response<LoginResponse>

    @GET("Schedules/ByClassId/{classId}/{weekId}")
    suspend fun getScheduleByClassId(
        @Path("classId") classId: String, @Path("weekId") weekId: String
    ): ScheduleResponse

    @GET("Schedules/ByTeacher/{userId}/{weekId}")
    suspend fun getScheduleForTeacher(
        @Path("userId") userId: String, @Path("weekId") weekId: String
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

    @GET("Users/GeneralInfo/{userId}")
    suspend fun getUserInfo(
        @Path("userId") studentId: String
    ): UserInfoResponse

    @POST("Users/CreateNewUser")
    suspend fun createNewUser(
        @Body data: DataForCreate
    ): Response<DataCreatedResponse>

    @GET("Users")
    suspend fun getAllUsers(): UserResponse

    @DELETE("Users/DeleteUser/{userId}")
    suspend fun deleteUser(
        @Path("userId") userId: String
    ):Response<DataCreatedResponse>

    @GET("Schedules/ByClassName/Correct/{className}/{weekId}")
    suspend fun getSchedulesForZavuch(
        @Path("className") className: String, @Path("weekId") weekId: String
    ): ScheduleResponse

    @GET("Subjects")
    suspend fun getAllSubjects(): SubjectsResponse

    @POST("Grade/GradeAllStudentByClassSubject")
    suspend fun getGradesByClassAndSubject(
        @Body classAndSubject: ClassAndSubject
    ): GradesForTeacherResponse

    @POST("Grade")
    suspend fun createNewGrade(
        @Body createGradeByTeacher: CreateGradeByTeacher
    )

    @GET("Student")
    suspend fun getAllStudents(): AllStudentsResponse

    @GET("Class")
    suspend fun getAllClass(): ClassNameResponse

    @GET("Lesson/Room")
    suspend fun getAllRoom(): RoomResponse

    @PUT("Users/UpdateUser/{userId}")
    suspend fun updateUserInfo(
        @Path("userId") userId: String,
        @Body data: EditData
    ): Response<EditDataResponse>

    @POST("Schedules/WithNewLessons")
    suspend fun addLesson(
        @Body data: AddLessons
    ):Response<AddLessonsResponse>
}
