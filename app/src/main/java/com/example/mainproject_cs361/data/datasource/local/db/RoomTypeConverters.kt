package com.cikyra.hometeam.data.datasource.local.db

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class RoomTypeConverters {
    @TypeConverter
    fun epochMillisToLocalDateTime(value: Long): LocalDateTime {
        return Instant.fromEpochMilliseconds(value).toLocalDateTime(TimeZone.currentSystemDefault())
    }

    @TypeConverter
    fun localDateTimeToEpochMillis(localDateTime: LocalDateTime): Long {
        return localDateTime.toInstant(TimeZone.UTC).toEpochMilliseconds()
    }
}