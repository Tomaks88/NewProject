package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MyAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var myDataset:Array<Image> = arrayOf()
    private var key: String = "14975843-11c82766dcb8b38c4cd451f26"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(this, this::openImageView)
        recyclerView = findViewById<RecyclerView>(R.id.rvImage)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter

        searchImg.setImeOptions(IME_ACTION_SEARCH)
        searchImg.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                val apiInterface = ApiInterface.create().getImageSearch(key, textView.text.toString())

                apiInterface.enqueue(object: Callback<Hits>{
                    override fun onResponse(call: Call<Hits>, response: Response<Hits>) {
                        if (response?.body() != null)
                            viewAdapter.setMyDataset(response.body()!!.hits)
                    }
                    override fun onFailure(call: Call<Hits>, t: Throwable) {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    }
                })
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })

        val apiInterface = ApiInterface.create().getImages(key)

        apiInterface.enqueue(object: Callback<Hits> {
            override fun onResponse(call: Call<Hits>?, response: Response<Hits>?) {
                Log.d("api body", response?.body().toString())
                if (response?.body() != null)
                    viewAdapter.setMyDataset(response.body()!!.hits)
            }

            override fun onFailure(call: Call<Hits>, t: Throwable) {
               Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun openImageView(img: Image){
        val imageIntent = Intent(this, OpenImageActivity::class.java)
        imageIntent.putExtra(OpenImageActivity.OPEN_IMAGE, img.id)

        startActivity(imageIntent)
    }

}
