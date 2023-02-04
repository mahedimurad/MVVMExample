package com.sarwar.mvvmexample.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarwar.mvvmexample.data.db.entity.ImageEntity

@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageEntity: ImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(imageEntity: List<ImageEntity>)

    @Query("select * from images where keyword = :key")
    suspend fun getImages(key: String) : List<ImageEntity>
}