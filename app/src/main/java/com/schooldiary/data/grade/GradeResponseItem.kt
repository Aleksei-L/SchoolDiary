package com.schooldiary.data.grade

data class GradeResponseItem(
    val grade: List<Grade>,
    val name: String,
    val subjectId: String
)