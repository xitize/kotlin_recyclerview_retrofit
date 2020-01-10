package com.kshtitiz.sundaymobilityapp.api

import com.kshtitiz.sundaymobilityapp.modal.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface GithubUserApi {

    @GET("users")
    fun getUserAsync(): Deferred<Response<List<User>>>

}