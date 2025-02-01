package com.example.myapplication.data.network

import  retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient{
    private const val BASE_URL="http://10.0.2.2:3000/"
    val retrofit:Retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

}