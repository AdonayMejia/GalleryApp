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

class PhotosAdapter(
    private val context: Context,
    private val imageFiles:List<File>,
    private val selectionChangeListener: SelectionChangeListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val isImgSelected = MutableList(imageFiles.size) { false }
    inner class TypeOneViewHolder(binding: ItemHolder1Binding) : RecyclerView.ViewHolder(binding.root) {
      val image = binding.imageViewItem
    }

    inner class TypeTwoViewHolder(binding: ItemHolder2Binding) : RecyclerView.ViewHolder(binding.root) {
        val image2 = binding.imageViewItem2
    }

    interface SelectionChangeListener {
        fun onSelectionChanged(selectedCount: Int)
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

              holderOne.image.setOnClickListener {
                    isImgSelected[position] = !isImgSelected[position]
                    holderOne.image.alpha = if (isImgSelected[position]) 0.5f else 1.0f
                    selectionChangeListener.onSelectionChanged(getImageSelected().size)
                }
            }

            TYPE_TWO -> {
                val holderTwo = holder as TypeTwoViewHolder
                Glide.with(context)
                    .load(imageFiles[position])
                    .into(holderTwo.image2)
                holderTwo.image2.setOnClickListener {
                    isImgSelected[position] = !isImgSelected[position]
                    holderTwo.image2.alpha = if (isImgSelected[position]) 0.5f else 1.0f
                    selectionChangeListener.onSelectionChanged(getImageSelected().size)
                }
            }
        }
    }

    override fun getItemCount(): Int = imageFiles.size

    fun getImageSelected(): List<File> {
        return imageFiles.filterIndexed { index, _ -> isImgSelected[index] }
    }
    companion object{
        const val TYPE_ONE = 1
        const val TYPE_TWO = 2
    }
}