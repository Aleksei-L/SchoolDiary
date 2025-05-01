package com.schooldiary.data.grade

data class CreateGradeByTeacher(
    val `data`: String,
    val studentName: String,
    val subjectName: String,
    val value: Int
)