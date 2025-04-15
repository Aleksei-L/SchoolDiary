package com.schooldiary.data

data class ScheduleResponseItem(
    val lessons: List<Lesson>,
    val weekDayName: String
)