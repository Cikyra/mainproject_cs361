package com.cikyra.hometeam.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime
import java.util.UUID

@Entity(tableName = "events",
    foreignKeys = [
        ForeignKey(
            entity = SchoolEntity::class,
            parentColumns = ["id"],
            childColumns = ["school_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EventEntity(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "school_id") val schoolId: UUID,
    val title: String,
    val subtitle: String,
    val body: String,
    val price: Int? = null,
    val photoUrl: String? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)