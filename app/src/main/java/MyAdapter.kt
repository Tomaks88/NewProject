package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.image_item.view.*

class MyAdapter(val context: Context, private val onClickListener: (Image) -> Unit) : RecyclerView.Adapter<MyAdapter.MyViewHolder>()  {

    private var myDataset: Array<Image> = arrayOf()

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        private val imageView = view.image
        private val tags = view.tags

        fun bind(imgData: Image) {
            tags.text = imgData.tags
            Glide.with(view.context).load(imgData.largeImageURL).apply(RequestOptions().centerCrop()).into(imageView)
        }
    }

    fun setMyDataset(myDataset: Array<Image>){
        this.myDataset = myDataset;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.MyViewHolder {

        val constrainLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)
        return MyViewHolder(constrainLayout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(myDataset[position])
        holder.view.setOnClickListener {
            onClickListener.invoke(myDataset[position]) }
    }

    override fun getItemCount() = myDataset.size


}