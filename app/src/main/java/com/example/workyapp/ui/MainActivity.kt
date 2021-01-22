package com.example.workyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.example.workyapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel.getPosts()
        setObservables()
        setContentView(binding.root)
    }

    private fun setObservables() = viewModel.apply {
        postsListInAction.observe(this@MainActivity, {
            binding.title.text = it.firstOrNull()?.title
        })
        errorListInAction.observe(this@MainActivity, {
            binding.title.text = "Error"
        })
    }
}