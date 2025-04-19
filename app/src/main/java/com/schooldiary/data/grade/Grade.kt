package com.schooldiary.data.grade

data class Grade(
    val `data`: String,
    val gradeId: String,
    val student: Student,
    val subjectId: String,
    val subjectName: String,
    val value: Int
)