package com.schooldiary.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message") val message: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("classId") val classId: String
)
