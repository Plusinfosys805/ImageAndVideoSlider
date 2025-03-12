package com.plusinfosys.imagevideosliderdemo

import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.plusinfosys.imagevideosliderdemo.databinding.ActivityVideoImageSliderBinding

class VideoImageSliderActivity : AppCompatActivity() {

    lateinit var binding: ActivityVideoImageSliderBinding
    private var exoVideoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVideoImageSliderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpSeekbar()
    }

    override fun onResume() {
        super.onResume()
        playVideo()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoVideoPlayer?.release()
    }

    private fun playVideo() {
        val selectedVideoURI =
            "android.resource://" + packageName + "/" + R.raw.slider_video
        if (selectedVideoURI.isNotEmpty()) {
            exoVideoPlayer = ExoPlayer.Builder(this@VideoImageSliderActivity).build()
            exoVideoPlayer?.addMediaItem(MediaItem.fromUri(Uri.parse(selectedVideoURI)))
            binding.beforeVideoPlayerView.player = exoVideoPlayer
            exoVideoPlayer?.repeatMode = Player.REPEAT_MODE_ALL
            binding.beforeVideoPlayerView.useController = false
            exoVideoPlayer?.prepare()

            exoVideoPlayer?.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    if (playbackState == Player.STATE_READY) {
                        binding.beforeVideoPlayerView.visibility = View.VISIBLE
                        exoVideoPlayer?.play()
                    } else if (playbackState == Player.STATE_ENDED) {
                        exoVideoPlayer?.playWhenReady = true
                    }
                }

            })
        }
    }

    private fun setUpSeekbar() {
        val initialProgress = 50
        binding.seekBar.progress = initialProgress
        binding.imageOverlay.post {
            updateClipAndLine(initialProgress)
        }

        binding.imgBack.setOnClickListener { finish() }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                updateClipAndLine(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
    }

    private fun updateClipAndLine(progress: Int) {
        // Get the current dimensions of the ImageView
        val imageWidth = binding.imageOverlay.width
        val margin = (35 * resources.displayMetrics.density).toInt() // 20dp to pixels
        val availableWidth = imageWidth - (2 * margin)
        // Calculate the clipping bounds based on the progress
        val clipLeft = (availableWidth * (progress / 100f)).toInt() + margin
        val finalClipLeft = clipLeft.coerceIn(margin, imageWidth - margin)
        // Create a Rect that defines the new clipping bounds
        val clipBounds = Rect(finalClipLeft, 0, imageWidth, binding.imageOverlay.height)

        // Apply the clipping bounds to the ImageView
        binding.imageOverlay.clipBounds = clipBounds

        // Update the position of the clipping line
        binding.clippingLine.translationX = finalClipLeft.toFloat()
        binding.imgSeekThumb.translationX =
            finalClipLeft.toFloat() - (binding.imgSeekThumb.width.div(2))
    }
}