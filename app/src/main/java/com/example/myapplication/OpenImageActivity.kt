package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_open_image.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class OpenImageActivity : AppCompatActivity() {

    companion object { const val OPEN_IMAGE = "image" }
    lateinit var imgData: Image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_image)

        var count: Int = intent.getIntExtra(OPEN_IMAGE, 0)
        Log.d("api body", count.toString())
        if (count != 0) {
                    val apiInterface = ApiInterface.create().getImage("14975843-11c82766dcb8b38c4cd451f26", count)

        apiInterface.enqueue(object: Callback<Hits> {
            override fun onResponse(call: Call<Hits>?, response: Response<Hits>?) {

                if (response?.body() != null)
                    Log.d("api body", response?.body()!!.hits[0].largeImageURL)
                imgData = response?.body()!!.hits[0]
                userName.text = imgData.user
                tagsName.text = imgData.tags
                likesCount.text = imgData.likes.toString()
                Glide.with(imgView.context).load(imgData.largeImageURL).apply(RequestOptions().centerCrop()).into(imgView)
            }

            override fun onFailure(call: Call<Hits>, t: Throwable) {
                Toast.makeText(applicationContext, "fuck", Toast.LENGTH_LONG).show()
            }
        })
        }
        shareBtn.setOnClickListener {
            share(imgView.drawable.toBitmap(), "Image")
        }
    }

    private fun share(image: Bitmap?, text: String?) {
        val file = File(imgView.context?.externalCacheDir?.path + "/image.jpg")
        FileOutputStream(file).apply {
            image?.compress(Bitmap.CompressFormat.PNG, 100, this)
            flush()
            close()
        }
        Intent().apply {
            putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            putExtra(Intent.EXTRA_TEXT, text)
            action = Intent.ACTION_SEND
            type = "image/*"
            startActivity(Intent.createChooser(this, "Sending photo"))
        }
    }
}
