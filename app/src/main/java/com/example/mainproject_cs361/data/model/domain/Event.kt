package com.cikyra.hometeam.data.model.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: String,
    @SerialName("school_id") val schoolId: String,
    val title: String,
    val subtitle: String,
    val body: String,
    @SerialName("photo_urls") val photoUrls: List<String>,
    val price: Int? = null,

    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
