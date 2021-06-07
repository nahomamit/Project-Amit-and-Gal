package com.example.final_project_amit_and_gal


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TabDatabaseDao {

    @Insert
    fun insert(tab: Tab)

    @Update
    fun update(tab: Tab)

    @Query("SELECT * from tabs_table WHERE name = :key")
    fun get(key: String): Tab?

    @Query("DELETE FROM tabs_table")
    fun clear()

    @Query("SELECT * FROM tabs_table ORDER BY name DESC")
    fun getAllTabs(): Array<Tab>

    @Query("SELECT category FROM tabs_table GROUP BY category")
    fun getCategories(): Array<String>

    @Query("SELECT difficulty FROM tabs_table GROUP BY difficulty")
    fun getDifficulty(): Array<Int>

    @Query("SELECT * FROM tabs_table WHERE category = :cate and difficulty = :diff")
    fun getTabs(cate: String, diff:Int): List<Tab>

    @Query("SELECT * FROM tabs_table WHERE category = :cate and url != '' ORDER BY RANDOM() LIMIT 3")
    fun get3TabsByCategory(cate: String): MutableList<Tab>

    @Query("SELECT * FROM tabs_table WHERE category = :cate and url != '' ORDER BY RANDOM() LIMIT 1")
    fun getTabByCategory(cate: String): Tab

    @Query("SELECT * FROM tabs_table WHERE url != '' ORDER BY RANDOM()")
    fun getOneTab(): Tab

    @Query("SELECT * FROM tabs_table WHERE url != '' ORDER BY RANDOM() LIMIT 4")
    fun getFourTabs(): MutableList<Tab>

    @Query("SELECT COUNT(*) FROM tabs_table")
    fun getNumTabs(): Int

    //@Query("SELECT category FROM (SELECT DISTINCT category FROM tabs_table) ORDER BY RAND() LIMIT 1")

}