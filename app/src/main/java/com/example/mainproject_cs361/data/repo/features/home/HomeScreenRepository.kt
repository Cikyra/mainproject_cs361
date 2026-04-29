package com.cikyra.hometeam.data.repo.features.home

import com.cikyra.hometeam.data.model.domain.Announcement
import com.cikyra.hometeam.data.model.domain.Event

interface HomeScreenRepository {
    fun getMyWeek()
    suspend fun getAnnouncements(): Result<List<Announcement>>
    suspend fun getEvents(): Result<List<Event>>
}