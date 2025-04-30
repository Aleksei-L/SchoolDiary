package com.schooldiary.data.classname

data class ClassNameResponseItem(
    val classId: String,
    val name: String
)

class ClassNameResponse : ArrayList<ClassNameResponseItem>()