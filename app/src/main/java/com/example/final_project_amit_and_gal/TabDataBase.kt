package com.example.final_project_amit_and_gal


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tab::class], version = 1, exportSchema = false)
abstract class TabDataBase : RoomDatabase() {

    abstract val tabDao: TabDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: TabDataBase? = null

        fun getInstance(context: Context): TabDataBase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TabDataBase::class.java,
                        "tab_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}