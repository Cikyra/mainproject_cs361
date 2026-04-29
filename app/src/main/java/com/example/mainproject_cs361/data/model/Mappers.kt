package com.cikyra.hometeam.data.model

import com.cikyra.hometeam.data.model.domain.Announcement
import com.cikyra.hometeam.data.model.domain.Event
import com.cikyra.hometeam.data.model.domain.School
import com.cikyra.hometeam.data.model.local.AnnouncementEntity
import com.cikyra.hometeam.data.model.local.EventEntity
import com.cikyra.hometeam.data.model.local.SchoolEntity
import java.util.UUID

// TODO: Do mappers for other entities, not emptyList()
fun SchoolEntity.toSchool(announcements: List<AnnouncementEntity>): School {
    return School(
        id = id.toString(),
        name = name,
        address = address,
        classes = emptyList(),
        events = emptyList(),
        announcements = announcements.toAnnouncement(),
        users = emptyList(),
        levels = emptyList(),
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

fun School.toEntity(): SchoolEntity {
    return SchoolEntity(
        id = UUID.fromString(id),
        name = name,
        address = address,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun AnnouncementEntity.toAnnouncement(): Announcement {
    return Announcement(
        id = id.toString(),
        schoolId = schoolId.toString(),
        title = title,
        subtitle = subtitle,
        body = body,
        photoUrl = photoUrl,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

fun List<AnnouncementEntity>.toAnnouncement(): List<Announcement> {
    return this.map { it.toAnnouncement() }
}

fun Announcement.toEntity(): AnnouncementEntity {
    return AnnouncementEntity(
        id = UUID.fromString(id),
        schoolId = UUID.fromString(schoolId),
        title = title,
        subtitle = subtitle,
        body = body,
        photoUrl = photoUrl,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

fun EventEntity.toEvent(): Event {
    return Event(
        id = id.toString(),
        schoolId = schoolId.toString(),
        title = title,
        subtitle = subtitle,
        body = body,
        // TODO: Add support for multiple photos
        photoUrls = if (photoUrl != null) listOf(photoUrl) else emptyList(),
        price = price,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun List<EventEntity>.toEvent(): List<Event> {
    return this.map { it.toEvent() }
}

fun Event.toEntity(): EventEntity {
    return EventEntity(
        id = UUID.fromString(id),
        schoolId = UUID.fromString(schoolId),
        title = title,
        subtitle = subtitle,
        body = body,
        price = price,
        // TODO: Add support for multiple photos
        photoUrl = photoUrls.firstOrNull(),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun List<Event>.toEntity(): List<EventEntity> {
    return this.map { it.toEntity() }
}
