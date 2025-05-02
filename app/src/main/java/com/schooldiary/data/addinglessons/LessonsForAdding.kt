package com.schooldiary.data.addinglessons

data class LessonsForAdding(
    val weekDayId:Int,
    val lessonOrder: Int,
    val subjectName: String,
    val teacherName: String,
    val startTime: String,
    val endTime: String,
    val roomName: String
)