package com.sts.o6uAttendance.data.network


import com.sts.o6uAttendance.data.model.FoodDto
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("api/")
    fun getHome(
    ): Call<FoodDto>


}