package com.schooldiary.data.student

data class AllStudentsResponseItem(
    val studentId: String,
    val classId: String,
    val user: User
)
