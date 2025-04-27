package com.schooldiary.data.users

data class User(
    val userId:String,
    val email:String,
    val password:String,
    val name: String,
    val login:String,
    val inActive:Boolean,
    val roles:List<String>
)
 class UserResponse: ArrayList<User>()