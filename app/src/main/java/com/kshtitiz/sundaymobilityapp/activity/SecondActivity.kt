package com.kshtitiz.sundaymobilityapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kshtitiz.sundaymobilityapp.R

class SecondActivity : AppCompatActivity() {
    lateinit var deleteItem: ImageView
    lateinit var ivUserImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        ivUserImage = findViewById(R.id.iv_show_image_large)
        deleteItem = findViewById(R.id.iv_delete_item)

        val url = intent.getStringExtra("image_url")
        val position = intent.getStringExtra("position")

        Glide.with(this).load(url)
            .centerCrop().placeholder(R.mipmap.ic_launcher).error(
                R.drawable.ic_error_image
            )
            .fallback(R.mipmap.ic_launcher).into(ivUserImage)

        deleteItem.setOnClickListener {
            val intent = Intent()
            intent.putExtra("pos", position)
            setResult(Activity.RESULT_OK, intent)
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
