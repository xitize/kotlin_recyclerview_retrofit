package com.kshtitiz.sundaymobilityapp.modal

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(var login: String, var avatar_url: String, var type: String)