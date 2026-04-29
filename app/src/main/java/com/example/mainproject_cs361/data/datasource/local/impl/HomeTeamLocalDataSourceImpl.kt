package com.cikyra.hometeam.data.datasource.local.impl

import com.cikyra.hometeam.data.datasource.local.HomeTeamLocalDataSource
import com.cikyra.hometeam.data.datasource.local.dao.AnnouncementDao
import com.cikyra.hometeam.data.datasource.local.dao.EventDao
import com.cikyra.hometeam.data.datasource.local.dao.SchoolDao
import com.cikyra.hometeam.data.model.domain.Announcement
import com.cikyra.hometeam.data.model.domain.Event
import com.cikyra.hometeam.data.model.domain.School
import com.cikyra.hometeam.data.model.toAnnouncement
import com.cikyra.hometeam.data.model.toEntity
import com.cikyra.hometeam.data.model.toEvent
import com.cikyra.hometeam.data.model.toSchool
import javax.inject.Inject

class HomeTeamLocalDataSourceImpl @Inject constructor(
    private val schoolDao: SchoolDao,
    private val announcementDao: AnnouncementDao,
    private val eventDao: EventDao
): HomeTeamLocalDataSource {

    override suspend fun getSchool(): School {
        val school = schoolDao.getSchool()
        val announcements = announcementDao.getAnnouncements()
        return school.toSchool(announcements)
    }

    override suspend fun getSchoolName(): String {
        return schoolDao.getSchool().name
    }

    override suspend fun createSchool(school: School) {
        return schoolDao.insert(school.toEntity())
    }

    override suspend fun getAnnouncements(): List<Announcement> {
        return announcementDao.getAnnouncements().toAnnouncement()
    }

    override suspend fun createAnnouncement(announcement: Announcement) {
        announcementDao.insert(announcement.toEntity())
    }

    override suspend fun getEvents(): List<Event> {
        return eventDao.getEvents().toEvent()
    }

    override suspend fun createEvent(event: Event) {
        eventDao.insert(event.toEntity())
    }
}