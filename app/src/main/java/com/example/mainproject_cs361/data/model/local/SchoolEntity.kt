package com.cikyra.hometeam.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime
import java.util.UUID

@Entity(tableName = "schools")
data class SchoolEntity(
    @PrimaryKey val id: UUID,
    val name: String,
    val address: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
