package com.cikyra.hometeam.data.model.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    @SerialName("start_time") val startTime: LocalDateTime,
    @SerialName("end_time") val endTime: LocalDateTime,
    val days: List<Day>,
    val repeats: Repeats
)