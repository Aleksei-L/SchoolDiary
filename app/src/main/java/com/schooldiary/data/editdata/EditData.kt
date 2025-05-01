package com.schooldiary.data.editdata

import com.google.gson.annotations.SerializedName

data class EditData(
    @SerializedName("email") val email: String,
    @SerializedName("login") val login: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("className") val className: String?
)


