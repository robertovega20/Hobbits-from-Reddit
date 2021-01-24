package com.example.workyapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workyapp.domain.subreddit.RedditPostsUseCase
import com.example.workyapp.domain.subreddit.model.AllHobbitsPosts
import com.example.workyapp.domain.subreddit.model.EntryEntity
import com.example.workyapp.domain.vote.VoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.workyapp.util.Result
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val redditPostsUseCase: RedditPostsUseCase,
    private val voteUseCase: VoteUseCase
) : ViewModel() {

    private var _entryInAction = MutableLiveData<EntryEntity?>()
    val entryInAction: LiveData<EntryEntity?> get() = _entryInAction

    private var _isUserLogged = MutableLiveData<Boolean>()
    val isUserLogged: LiveData<Boolean> get() = _isUserLogged

    private var _successVoteInAction = MutableLiveData<Unit>()
    val successVoteInAction: LiveData<Unit> get() = _successVoteInAction

    private var _errorListInAction = MutableLiveData<Int>()
    val errorListInAction: LiveData<Int> get() = _errorListInAction

    init {
        getPosts()
    }

    private fun getPosts() = viewModelScope.launch {
        when (val response = redditPostsUseCase.invoke()) {
            is Result.Success -> {
                _entryInAction.postValue(response.data)
            }
            is Result.Error -> {
                _errorListInAction.postValue(response.exception)
            }
        }
    }

    fun votePost(voteType: Int, postId: String, isLogged: Boolean) = viewModelScope.launch {
        if (isLogged) {
            when (voteUseCase.invoke(voteType, postId)) {
                is Result.Success -> {
                    _successVoteInAction.postValue(Unit)
                }
                is Result.Error -> {
                    _isUserLogged.postValue(false)
                }
            }
        } else {
            _isUserLogged.postValue(false)
        }
    }
}