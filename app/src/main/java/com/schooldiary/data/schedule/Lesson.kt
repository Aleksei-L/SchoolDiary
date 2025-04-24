package com.schooldiary.data.schedule

data class Lesson(
    val lessonId: String,
    val endTime: String,
    val homework: String,
    val lessonOrder: Int,
    val room: String,
    val startTime: String,
    val subjectName: String,
    val teacherName: String
)