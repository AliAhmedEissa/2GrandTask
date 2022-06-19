package com.example.grandtask.profile.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.grandtask.R
import com.example.grandtask.common.BaseFragment
import com.example.grandtask.common.utils.DataState
import com.example.grandtask.databinding.FragmentProfileBinding
import com.example.grandtask.profile.adapter.UserAlbumsAdapter
import com.example.grandtask.profile.model.UserModel
import com.example.grandtask.profile.viewModels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private val vm: ProfileViewModel by viewModels()

    private val usersAdapter by lazy { UserAlbumsAdapter(onAlbumClickCallback) }

    private val onAlbumClickCallback: (albumId: String) -> Unit = { albumId ->
        val direction = ProfileFragmentDirections.fromProfileToPhotosFragment(albumId)
        findNavController().navigate(direction)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.getRandomUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeData()
    }

    private fun initView(userModel: UserModel) {
        binding.txtUserName.text = userModel.name
        binding.txtUserAddress.text =
            getString(
                R.string.address_view,
                userModel.address?.city.toString(),
                userModel.address?.suite.toString(),
                userModel.address?.street.toString(),
                userModel.address?.geo?.lat.toString(),
                userModel.address?.geo?.lng.toString()
            )

    }

    private fun subscribeData() {
        vm.getRandomUserLive.observe(viewLifecycleOwner) {
            it.let {
                when (it) {
                    is DataState.Loading -> {
                        showProgressView()
                    }
                    is DataState.Success -> {
                        initView(it.data)
                    }
                    is DataState.ErrorMessage -> {
                        showToast(it.error)
                        hideProgressView()
                    }
                    is DataState.Finished -> {
                        hideProgressView()
                    }
                    is DataState.NoDataCachedException -> {
                       showToast(it.message)
                    }
                    else -> {
                        hideProgressView()
                    }
                }
            }
        }

        vm.getUserAlbumsLive.observe(viewLifecycleOwner) {
            it.let {
                when (it) {
                    is DataState.Loading -> {
                        showProgressView()
                    }
                    is DataState.Success -> {
                        usersAdapter.updateData(it.data!!)
                        binding.userAlbumsRecycler.adapter = usersAdapter
                        hideProgressView()
                    }
                    is DataState.ErrorMessage -> {
                        showToast(it.error)
                        hideProgressView()
                    }
                    else -> {
                        hideProgressView()
                    }
                }
            }
        }
    }
}