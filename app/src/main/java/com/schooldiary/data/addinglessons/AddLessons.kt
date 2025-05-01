package com.schooldiary.data.addinglessons


data class AddLessons(
    val className: String,
    val lessons: List<LessonsForAdding>,
)