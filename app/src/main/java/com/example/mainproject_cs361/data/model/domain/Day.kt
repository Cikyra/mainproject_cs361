package com.cikyra.hometeam.data.model.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Day(val day: Int) {
    @SerialName("monday") MONDAY(0),
    @SerialName("tuesday") TUESDAY(1),
    @SerialName("wednesday") WEDNESDAY(2),
    @SerialName("thursday") THURSDAY(3),
    @SerialName("friday") FRIDAY(4),
    @SerialName("saturday") SATURDAY(5),
    @SerialName("sunday") SUNDAY(6)
}