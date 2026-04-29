package com.cikyra.hometeam.data.model.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Repeats(val type: String) {
    @SerialName("daily") DAILY("daily"),
    @SerialName("weekly") WEEKLY("weekly"),
    @SerialName("monthly") MONTHLY("monthly"),
}