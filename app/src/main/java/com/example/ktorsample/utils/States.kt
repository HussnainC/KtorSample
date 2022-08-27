package com.example.ktorsample.utils

import com.example.ktorsample.modelclass.DataClass

sealed class States {
    object Empty : States()
    class Failure(val msg: Throwable) : States()
    class Success(val data: MutableList<DataClass>) : States()
    object Loading : States()
}