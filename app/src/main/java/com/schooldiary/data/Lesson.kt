package com.schooldiary.data

data class Lesson(
    val endTime: String,
    val homework: String,
    val lessonOrder: Int,
    val room: String,
    val startTime: String,
    val subjectName: String,
    val teacherName: String
)