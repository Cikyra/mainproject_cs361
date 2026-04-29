package com.cikyra.hometeam.data.model.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    @SerialName("photo_url") val photoUrl: String? = null,
    val children: List<String>,
    @SerialName("registered_events") val registeredEvents: List<String>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
