package com.armshare.loopcarousel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_image_details.*

class ImageDetailsActivity : AppCompatActivity() {
    private val DRAWABLE_RESOURE = "resource"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)

        val drawbleResource = intent.getIntExtra(DRAWABLE_RESOURE, 0)
        imageView.setImageResource(drawbleResource)

    }
}
