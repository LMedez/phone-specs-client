package com.luc.phonespecs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.luc.phonespecs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PhoneSpecs)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}