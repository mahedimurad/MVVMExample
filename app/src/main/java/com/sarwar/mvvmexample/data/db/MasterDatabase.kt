package com.sarwar.mvvmexample.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.sarwar.mvvmexample.data.db.dao.ContactDao
import com.sarwar.mvvmexample.data.db.entity.Contact


@Database(entities = [Contact::class], version = 2)
abstract class MasterDatabase: RoomDatabase() {

    companion object{

        private var instance:  MasterDatabase? = null

        fun getInstance(context: Context): MasterDatabase {
            return instance ?: synchronized(this){
                return instance ?: databaseBuilder(
                    context,
                    MasterDatabase::class.java,
                    "MasterDatabase"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build().also { instance = it }
            }
        }
    }

    abstract fun getContactDao(): ContactDao

    //Singleton - pattern
}