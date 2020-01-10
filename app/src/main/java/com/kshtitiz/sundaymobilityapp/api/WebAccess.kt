package com.kshtitiz.sundaymobilityapp.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


//Singleton pattern in kotlin  - good to have single instance of Retrofit while calling multiple time the apis (endpoints)
object WebAccess {

    private val myOkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(getHttpLoggingInterceptor())
        .build()

    private fun getHttpLoggingInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    val usersApi: GithubUserApi by lazy {
        val retrofit = Retrofit.Builder().client(myOkHttpClient).baseUrl(" https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
        return@lazy retrofit.create(GithubUserApi::class.java)
    }

}