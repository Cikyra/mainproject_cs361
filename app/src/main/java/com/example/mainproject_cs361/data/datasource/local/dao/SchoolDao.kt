package com.cikyra.hometeam.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cikyra.hometeam.data.model.local.SchoolEntity

@Dao
interface SchoolDao {
    @Query("SELECT * FROM schools LIMIT 1")
    suspend fun getSchool(): SchoolEntity

    @Query("SELECT * FROM schools")
    suspend fun getAllSchools(): List<SchoolEntity>

    @Insert
    suspend fun insert(school: SchoolEntity)

    @Update
    suspend fun update(school: SchoolEntity)

    @Delete
    suspend fun delete(school: SchoolEntity)
}