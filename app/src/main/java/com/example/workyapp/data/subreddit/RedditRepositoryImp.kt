package com.example.workyapp.data.subreddit

import android.util.Log
import com.example.workyapp.R
import com.example.workyapp.data.subreddit.model.Entry
import com.example.workyapp.domain.subreddit.RedditRepository
import com.example.workyapp.domain.subreddit.model.*
import com.example.workyapp.util.HobbitNames.BILBO
import com.example.workyapp.util.HobbitNames.FRODO
import com.example.workyapp.util.HobbitNames.GOLLUM
import com.example.workyapp.util.HobbitNames.MERIADOC
import com.example.workyapp.util.HobbitNames.MERRY
import com.example.workyapp.util.HobbitNames.PEREGRIN
import com.example.workyapp.util.HobbitNames.PIPPIN
import com.example.workyapp.util.HobbitNames.SAM
import com.example.workyapp.util.HobbitNames.SAMSAGAZ
import com.example.workyapp.util.HobbitNames.SMEAGOL
import com.example.workyapp.util.Result
import com.example.workyapp.util.safeApiCall
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import retrofit2.Retrofit
import javax.inject.Inject


class RedditRepositoryImp @Inject constructor(
    private val retrofit: Retrofit
) : RedditRepository {

    companion object {
        const val POSTS_NUMBER = 99
        const val FIRST_CHAR = 0
        const val START_INDEX = 4000
        const val HOBBIT_POSTS = "Hobbit posts"
        const val LIST_START = 0
        const val ERROR_TAG = "ERROR TAG"
        const val ERROR_SERVICE = "API ERROR"
        const val WORD_NO_FEASIBLE = "*!?$=·=$%3@?..1"
        const val NO_TIE = 1
    }

    override suspend fun getPosts() = safeApiCall(
        call = { getPostsCall() },
        errorMessage = R.string.error_polite
    )

    private suspend fun getPostsCall(): Result<EntryEntity?> {
        val response = retrofit
            .create(RedditService::class.java)
            .getLotrResponse(POSTS_NUMBER)
        if (response.isSuccessful) {
            val entryList = entryMapper(response.body()?.entries)
            val allHobbitPosts = makeAllHobbitsPosts(entryList)
            printPostsToConsole(allHobbitPosts)
            return Result.Success(selectPostToShow(allHobbitPosts))
        }
        Log.d(ERROR_TAG, ERROR_SERVICE)
        return Result.Error(R.string.error_polite)
    }

    private fun entryMapper(entryList: List<Entry>?): List<EntryEntity> {
        return entryList?.map {
            EntryEntity(
                AuthorEntity(
                    it.author?.name ?: "",
                    it.author?.uri ?: ""
                ),
                CategoryEntity(
                    it.category?.term ?: "",
                    it.category?.label ?: ""),
               it.content ?: "",
                it.id ?: "",
                MediaEntity(it.media?.url ?: ""),
                LinkEntity( it.link?.href ?: ""),
                it.updated ?: "",
                it.title ?: ""
            )
        } ?: listOf()
    }

    private fun makeAllHobbitsPosts(entryList: List<EntryEntity>): AllHobbitsPosts {
        entryList.let {
            val frodo = PostsEntity(FRODO, makeHobbitEntityList(it, FRODO))
            val bilbo = PostsEntity(BILBO, makeHobbitEntityList(it, BILBO))
            val sam = PostsEntity(SAM, makeHobbitEntityList(it, SAM, SAMSAGAZ))
            val merry = PostsEntity(MERRY, makeHobbitEntityList(it, MERRY, MERIADOC))
            val pippin = PostsEntity(PIPPIN, makeHobbitEntityList(it, PIPPIN, PEREGRIN))
            val gollum = PostsEntity(GOLLUM, makeHobbitEntityList(it, GOLLUM, SMEAGOL))

            val hobbitsList = mutableListOf(frodo, bilbo, sam, merry, pippin, gollum)
            return orderBySizeAndAlphabetically(hobbitsList)
        }
    }

    private fun orderBySizeAndAlphabetically(hobbitsList: MutableList<PostsEntity>): AllHobbitsPosts {
        val hobbitsOrderedBySize = AllHobbitsPosts(hobbitsList.sortedByDescending { it.posts?.size })
        var hobbitsWithSamePosts = 0

        for(element in hobbitsOrderedBySize.allPosts) {
            if (hobbitsOrderedBySize.allPosts[0].posts?.size == element.posts?.size) {
                hobbitsWithSamePosts += 1
            }
        }
        if (hobbitsWithSamePosts == NO_TIE) {
            return  hobbitsOrderedBySize
        }
        val hobbitsSameSize = hobbitsOrderedBySize.allPosts.take(hobbitsWithSamePosts).toMutableList()
        val newHobbitList = hobbitsOrderedBySize.allPosts.drop(hobbitsWithSamePosts).toMutableList()
        hobbitsSameSize.sortBy { it.hobbitName?.get(FIRST_CHAR) }
        newHobbitList.addAll(index = LIST_START, hobbitsSameSize)
        return AllHobbitsPosts(newHobbitList)
    }

    private fun makeHobbitEntityList(
        entryList: List<EntryEntity>,
        firstName: String,
        secondName: String = WORD_NO_FEASIBLE
    ) = entryList.filter {
        it.title.contains(firstName) ||
                it.title.contains(secondName)
    }.map { HobbitEntity(it) }

    private fun selectPostToShow(allHobbitsPosts: AllHobbitsPosts): EntryEntity? {
        val biggerHobbit = allHobbitsPosts.allPosts.firstOrNull()?.posts
        val postsWithImage = biggerHobbit?.filter { it.entry?.mediaEntity?.url != ""  }
        var index = 0
        return if (postsWithImage != null) {
            index = getBiggestTitleIndex(postsWithImage)
            postsWithImage[index].entry
        } else {
            index = biggerHobbit?.let { getBiggestTitleIndex(it) } ?: 0
            biggerHobbit?.get(index)?.entry
        }
    }

    private fun getBiggestTitleIndex(entryList: List<HobbitEntity>): Int {
        var length = 0
        var indexToTake = 0
        for ((index, hobbit) in entryList.withIndex()) {
            if(hobbit.entry?.title?.length!! > length){
                indexToTake = index
                length = hobbit.entry?.title?.length!!
            }
        }
        return indexToTake
    }

    private fun printPostsToConsole(hobbitsOrdered: AllHobbitsPosts) {
        val jsonString = Gson().toJson(hobbitsOrdered)
        val json = JsonParser().parse(jsonString).asJsonObject
        val allHobbitsPosts = GsonBuilder().setPrettyPrinting().create().toJson(json)
        longInfo(allHobbitsPosts)
    }

    private fun longInfo(allHobbitsPosts: String) {
        if (allHobbitsPosts.length > START_INDEX) {
            Log.d(HOBBIT_POSTS, allHobbitsPosts.substring(FIRST_CHAR, START_INDEX))
            longInfo(allHobbitsPosts.substring(START_INDEX))
        } else Log.d(HOBBIT_POSTS, allHobbitsPosts)
    }
}