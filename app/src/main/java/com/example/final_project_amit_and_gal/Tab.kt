package com.example.final_project_amit_and_gal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabs_table")
data class Tab(
    @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "gender")
    var gender:String,
    @ColumnInfo(name = "url")
    var url:String,
    @ColumnInfo(name = "category")
    var category:String,
    @ColumnInfo(name = "difficulty")
    var difficulty:Int,
    @ColumnInfo(name = "description")
    var description:String
)