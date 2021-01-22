package com.example.workyapp.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workyapp.domain.RedditPostsUseCase
import com.example.workyapp.domain.model.EntryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.workyapp.util.Result
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val redditPostsUseCase: RedditPostsUseCase) :
    ViewModel() {

    private var _postsListInAction = MutableLiveData<List<EntryEntity>>()
    val postsListInAction: LiveData<List<EntryEntity>> get() = _postsListInAction

    private var _errorListInAction = MutableLiveData<Unit>()
    val errorListInAction: LiveData<Unit> get() = _errorListInAction

    fun getPosts() = viewModelScope.launch {
        when (val response = redditPostsUseCase.invoke()) {
            is Result.Success -> {

                _postsListInAction.postValue(response.data)
            }
            is Result.Error -> {
                _errorListInAction.postValue(Unit)
            }
        }
    }

}