package com.example.mainproject_cs361.data.repo.features.schedule

import com.example.mainproject_cs361.data.model.domain.Class
import java.util.Date

interface ClassRepository {
    fun getClasses(day: Date?): List<Class>
}