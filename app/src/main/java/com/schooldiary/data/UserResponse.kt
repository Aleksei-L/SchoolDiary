package com.schooldiary.data

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("message") val message: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)
