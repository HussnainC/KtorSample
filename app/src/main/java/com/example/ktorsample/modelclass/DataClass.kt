package com.example.ktorsample.modelclass

import kotlinx.serialization.Serializable

@Serializable
data class DataClass(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)