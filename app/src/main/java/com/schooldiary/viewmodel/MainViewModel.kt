package com.schooldiary.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schooldiary.data.grade.GradeResponse
import com.schooldiary.data.login.LoginResponse
import com.schooldiary.data.login.User
import com.schooldiary.data.schedule.ScheduleResponse
import com.schooldiary.data.schedule.UpdateHomework
import com.schooldiary.repository.Repository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class MainViewModel(
    private val repository: Repository
) : ViewModel() {
    private val mLoginData = MutableLiveData<LoginResponse>()
    val loginData: LiveData<LoginResponse> = mLoginData

    private val mScheduleData = MutableLiveData<ScheduleResponse>()
    val scheduleData: LiveData<ScheduleResponse> = mScheduleData

    private val mGradesData = MutableLiveData<GradeResponse>()
    val gradesData: LiveData<GradeResponse> = mGradesData

    var dayForDetails = -1

    private val mWeekNumber = MutableLiveData(
        LocalDate.now().get(WeekFields.of(Locale.UK).weekOfYear()) + 17
    )
    val weekNumber: LiveData<Int> = mWeekNumber

    lateinit var userRole: UserRole

    var lessonIdForUpdateHomework: String? = null

    var currentHomeworkForEdit: String = ""
    val homeworkUpdated = MutableLiveData<Boolean>(false)

    fun login(login: String, password: String) = viewModelScope.launch {
        val userData = User(login, password)
        val loginResponse = repository.loginUser(userData)
        mLoginData.postValue(
            loginResponse ?: LoginResponse(
                "Логин или пароль некорректен", "", "", listOf()
            )
        )
    }

    fun getScheduleForStudent(classId: String) = viewModelScope.launch {
        val scheduleResponse =
            repository.getScheduleForStudent(classId, weekNumber.value.toString())
        scheduleResponse?.let { mScheduleData.postValue(it) }
    }

    fun getScheduleForTeacher(userId: String) = viewModelScope.launch {
        val scheduleResponse = repository.getScheduleForTeacher(userId, weekNumber.value.toString())
        scheduleResponse?.let { mScheduleData.postValue(it) }
    }

    fun getAllGradesForUser(userId: String) = viewModelScope.launch {
        val gradeResponse = repository.getAllGradesForUser(userId)
        gradeResponse?.let { mGradesData.postValue(it) }
    }

    fun plusWeekId() = mWeekNumber.postValue(mWeekNumber.value!! + 1)

    fun minusWeekId() = mWeekNumber.postValue(mWeekNumber.value!! - 1)

    fun updateHomework(newHomework: String) = viewModelScope.launch {
        if (lessonIdForUpdateHomework == null) {
            Log.e(this.javaClass.name, "Field lessonIdForUpdateHomework is null")
            return@launch
        }
        val homeworkData = UpdateHomework(lessonIdForUpdateHomework!!, newHomework)
        repository.updateHomeworkByLessonId(homeworkData)
        currentHomeworkForEdit = newHomework
        homeworkUpdated.postValue(true)
    }

    fun clearMessage() = viewModelScope.launch {
        mLoginData.postValue(LoginResponse("", "", "", listOf()))
    }
}
