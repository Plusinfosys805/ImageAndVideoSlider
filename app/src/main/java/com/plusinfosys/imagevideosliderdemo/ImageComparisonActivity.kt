package com.plusinfosys.imagevideosliderdemo

import android.graphics.Rect
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.plusinfosys.imagevideosliderdemo.databinding.ActivityImageComparisonBinding

class ImageComparisonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageComparisonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityImageComparisonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.afterImageView.post {
            // Set initial SeekBar progress to 50% and update UI
            binding.seekBar.progress = 50
            updateClip(50)
        }

        binding.imgBack.setOnClickListener { finish() }

        // Update UI based on seekBar movement
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateClip(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


    }

    private fun updateClip(progress: Int) {
        val width = binding.beforeImageView.width
        val clipValue = (width * progress) / 100 // Calculate clip position

        // Clip the after image view
        binding.afterImageView.clipBounds = Rect(0, 0, clipValue, binding.afterImageView.height)

        // Move the slider thumb and clipping line
        val params = binding.clippingLine.layoutParams as FrameLayout.LayoutParams
        params.marginStart = clipValue - (binding.clippingLine.width / 2)
        binding.clippingLine.layoutParams = params

        val thumbParams = binding.imgSeekThumb.layoutParams as FrameLayout.LayoutParams
        thumbParams.marginStart = clipValue - (binding.imgSeekThumb.width / 2)
        binding.imgSeekThumb.layoutParams = thumbParams
    }

}