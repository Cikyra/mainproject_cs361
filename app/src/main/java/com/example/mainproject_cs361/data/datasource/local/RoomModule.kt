package com.cikyra.hometeam.data.datasource.local

import android.content.Context
import androidx.room.Room
import com.cikyra.hometeam.data.datasource.local.dao.SchoolDao
import com.cikyra.hometeam.data.datasource.local.db.HomeTeamDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): HomeTeamDatabase {
        return Room.databaseBuilder(
            context,
            HomeTeamDatabase::class.java,
            "hometeam.db",
        )
        .fallbackToDestructiveMigration() // TODO: REMOVE BEFORE LAUNCH
        .build()
    }

    @Singleton
    @Provides
    fun provideSchoolDao(homeTeamDatabase: HomeTeamDatabase): SchoolDao {
        return homeTeamDatabase.getSchoolDao()
    }

    @Singleton
    @Provides
    fun provideAnnouncementDao(db: HomeTeamDatabase) = db.getAnnouncementDao()

    @Singleton
    @Provides
    fun provideEventDao(db: HomeTeamDatabase) = db.getEventDao()
}