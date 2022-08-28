package com.example.ktorsample.utils

import com.example.ktorsample.modelclass.CommentsDataClass
import com.example.ktorsample.modelclass.DataClass

sealed class CommentsStates {
    object Empty : CommentsStates()
    class Failure(val msg: Throwable) : CommentsStates()
    class Success(val data: MutableList<CommentsDataClass>) : CommentsStates()
    object Loading : CommentsStates()
}