package com.plusinfosys.imagevideosliderdemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.plusinfosys.imagevideosliderdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        init()
    }

    private fun init(){
        binding.txtImageSlider.setOnClickListener {
            val intent = Intent(this, ImageComparisonActivity::class.java)
            startActivity(intent)
        }

        binding.txtImageVideoSlider.setOnClickListener {
            val intent = Intent(this, VideoImageSliderActivity::class.java)
            startActivity(intent)
        }
    }
}