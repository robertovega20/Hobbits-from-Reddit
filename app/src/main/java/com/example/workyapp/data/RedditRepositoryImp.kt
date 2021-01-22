package com.example.workyapp.data

import android.util.Log
import com.example.workyapp.R
import com.example.workyapp.data.model.Entry
import com.example.workyapp.domain.RedditRepository
import com.example.workyapp.domain.model.*
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
        const val POSTS_NUMBER = 100
    }

    private val characterList2 = listOf(
        R.string.character_frodo,
        R.string.character_bilbo,
        R.string.character_sam,
        R.string.character_samsagaz,
        R.string.character_meriadoc,
        R.string.character_merry,
        R.string.character_peregrin,
        R.string.character_pippin,
        R.string.character_gollum,
        R.string.character_smeagol
    )

    private val characterNames = listOf(
        "Frodo",
        "Bilbo",
        "Sam",
        "Samsagaz",
        "Meriadoc",
        "Merry",
        "Peregrin",
        "Pippin",
        "Gollum",
        "Gandalf"
    )

    override suspend fun getPosts() = safeApiCall(
        call = { getPostsCall() },
        errorMessage = R.string.error_polite
    )

    private suspend fun getPostsCall(): Result<List<EntryEntity>> {
        val response = retrofit
            .create(RedditService::class.java)
            .getLotrResponse(POSTS_NUMBER)
        if (response.isSuccessful) {
            val entryList = entryMapper(response.body()?.entries)
            makePostList(entryList)
            return Result.Success(entryList)
        }
        Log.d("ERROR", "SERVICE ERROR")
        return Result.Error(R.string.error_polite)
    }

    private fun entryMapper(entryList: List<Entry>?): List<EntryEntity> {
        return entryList?.map {
            EntryEntity(
                AuthorEntity(
                    it.author?.name ?: "",
                    it.author?.uri ?: ""
                ),
                it.category ?: "",
                it.content ?: "",
                it.id ?: "",
                it.link ?: "",
                it.updated ?: "",
                it.title ?: ""
            )
        } ?: listOf()
    }

    private fun makePostList(entryList: List<EntryEntity>) {
        val frodoList = PostsEntity(FRODO, makeHobbitList(entryList, FRODO))
        val bilboList = PostsEntity(BILBO, makeHobbitList(entryList, BILBO))
        val samList = PostsEntity(SAM, makeHobbitWithTwoNamesList(entryList, SAM, SAMSAGAZ))
        val merryList = PostsEntity(MERRY, makeHobbitWithTwoNamesList(entryList, MERRY, MERIADOC))
        val pippinList = PostsEntity(PIPPIN, makeHobbitWithTwoNamesList(entryList, PIPPIN, PEREGRIN ))
        val gollumList = PostsEntity(GOLLUM, makeHobbitWithTwoNamesList(entryList, GOLLUM, SMEAGOL))

        val allHobbitList = listOf(frodoList, bilboList, samList, merryList, pippinList, gollumList)
        val allHobbitsPosts = AllHobbitsPosts(allHobbitList.sortedByDescending { it.posts?.size })

        val jsonString = Gson().toJson(allHobbitsPosts)
        val parser = JsonParser()
        val json = parser.parse(jsonString).asJsonObject
        val gson = GsonBuilder().setPrettyPrinting().create()
        Log.d("Hobbits Posts", gson.toJson(json))
    }

    private fun makeHobbitList(entryList: List<EntryEntity>, name: String) =
        entryList.filter { it.title.contains(name) }.map {
            HobbitEntity(it)
        }


    private fun makeHobbitWithTwoNamesList(
        entryList: List<EntryEntity>,
        firstName: String,
        secondName: String
    ) = entryList.filter {
            it.title.contains(firstName) ||
                    it.title.contains(secondName)
        }.map { HobbitEntity(it) }
}