package com.schooldiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schooldiary.data.grade.GradeResponse
import com.schooldiary.data.login.LoginResponse
import com.schooldiary.data.login.User
import com.schooldiary.data.schedule.ScheduleResponse
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

    fun login(login: String, password: String) = viewModelScope.launch {
        val userData = User(login, password)
        val loginResponse = repository.loginUser(userData)
        mLoginData.postValue(loginResponse ?: LoginResponse("", "", "", listOf()))
    }

    fun getScheduleByClassId(classId: String) = viewModelScope.launch {
        val scheduleResponse = repository.getScheduleByClassId(classId, weekNumber.value.toString())
        scheduleResponse?.let { mScheduleData.postValue(it) }
    }

    fun getAllGradesForUser(userId: String) = viewModelScope.launch {
        val gradeResponse = repository.getAllGradesForUser(userId)
        gradeResponse?.let { mGradesData.postValue(it) }
    }

    fun plusWeekId() = mWeekNumber.postValue(mWeekNumber.value!! + 1)

    fun minusWeekId() = mWeekNumber.postValue(mWeekNumber.value!! - 1)
}
