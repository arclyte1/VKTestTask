package com.example.vktesttask.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vktesttask.R
import com.example.vktesttask.domain.repository.GifRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var gifRepository: GifRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}