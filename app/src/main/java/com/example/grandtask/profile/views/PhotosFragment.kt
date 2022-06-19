package com.example.grandtask.profile.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.example.grandtask.common.BaseFragment
import com.example.grandtask.common.utils.DataState
import com.example.grandtask.databinding.FragmentPhotosBinding
import com.example.grandtask.profile.adapter.PhotosAdapter
import com.example.grandtask.profile.viewModels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosFragment : BaseFragment() {

    private lateinit var binding: FragmentPhotosBinding
    private val vm: ProfileViewModel by viewModels()
    private val photosAdapter by lazy { PhotosAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val albumId = arguments?.getString("albumId")
        vm.getPhotos(albumId!!)
        subscribeData()
        setBtnListeners()
    }

    private fun setBtnListeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                photosAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun subscribeData() {
        vm.getPhotosLive.observe(viewLifecycleOwner) {
            it.let {
                when (it) {
                    is DataState.Loading -> {
                        showProgressView()
                    }
                    is DataState.Success -> {
                        photosAdapter.setData(it.data!!)
                        binding.photosRecycler.adapter = photosAdapter
                        hideProgressView()
                    }
                    is DataState.ErrorMessage -> {
                        showToast(it.error)
                        hideProgressView()
                    }

                }

            }
        }
    }
}