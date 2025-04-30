package com.schooldiary.repository

import android.util.Log
import com.schooldiary.data.classname.ClassNameResponse
import com.schooldiary.data.createdata.DataCreatedResponse
import com.schooldiary.data.createdata.DataForCreate
import com.schooldiary.data.editdata.EditData
import com.schooldiary.data.editdata.EditDataResponse
import com.schooldiary.data.grade.GradeResponse
import com.schooldiary.data.login.LoginResponse
import com.schooldiary.data.login.User
import com.schooldiary.data.room.RoomResponse
import com.schooldiary.data.schedule.ScheduleResponse
import com.schooldiary.data.schedule.UpdateHomework
import com.schooldiary.data.studentinfo.StudentInfoResponse
import com.schooldiary.data.subject.SubjectsResponse
import com.schooldiary.data.teacherInfo.TeacherInfoResponse
import com.schooldiary.data.users.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val api: UserApi
) {
    suspend fun loginUser(userData: User): LoginResponse = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.loginUser(userData)
            Log.i(this@Repository.javaClass.name, "Response from server: $response")
            response.body() ?: LoginResponse("Логин или пароль некорректны", "", "", listOf())
        } catch (e: Exception) {
            Log.e(this@Repository.javaClass.name, "Login for user $userData failed")
            Log.e(this@Repository.javaClass.name, e.stackTraceToString())
            LoginResponse("Ошибка связи с сервером", "", "", listOf())
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

    suspend fun getAllGradesForUser(userId: String): GradeResponse? = withContext(Dispatchers.IO) {
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

    suspend fun updateHomeworkByLessonId(homework: UpdateHomework) = withContext(Dispatchers.IO) {
        try {
            api.updateHomework(homework)
            Log.i(this@Repository.javaClass.name, "Homework $homework was updated successfully")
        } catch (e: Exception) {
            Log.e(
                this@Repository.javaClass.name, "Can't update homework for this data: $homework"
            )
            Log.e(this@Repository.javaClass.name, e.stackTraceToString())
        }
    }

    suspend fun getStudentInfo(userId: String): StudentInfoResponse? = withContext(Dispatchers.IO) {
        try {
            val translate = api.translateUserIdToStudentId(userId)
            if (translate.isNotEmpty()) {
                val response = api.getStudentInfo(translate[0].studentId)
                Log.i(this@Repository.javaClass.name, "Response from server: $response")
                return@withContext response
            }
            throw Exception()
        } catch (e: Exception) {
            Log.e(this@Repository.javaClass.name, "Can't load info for $userId user")
            Log.e(this@Repository.javaClass.name, e.stackTraceToString())
            return@withContext null
        }
    }

    suspend fun getTeacherInfo(userId: String): TeacherInfoResponse? = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getTeacherInfo(userId)
            Log.i(this@Repository.javaClass.name, "Response from server: $response")
            response
        } catch (e: Exception) {
            Log.e(this@Repository.javaClass.name, "Can't load info for $userId teacher")
            Log.e(this@Repository.javaClass.name, e.stackTraceToString())
            null
        }
    }

    suspend fun createUser(data: DataForCreate): DataCreatedResponse = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.createNewUser(data)
            Log.i(this@Repository.javaClass.name, "Response from server: $response")
            if (response.isSuccessful) {
                response.body()
                    ?: DataCreatedResponse("Данные некорректны или пользователь уже существует")
            } else {
                when (response.code()) {
                    400 -> {
                        val errorBodyText = response.errorBody()?.string()
                        val cleanError = errorBodyText?.substringAfter("""message":"""")
                            ?.substringBefore(""""}""")
                        DataCreatedResponse("${cleanError}")
                    }

                    else -> DataCreatedResponse("Ошибка сервера: ${response.code()}")
                }
            }
        } catch (e: Exception) {
            DataCreatedResponse("Ошибка связи с сервером")
        }
    }

    suspend fun getAllUsers(): UserResponse? = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getAllUsers()
            Log.i(this@Repository.javaClass.name, "Response from server: $response")
            response
        } catch (e: Exception) {
            Log.e(this@Repository.javaClass.name, "Can't load info for users")
            Log.e(this@Repository.javaClass.name, e.stackTraceToString())
            null
        }
    }

    suspend fun deleteUser(userId: String) {
        withContext(Dispatchers.IO) {
            try {
                api.deleteUser(userId)
            } catch (e: Exception) {
                Log.e(this@Repository.javaClass.name, e.stackTraceToString())
            }
        }
    }

    suspend fun getAllSubjects(): SubjectsResponse? = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getAllSubjects()
            Log.i(this@Repository.javaClass.name, "Response from server: $response")
            response
        } catch (e: Exception) {
            Log.e(this@Repository.javaClass.name, "Can't load list of subjects")
            Log.e(this@Repository.javaClass.name, e.stackTraceToString())
            null
        }
    }

    suspend fun getAllClasses(): ClassNameResponse? = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getAllClass()
            Log.i(this@Repository.javaClass.name, "Response from server: $response")
            response
        } catch (e: Exception) {
            Log.e(this@Repository.javaClass.name, "Can't load list of classes")
            Log.e(this@Repository.javaClass.name, e.stackTraceToString())
            null
        }
    }

    suspend fun getScheduleForZavuch(className: String, weekId: String): ScheduleResponse? =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getSchedulesForZavuch(className, weekId)
                Log.i(this@Repository.javaClass.name, "Response from server: $response")
                return@withContext response
            } catch (e: Exception) {
                Log.e(
                    this@Repository.javaClass.name,
                    "Can't load schedule for $className className and $weekId week"
                )
                Log.e(this@Repository.javaClass.name, e.stackTraceToString())
                return@withContext null
            }
        }

    suspend fun getAllRoom(): RoomResponse? = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getAllRoom()
            Log.i(this@Repository.javaClass.name, "Response from server: $response")
            response
        } catch (e: Exception) {
            Log.e(this@Repository.javaClass.name, "Can't load list of rooms")
            Log.e(this@Repository.javaClass.name, e.stackTraceToString())
            null
        }
    }

    suspend fun updateUserInfo(userId: String, editData: EditData): EditDataResponse =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val response = api.updateUserInfo(userId, editData)
                Log.i(this@Repository.javaClass.name, "Response from server: $response")
                if (response.isSuccessful) {
                    response.body()?:EditDataResponse("")
                } else {
                    when (response.code()) {
                        400 -> {
                            val errorBodyText = response.errorBody()?.string()
                            val cleanError = errorBodyText?.substringAfter("""message":"""")
                                ?.substringBefore(""""}""")
                            EditDataResponse("${cleanError}")
                        }
                        else -> EditDataResponse("Ошибка сервера: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                EditDataResponse("Ошибка связи с сервером")
            }
        }
}