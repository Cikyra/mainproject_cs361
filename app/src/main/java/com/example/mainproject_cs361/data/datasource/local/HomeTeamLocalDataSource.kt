package com.cikyra.hometeam.data.datasource.local

import com.cikyra.hometeam.data.model.domain.Announcement
import com.cikyra.hometeam.data.model.domain.Event
import com.cikyra.hometeam.data.model.domain.School

interface HomeTeamLocalDataSource {
    suspend fun getSchool(): School
    suspend fun getSchoolName(): String
    suspend fun createSchool(school: School)

    suspend fun getAnnouncements(): List<Announcement>
    suspend fun createAnnouncement(announcement: Announcement)

    suspend fun getEvents(): List<Event>
    suspend fun createEvent(event: Event)
}