package com.cikyra.hometeam.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cikyra.hometeam.data.model.local.EventEntity
import java.util.UUID

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    suspend fun getEvents(): List<EventEntity>

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getEventById(id: UUID): EventEntity

    @Insert
    suspend fun insert(eventEntity: EventEntity)

    @Update
    suspend fun update(eventEntity: EventEntity)

    @Delete
    suspend fun delete(eventEntity: EventEntity)
}