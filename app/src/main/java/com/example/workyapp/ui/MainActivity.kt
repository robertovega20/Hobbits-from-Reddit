package com.example.workyapp.ui

import android.app.Dialog
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.workyapp.R
import com.example.workyapp.databinding.ActivityMainBinding
import com.example.workyapp.domain.subreddit.model.AllHobbitsPosts
import com.example.workyapp.domain.subreddit.model.EntryEntity
import com.example.workyapp.util.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val CLIENT_ID = "59jGJJlAKtPacQ"
        const val REDIRECT_URI = "https://www.google.com"
        const val OAUTH_URL = "https://www.reddit.com/api/v1/authorize.compact"
        const val RESPONSE_TYPE = "token"
        const val OAUTH_SCOPE = "vote"
        const val ACCESS_TOKEN = "access_token"
        const val UP_VOTE = 1
        const val DOWN_VOTE = -1
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private lateinit var sessionManager: SessionManager
    var web: WebView? = null
    var auth_dialog: Dialog? = null
    var authCode: String? = null
    var deviceId = UUID.randomUUID().toString()
    var postId: String = ""
    var isUserLogged: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        sessionManager = SessionManager(this)
        isUserLogged = sessionManager.fetchAuthToken() != null
        setObservables()
        setUIListeners()
        setContentView(binding.root)
    }

    private fun setObservables() = viewModel.apply {
        entryInAction.observe(this@MainActivity, {
            setView(it)
        })
        errorListInAction.observe(this@MainActivity, {
            makeToast(it)
        })
        successVoteInAction.observe(this@MainActivity, {
            makeToast(R.string.success_vote)
        })
        isUserLogged.observe(this@MainActivity, {
            if (!it) {
                binding.upVote.setBackgroundColor(Color.WHITE)
                binding.downVote.setBackgroundColor(Color.WHITE)
                setWebView()
            }
        })
    }

    private fun setUIListeners() = binding.apply {
        upVote.setOnClickListener {
            viewModel.votePost(UP_VOTE,postId, isUserLogged)
            downVote.setBackgroundColor(Color.WHITE)
            upVote.setBackgroundColor(Color.RED)
        }
        downVote.setOnClickListener {
            viewModel.votePost(DOWN_VOTE, postId, isUserLogged)
            downVote.setBackgroundColor(Color.BLUE)
            upVote.setBackgroundColor(Color.WHITE)
        }
    }

    private fun setView(entryEntity: EntryEntity?) {
        binding.title.text = entryEntity?.title
        postId = entryEntity?.id.toString()
        Glide
            .with(this@MainActivity)
            .load(entryEntity?.mediaEntity?.url)
            .override(600, 600)
            .into(binding.image)
    }

    private fun setWebView() {
        auth_dialog = Dialog(this@MainActivity)
        auth_dialog?.setContentView(R.layout.auth_dialog)
        web = auth_dialog?.findViewById<View>(R.id.webv) as WebView
        web?.settings?.javaScriptEnabled = true
        val url =
            "$OAUTH_URL?client_id=$CLIENT_ID&response_type=$RESPONSE_TYPE&state=$deviceId&redirect_uri=$REDIRECT_URI&scope=$OAUTH_SCOPE"
        web?.loadUrl(url)
        web?.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                if (url.contains(ACCESS_TOKEN)) {
                    val uri = Uri.parse(url)
                    authCode = uri.fragment?.dropWhile { !it.isDigit() }
                    authCode = authCode?.takeWhile { it.toString() != "&" }
                    isUserLogged = true
                    authCode?.let { it1 -> sessionManager.saveAuthToken(it1) }
                    auth_dialog?.dismiss()
                } else if (url.contains("error=access_denied")) {
                    auth_dialog?.dismiss()
                }
            }
        }
        auth_dialog?.show()
        auth_dialog?.setCancelable(true)
    }

    private fun makeToast(message: Int) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }
}

