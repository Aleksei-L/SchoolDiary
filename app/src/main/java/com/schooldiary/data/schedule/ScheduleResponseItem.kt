package com.schooldiary.data.schedule

data class ScheduleResponseItem(
    val endDate: String,
    val schedule: List<Schedule>,
    val startDate: String,
    val weekId: Int
)