package com.schooldiary.data.grade

import com.google.gson.annotations.SerializedName

data class ClassAndSubject(
    @SerializedName("classId") val classId: String,
    @SerializedName("subjectId") val subjectId: String
)
