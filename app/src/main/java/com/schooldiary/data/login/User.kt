package com.schooldiary.data.login

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("Login") val login: String,
    @SerializedName("Password") val password: String
)
