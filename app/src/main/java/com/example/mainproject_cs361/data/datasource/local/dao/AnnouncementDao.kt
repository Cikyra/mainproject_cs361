package com.cikyra.hometeam.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cikyra.hometeam.data.model.local.AnnouncementEntity
import java.util.UUID

@Dao
interface AnnouncementDao {
    @Query("SELECT * FROM announcements")
    suspend fun getAnnouncements(): List<AnnouncementEntity>

    @Query("SELECT * FROM announcements WHERE id = :id")
    suspend fun getAnnouncementById(id: UUID): AnnouncementEntity

    @Insert
    suspend fun insert(announcementEntity: AnnouncementEntity)

    @Update
    suspend fun update(announcementEntity: AnnouncementEntity)

    @Delete
    suspend fun delete(announcementEntity: AnnouncementEntity)
}