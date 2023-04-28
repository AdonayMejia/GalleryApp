package com.example.galleryapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.databinding.ItemHolder1Binding
import com.example.galleryapp.databinding.ItemHolder2Binding
import java.io.File

class PhotosAdapter(private val context: Context,private val imageFiles:List<File> ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class TypeOneViewHolder(binding: ItemHolder1Binding) : RecyclerView.ViewHolder(binding.root) {
      val image = binding.imageViewItem
    }

    inner class TypeTwoViewHolder(binding: ItemHolder2Binding) : RecyclerView.ViewHolder(binding.root) {
        val image2 = binding.imageViewItem2
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 3 == 0) TYPE_TWO else TYPE_ONE
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ONE -> {
                val binding = ItemHolder1Binding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TypeOneViewHolder(binding)
            }

            TYPE_TWO -> {
                val binding = ItemHolder2Binding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TypeTwoViewHolder(binding)
            }

            else -> throw IllegalArgumentException(context.getString(R.string.invalid_viewType))
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_ONE -> {
                val holderOne = holder as TypeOneViewHolder
               Glide.with(context)
                    .load(imageFiles[position])
                    .into(holderOne.image)

               /* holderOne.image.setOnClickListener {
                    isSelected[position] = !isSelected[position]
                    viewHolderOne.imageView.alpha = if (isSelected[position]) 0.5f else 1.0f
                    selectionChangeListener.onSelectionChanged(getSelectedImages().size)
                }*/
            }

            TYPE_TWO -> {
                val holderTwo = holder as TypeTwoViewHolder
                Glide.with(context)
                    .load(imageFiles[position])
                    .into(holderTwo.image2)
                /*viewHolderTwo.imageView.setOnClickListener {
                    isSelected[position] = !isSelected[position]
                    viewHolderTwo.imageView.alpha = if (isSelected[position]) 0.5f else 1.0f
                    selectionChangeListener.onSelectionChanged(getSelectedImages().size)
                }*/
            }
        }
    }

    override fun getItemCount(): Int = imageFiles.size

    companion object{
        const val TYPE_ONE = 1
        const val TYPE_TWO = 2
    }

}