package com.example.modernauppgift2

import kotlinx.serialization.Serializable

@Serializable
data class Quote(val id : Int, val quote : String, val author : String)
