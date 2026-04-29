package com.cikyra.hometeam.data.model.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Announcement(
    val id: String,
    @SerialName("school_id") val schoolId: String,
    val title: String,
    val subtitle: String,
    val body: String,
    @SerialName("photo_url") val photoUrl: String? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
