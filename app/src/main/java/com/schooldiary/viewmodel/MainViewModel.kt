package com.schooldiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schooldiary.data.LoginResponse
import com.schooldiary.data.ScheduleResponse
import com.schooldiary.data.User
import com.schooldiary.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
) : ViewModel() {
    private val mLoginData = MutableLiveData<LoginResponse>()
    val loginData: LiveData<LoginResponse> = mLoginData

    private val mScheduleData = MutableLiveData<ScheduleResponse>()
    val scheduleData: LiveData<ScheduleResponse> = mScheduleData

    var dayForDetails = -1

    fun login(login: String, password: String) = viewModelScope.launch {
        val userData = User(login, password)
        val loginResponse = repository.loginUser(userData)
        mLoginData.postValue(loginResponse ?: LoginResponse("", "", ""))
    }

    fun getScheduleByClassId(classId: String) = viewModelScope.launch {
        val scheduleResponse = repository.getScheduleByClassId(classId)
        scheduleResponse?.let { mScheduleData.postValue(it) }
    }
}
