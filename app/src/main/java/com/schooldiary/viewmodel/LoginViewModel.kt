package com.schooldiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schooldiary.data.User
import com.schooldiary.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: Repository
) : ViewModel() {
    private val mLoginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = mLoginStatus

    fun login(login: String, password: String) = viewModelScope.launch {
        val userData = User(login, password)
        mLoginStatus.postValue(repository.loginUser(userData))
    }
}
