package com.example.galleryapp.fragments

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import com.example.galleryapp.databinding.FragmentPhotoListBinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.R
import com.example.galleryapp.adapter.PhotosAdapter
import java.io.File

class PhotoListFragment : Fragment(R.layout.fragment_photo_list),
    PhotosAdapter.SelectionChangeListener {
    private lateinit var binding: FragmentPhotoListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageDirectory = File(Environment.getExternalStorageDirectory(), CAMERAX_IMAGE_FOLDER)
        val imageFiles =
            imageDirectory.listFiles()?.filter { it.extension == IMAGE_EXTENSION }?.toList()
                ?: emptyList()

        val photosAdapter = PhotosAdapter(requireContext(), imageFiles, this)

        with(binding.galleryRecycler) {
            layoutManager = GridLayoutManager(requireContext(), 2).also {
                it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position % 3 == 0) 2
                        else 1
                    }
                }
            }
            adapter = photosAdapter
        }

        binding.goToCamera.setOnClickListener {
            findNavController().navigate(R.id.action_photoListFragment_to_cameraFragmentActivity)
        }

        binding.goToSlider.setOnClickListener {
            navigateToSliderScreenFragment(photosAdapter.getImageSelected())
        }
        binding.goToSlider.visibility = View.GONE
    }

    override fun onSelectionChanged(selectedCount: Int) {
        binding.goToSlider.visibility = if (selectedCount > 0) View.VISIBLE else View.GONE
    }

    private fun navigateToSliderScreenFragment(selectedImages: List<File>) {
        // Convert selected images to URIs
        val selectedImageUris = ArrayList<Uri>()
        for (imageFile in selectedImages) {
            val imageUri = Uri.fromFile(imageFile)
            selectedImageUris.add(imageUri)
        }
        // Create SliderScreenFragment instance and pass the selected image URIs
        val sliderFragment = SliderFragment.newInstance(selectedImageUris)
        parentFragmentManager.beginTransaction().replace(R.id.host_fragment, sliderFragment)
            .addToBackStack(null).commit()
    }


    companion object {
        private const val CAMERAX_IMAGE_FOLDER = "Pictures/CameraX-Folder"
        private const val IMAGE_EXTENSION = "jpg"
    }
}