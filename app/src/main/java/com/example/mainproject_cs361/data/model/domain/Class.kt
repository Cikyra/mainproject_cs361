package com.example.mainproject_cs361.data.model.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Class(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    //val schedule: Schedule,
    var startTime: String,
    var endTime: String,
    val active: Boolean,
    val description: String? = null,
    @SerialName("photo_urls") val photoUrls: List<String>? = null,

    //registration

    //val createdAt: LocalDateTime,
    //val updatedAt: LocalDateTime,
)
