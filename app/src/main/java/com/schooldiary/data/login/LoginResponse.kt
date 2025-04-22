package com.schooldiary.data.login

data class LoginResponse(
    val message: String,
    val userId: String,
    val classId: String?,
    val roles: List<String>
)