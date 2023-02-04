package com.sarwar.mvvmexample.utils

import com.sarwar.mvvmexample.data.db.entity.ImageEntity
import com.sarwar.mvvmexample.data.network.model.ImageModel

class Mapper {

    companion object {
        private fun imageEntityToImageModel(image: ImageEntity) = ImageModel(image.id, image.urls)

        fun imageEntityListToImageModelList(images: ArrayList<ImageEntity>): ArrayList<ImageModel> {
            val models = ArrayList<ImageModel>()
            for (i in images) {
                models.add(imageEntityToImageModel(i))
            }
            return models
        }

        private fun ImageModelToImageEntity(image: ImageModel) = ImageEntity(image.id, image.urls)

        fun ImageModelListToImageEntityList(images: ArrayList<ImageModel>): ArrayList<ImageEntity> {
            val models = ArrayList<ImageEntity>()
            for (i in images) {
                models.add(ImageModelToImageEntity(i))
            }
            return models
        }
    }
}