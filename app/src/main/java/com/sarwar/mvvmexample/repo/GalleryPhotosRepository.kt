package com.sarwar.mvvmexample.repo

import com.sarwar.mvvmexample.data.network.api.ApiService
import com.sarwar.mvvmexample.data.network.model.ImageModel
import com.sarwar.mvvmexample.data.network.model.UnsplashApiResponse
import com.sarwar.mvvmexample.utils.Resource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class GalleryPhotosRepository @Inject constructor(val apiService: ApiService) {

    suspend fun searchImage(keyword: String, page:Int, size: Int) : Resource<UnsplashApiResponse>{
       val response =  apiService.searchImage(keyword,page,size)
        return when(response.code()){
            200->{
                Resource.success("successful",response.body())
            }
            403 ->{
                Resource.error("Unauthorized",null)
            }
            else->{
                Resource.error("Something went wrong",null)
            }
        }
    }
}