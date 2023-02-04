package com.sarwar.mvvmexample.repo

import androidx.compose.runtime.key
import com.sarwar.mvvmexample.data.db.MasterDatabase
import com.sarwar.mvvmexample.data.db.entity.ImageEntity
import com.sarwar.mvvmexample.data.network.api.ApiService
import com.sarwar.mvvmexample.data.network.model.ImageModel
import com.sarwar.mvvmexample.data.network.model.UnsplashApiResponse
import com.sarwar.mvvmexample.utils.Mapper
import com.sarwar.mvvmexample.utils.NetworkManager
import com.sarwar.mvvmexample.utils.Resource
import com.sarwar.mvvmexample.utils.Status
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class GalleryPhotosRepository @Inject constructor(
    val apiService: ApiService,
    val masterDatabase: MasterDatabase,
    val networkManager: NetworkManager
) {

    suspend fun searchImage(keyword: String, page: Int, size: Int): Resource<UnsplashApiResponse> {
        if (networkManager.isNetworkConnected()) {
            val response = apiService.searchImage(keyword, page, size)
            return when (response.code()) {
                200 -> {
                    val images = Mapper.ImageModelListToImageEntityList(response.body()!!.results)
                    for(i in images){
                        i.keyword = keyword
                    }
                    masterDatabase.getImagesDao()
                        .insertImages(images)

                    Resource.success("successful", response.body())
                }
                403 -> {
                    Resource.error("Unauthorized", null)
                }
                else -> {
                    Resource.error("Something went wrong", null)
                }
            }
        } else {
            val images = masterDatabase.getImagesDao().getImages(keyword)
            return Resource(
                Status.SUCCESS,
                UnsplashApiResponse(images.size, 1, Mapper.imageEntityListToImageModelList(images as ArrayList<ImageEntity>)),
                "successful"
            )
        }

    }
}