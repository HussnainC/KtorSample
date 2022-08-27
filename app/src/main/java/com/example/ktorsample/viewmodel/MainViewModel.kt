package com.example.ktorsample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope


import com.example.ktorsample.repositories.MainRepo
import com.example.ktorsample.utils.States
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mRepo: MainRepo) : ViewModel() {

    private val _postList: MutableStateFlow<States> = MutableStateFlow(States.Empty)
    val postList: StateFlow<States> = _postList

    fun loadPosts() = viewModelScope.launch(Dispatchers.IO) {
        mRepo.getPosts().onStart {
            _postList.value = States.Loading
        }.catch { e ->
            _postList.value = States.Failure(e)
        }.collect {
            _postList.value = States.Success(it)
        }
    }
}