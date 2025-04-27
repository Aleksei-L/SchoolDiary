package com.schooldiary.data.createdata

import com.google.gson.annotations.SerializedName

data class DataForCreate(
    @SerializedName("email") val email: String,
    @SerializedName("login") val login: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("roles") val roles: List<String>,
    @SerializedName("className") val className: String?
)


