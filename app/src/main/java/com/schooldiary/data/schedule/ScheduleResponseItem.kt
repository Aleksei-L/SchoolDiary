package com.schooldiary.data.schedule

data class ScheduleResponseItem(
    val lessons: List<Lesson>,
    val weekDayName: String
)