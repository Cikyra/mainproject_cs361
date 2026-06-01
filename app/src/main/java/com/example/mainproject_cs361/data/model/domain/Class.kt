package com.example.mainproject_cs361.data.model.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Class(
    @SerialName("class_id") var id: Int,
    val title: String,
    val subtitle: String? = null,
    @SerialName("start_time") var startTime: String,
    @SerialName("end_time") var endTime: String,
    val description: String? = null,
)
