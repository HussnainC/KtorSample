package com.example.ktorsample.repositories

import com.example.ktorsample.modelclass.DataClass
import com.example.ktorsample.server.ConnectionBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepo @Inject constructor(val connectionBuilder: ConnectionBuilder) {

    fun getPosts(): Flow<MutableList<DataClass>> = flow {
        emit(connectionBuilder.getPosts())
    }
}