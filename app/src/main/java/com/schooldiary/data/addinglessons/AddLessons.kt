package com.schooldiary.data.addinglessons


data class AddLessons(
    val className: String,
    val weekId: Int,
    val schedule: List<LessonsForAdding>,
)