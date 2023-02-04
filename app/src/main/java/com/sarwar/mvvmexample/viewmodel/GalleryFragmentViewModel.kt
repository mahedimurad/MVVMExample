package com.sarwar.mvvmexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarwar.mvvmexample.data.network.model.UnsplashApiResponse
import com.sarwar.mvvmexample.repo.GalleryPhotosRepository
import com.sarwar.mvvmexample.utils.Resource
import com.sarwar.mvvmexample.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryFragmentViewModel @Inject constructor(
    private val repository: GalleryPhotosRepository) : ViewModel() {

    //coroutine
    private var _imagesLiveData : MutableLiveData<Resource<UnsplashApiResponse>> = MutableLiveData()
    val imagesLiveData : LiveData<Resource<UnsplashApiResponse>> get() =  _imagesLiveData

    private var _searchKeyLiveData : MutableLiveData<String> = MutableLiveData()
    val searchKeyLiveData : LiveData<String> get() =  _searchKeyLiveData

    fun searchImage(keyword:String) {
        _imagesLiveData.postValue(Resource(Status.LOADING,null,""))
        viewModelScope.launch {
           val resource =  repository.searchImage(keyword,0,100)
            _imagesLiveData.postValue(resource)
        }

    }

}