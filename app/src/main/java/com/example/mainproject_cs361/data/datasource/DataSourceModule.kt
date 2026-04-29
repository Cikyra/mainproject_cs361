package com.cikyra.hometeam.data.datasource

import android.content.Context
import android.content.SharedPreferences
import com.cikyra.hometeam.data.datasource.local.HomeTeamLocalDataSource
import com.cikyra.hometeam.data.datasource.local.dao.AnnouncementDao
import com.cikyra.hometeam.data.datasource.local.dao.EventDao
import com.cikyra.hometeam.data.datasource.local.dao.SchoolDao
import com.cikyra.hometeam.data.datasource.local.impl.HomeTeamLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideHomeTeamLocalDataSource(
        schoolDao: SchoolDao,
        announcementDao: AnnouncementDao,
        eventDao: EventDao
    ): HomeTeamLocalDataSource {
        return HomeTeamLocalDataSourceImpl(
            schoolDao,
            announcementDao,
            eventDao
        )
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("com.cikyra.hometeam.prefs", Context.MODE_PRIVATE)
    }
}