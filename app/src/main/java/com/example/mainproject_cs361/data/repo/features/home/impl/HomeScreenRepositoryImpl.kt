package com.cikyra.hometeam.data.repo.features.home.impl

import com.cikyra.hometeam.data.datasource.local.HomeTeamLocalDataSource
import com.cikyra.hometeam.data.model.domain.Announcement
import com.cikyra.hometeam.data.model.domain.Event
import com.cikyra.hometeam.data.repo.features.home.HomeScreenRepository
import kotlinx.coroutines.ensureActive
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class HomeScreenRepositoryImpl @Inject constructor(
    private val localDataSource: HomeTeamLocalDataSource
): HomeScreenRepository {
    override fun getMyWeek() {
        TODO("Not yet implemented")
    }

    override suspend fun getAnnouncements(): Result<List<Announcement>> {
        return try {
            Result.success(localDataSource.getAnnouncements())
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            Result.failure(e)
        }
    }

    override suspend fun getEvents(): Result<List<Event>> {
        return try {
            Result.success(localDataSource.getEvents())
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            Result.failure(e)
        }
    }
}