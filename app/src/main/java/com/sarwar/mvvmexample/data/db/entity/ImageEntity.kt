package com.sarwar.mvvmexample.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sarwar.mvvmexample.data.network.model.Urls

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey val id:String,
    @Embedded val urls: Urls,
    var keyword: String = ""
)