package com.schooldiary.data.schedule

import com.google.gson.annotations.SerializedName

data class UpdateHomework(
    @SerializedName("lessonId") val lessonId: String,
    @SerializedName("homework") val homework: String
)
