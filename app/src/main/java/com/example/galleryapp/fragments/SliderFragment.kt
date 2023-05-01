package com.example.galleryapp.fragments

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.galleryapp.R
import com.example.galleryapp.adapter.SliderAdapter
import com.example.galleryapp.databinding.ActivitySliderFragmentBinding
import kotlin.collections.ArrayList

class SliderFragment : Fragment(R.layout.activity_slider_fragment) {

    private lateinit var binding: ActivitySliderFragmentBinding
    private lateinit var uriImages: ArrayList<Uri>
    private val snapHelper = LinearSnapHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        uriImages = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            (requireArguments().getParcelableArrayList(
                ARG_IMAGES, Uri::class.java
            ) as ArrayList<Uri>)
        } else {
            requireArguments().getParcelableArrayList<Uri>(
                ARG_IMAGES
            ) as ArrayList<Uri>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ActivitySliderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.sliderRecycler
        with(binding.sliderRecycler) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = SliderAdapter(requireContext(), uriImages)
        }
        snapHelper.attachToRecyclerView(recyclerView)
    }

    companion object {
        private const val ARG_IMAGES = "images"
        fun newInstance(imageUris: ArrayList<Uri>): SliderFragment {
            val fragment = SliderFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_IMAGES, imageUris)
            fragment.arguments = args
            return fragment
        }
    }
}
