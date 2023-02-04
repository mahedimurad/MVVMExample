package com.sarwar.mvvmexample.data.network.model



data class UnsplashApiResponse(
     val total: Int,
     val total_pages:Int,
     val results: ArrayList<ImageModel>
     )