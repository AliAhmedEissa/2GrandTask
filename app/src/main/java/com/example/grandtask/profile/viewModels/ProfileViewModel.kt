package com.example.grandtask.profile.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grandtask.common.utils.DataState
import com.example.grandtask.profile.model.AlbumsModel
import com.example.grandtask.profile.model.PhotosModel
import com.example.grandtask.profile.model.UserModel
import com.example.grandtask.profile.repository.ProfileRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @Singleton private val profileRepo: ProfileRepo,
) : ViewModel() {

    private val _getUserAlbumsState: MutableLiveData<DataState<List<AlbumsModel>?>> =
        MutableLiveData()
    val getUserAlbumsLive: LiveData<DataState<List<AlbumsModel>?>>
        get() = _getUserAlbumsState

    private val _getPhotosState: MutableLiveData<DataState<ArrayList<PhotosModel>?>> =
        MutableLiveData()
    val getPhotosLive: LiveData<DataState<ArrayList<PhotosModel>?>>
        get() = _getPhotosState

    private val _getRandomUser: MutableLiveData<DataState<UserModel>> =
        MutableLiveData()
    val getRandomUserLive: LiveData<DataState<UserModel>>
        get() = _getRandomUser

    fun getRandomUser() {
        _getRandomUser.postValue(DataState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = profileRepo.getUsers()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    val randomUser = data[getRandomUser(data)]
                    cacheUser(randomUser)
                    getUserAlbums(randomUser.id.toString())
                    _getRandomUser.postValue(DataState.Success(randomUser))
                } else {
                    getUserFromCache()
                }
            } catch (error: Exception) {
                getUserFromCache()
            }
        }
    }

    private fun getUserAlbums(userId: String) {
        _getUserAlbumsState.postValue(DataState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = profileRepo.getUserAlbums(userId)
                if (response.isSuccessful) {
                    cacheUserAlbums(response.body())
                    _getUserAlbumsState.postValue(DataState.Success(response.body()))
                } else {
                    // get Data from cache
                    _getUserAlbumsState.postValue(DataState.ErrorMessage("" + response.message()))
                }
            } catch (error: Exception) {
                _getUserAlbumsState.postValue(DataState.ErrorMessage(error.localizedMessage?.toString()
                    ?: "Something went wrong"))
            }
        }
    }

    private fun getRandomUser(allUsers: ArrayList<UserModel>): Int {
        return Random.nextInt(0, allUsers.size)
    }

    fun getPhotos(albumId: String) {
        _getPhotosState.postValue(DataState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = profileRepo.getPhotos(albumId)
                if (response.isSuccessful) {
                    _getPhotosState.postValue(DataState.Success(response.body()!!))
                } else {
                    _getPhotosState.postValue(DataState.ErrorMessage("" + response.message()))
                }
            } catch (error: Exception) {
                _getPhotosState.postValue(DataState.ErrorMessage("" + error.localizedMessage))

            }
        }
    }


    private fun cacheUser(randomUser: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                profileRepo.addUsersInDB(randomUser)

            } catch (error: Exception) {
                _getUserAlbumsState.postValue(DataState.ErrorMessage(error.localizedMessage?.toString()
                    ?: "Something went wrong"))
            }
        }
    }

    private fun cacheUserAlbums(userAlbumsList: ArrayList<AlbumsModel>?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                profileRepo.addUserAlbumsInDB(userAlbumsList)
            } catch (error: Exception) {
                _getUserAlbumsState.postValue(DataState.ErrorMessage(error.localizedMessage?.toString()
                    ?: "Something went wrong"))
            }
        }
    }


    private fun getUserFromCache() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val allUsers: ArrayList<UserModel> = profileRepo.getAllUsersFromDB() as ArrayList<UserModel>
                if (allUsers.isNotEmpty()){
                    val randomUser = allUsers[getRandomUser(allUsers)]
                    _getRandomUser.postValue(DataState.Success(randomUser))
                    getUserAlbumsFromCache(randomUser.id.toString())
                }else _getRandomUser.postValue(DataState.NoDataCachedException("No Data In Cache Check Internet connection"))


            } catch (error: Exception) {
                _getUserAlbumsState.postValue(DataState.ErrorMessage(error.localizedMessage?.toString()
                    ?: "Something went wrong"))
            }
        }
    }


    private fun getUserAlbumsFromCache(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val getAlbumsList: List<AlbumsModel> = profileRepo.getUserAlbumsFromDB(userId)
                _getUserAlbumsState.postValue(DataState.Success(getAlbumsList))
            } catch (error: Exception) {
                _getUserAlbumsState.postValue(DataState.ErrorMessage(error.localizedMessage?.toString()
                    ?: "Something went wrong"))
            }
        }
    }
}

