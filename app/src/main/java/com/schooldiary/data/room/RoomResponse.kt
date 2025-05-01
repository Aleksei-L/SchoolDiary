package com.schooldiary.data.room

data class RoomResponseItem(

    val room: String
)
class RoomResponse : ArrayList<RoomResponseItem>()