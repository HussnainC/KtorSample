package com.example.ktorsample.modelclass

import kotlinx.serialization.Serializable

@Serializable
data class CommentsDataClass(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)