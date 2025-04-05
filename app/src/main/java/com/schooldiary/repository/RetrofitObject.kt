package com.schooldiary.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://schooldiary.cloudpub.ru/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService: UserApi = retrofit.create(UserApi::class.java)
}
