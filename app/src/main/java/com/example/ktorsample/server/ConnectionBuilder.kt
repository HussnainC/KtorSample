package com.example.ktorsample.server

import com.example.ktorsample.modelclass.CommentsDataClass
import com.example.ktorsample.modelclass.DataClass
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class ConnectionBuilder @Inject constructor(val client: HttpClient) {
    suspend fun getPosts(): MutableList<DataClass> {
        return client.get {
            this.url("https://jsonplaceholder.typicode.com/posts")
        }
    }

    suspend fun getComments(id: Int): MutableList<CommentsDataClass> {
        return client.get {
            this.url("https://jsonplaceholder.typicode.com/comments")
            parameter("postId", id)
        }
    }
}