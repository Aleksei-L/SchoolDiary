package com.schooldiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schooldiary.data.ScheduleResponse
import com.schooldiary.data.User
import com.schooldiary.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
) : ViewModel() {
    private val mLoginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = mLoginStatus

    private val mClassId = MutableLiveData<String>()
    private val classId: LiveData<String> = mClassId

    private val mSchedule = MutableLiveData<ScheduleResponse>()
    val schedule: LiveData<ScheduleResponse> = mSchedule

    var dayForDetails = -1

    fun login(login: String, password: String) = viewModelScope.launch {
        val userData = User(login, password)
        val loginResponse = repository.loginUser(userData)
        if (loginResponse != null) {
            mLoginStatus.postValue(loginResponse.message == "Пользователь успешно аутентифицирован")
            mClassId.postValue(loginResponse.classId)
        }
    }

    fun getScheduleByClassId() = viewModelScope.launch {
        val scheduleResponse = classId.value?.let { repository.getScheduleByClassId(it) }
        scheduleResponse?.let { mSchedule.postValue(it) }
    }
}
