package com.schooldiary.repository

import android.util.Log
import com.schooldiary.data.grade.GradeResponse
import com.schooldiary.data.login.LoginResponse
import com.schooldiary.data.login.User
import com.schooldiary.data.schedule.ScheduleResponse
import com.schooldiary.data.schedule.UpdateHomework
import com.schooldiary.data.studentinfo.StudentInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val api: UserApi
) {
    suspend fun loginUser(userData: User): LoginResponse? =
        withContext(Dispatchers.IO) {
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

    suspend fun getScheduleForStudent(classId: String, weekId: String): ScheduleResponse? =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getScheduleByClassId(classId, weekId)
                Log.i(this@Repository.javaClass.name, "Response from server: $response")
                return@withContext response
            } catch (e: Exception) {
                Log.e(
                    this@Repository.javaClass.name,
                    "Can't load schedule for $classId class and $weekId week"
                )
                Log.e(this@Repository.javaClass.name, e.stackTraceToString())
                return@withContext null
            }
        }

    suspend fun getScheduleForTeacher(userId: String, weekId: String): ScheduleResponse? =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getScheduleForTeacher(userId, weekId)
                Log.i(this@Repository.javaClass.name, "Response from server: $response")
                return@withContext response
            } catch (e: Exception) {
                Log.e(
                    this@Repository.javaClass.name,
                    "Can't load schedule for $userId user (teacher) and $weekId week"
                )
                Log.e(this@Repository.javaClass.name, e.stackTraceToString())
                return@withContext null
            }
        }

    suspend fun getAllGradesForUser(userId: String): GradeResponse? =
        withContext(Dispatchers.IO) {
            try {
                val translate = api.translateUserIdToStudentId(userId)
                if (translate.isNotEmpty()) {
                    val response = api.getStudentGrades(translate[0].studentId)
                    Log.i(this@Repository.javaClass.name, "Response from server: $response")
                    return@withContext response
                }
                throw Exception()
            } catch (e: Exception) {
                Log.e(this@Repository.javaClass.name, "Can't load grades for $userId user")
                Log.e(this@Repository.javaClass.name, e.stackTraceToString())
                return@withContext null
            }
        }

    suspend fun updateHomeworkByLessonId(homework: UpdateHomework) =
        withContext(Dispatchers.IO) {
            try {
                api.updateHomework(homework)
                Log.i(this@Repository.javaClass.name, "Homework $homework was updated successfully")
            } catch (e: Exception) {
                Log.e(
                    this@Repository.javaClass.name,
                    "Can't update homework for this data: $homework"
                )
                Log.e(this@Repository.javaClass.name, e.stackTraceToString())
            }
        }

    suspend fun getStudentInfo(userId: String): StudentInfoResponse? =
        withContext(Dispatchers.IO) {
            try {
                val translate = api.translateUserIdToStudentId(userId)
                if (translate.isNotEmpty()) {
                    val response = api.getStudentInfo(translate[0].studentId)
                    return@withContext response
                }
                throw Exception()
            } catch (e: Exception) {

                Log.e(this@Repository.javaClass.name, "Can't load info for $userId user")
                Log.e(this@Repository.javaClass.name, e.stackTraceToString())
                return@withContext null
            }
        }
}

