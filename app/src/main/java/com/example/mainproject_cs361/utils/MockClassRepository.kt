package com.example.mainproject_cs361.utils

import com.example.mainproject_cs361.data.repo.features.schedule.ClassRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.mainproject_cs361.data.model.domain.Class
import com.example.mainproject_cs361.data.model.domain.Schedule
import kotlinx.datetime.LocalDateTime

class MockClassRepository : ClassRepository{
    override fun getClasses(day: Date?): MutableList<Class> {
        val formatter = SimpleDateFormat("EEE", Locale.US)
        val dayString = formatter.format(day?.time)

        val classList : MutableList<Class> = mutableListOf()

        val beginners = Class("Beginners","Beginners",
            "Youth (ages 7-11) students yellow and orange belts",
            "4:00pm",
            "4:30pm",
            true)

        val champions = Class("Champions",
            "Champions",
            "Students age 3-6 all belt ranks",
            "4:30pm",
            "5:00pm",
            true)

        val whiteBelts = Class("White Belts",
            "White Belts",
            "Youth (age 7-11) students new to the program and graduating Champions",
            "5:00pm",
            "5:30pm",
            true)

        val intermediates = Class("Intermediates",
            "Intermediates",
            "Youth (age 7-11) students purple belt and above",
            "5:30pm",
            "6:00pm",
            true)

        val teenAdult = Class("Teen & Adults",
            "Teen & Adults",
            "Teen & Adult (ages 12+) students all belt ranks",
            "6:00pm",
            "6:45pm",
            true)


        if(dayString == "Mon" || dayString == "Wed"){
            classList.add(beginners)
            classList.add(champions)
            classList.add(whiteBelts)
            classList.add(intermediates)
            classList.add(teenAdult)
        }
        else if(dayString == "Tue" || dayString == "Thu"){

            champions.startTime = "4:00pm"
            champions.endTime = "4:30pm"
            classList.add(champions)

            intermediates.startTime = "4:30pm"
            intermediates.endTime = "5:00pm"
            classList.add(intermediates)

            beginners.startTime = "5:00pm"
            beginners.endTime = "5:30pm"
            classList.add(beginners)

            whiteBelts.startTime = "5:30pm"
            whiteBelts.endTime = "6:00pm"
            classList.add(whiteBelts)

            classList.add(teenAdult)
            val sparring = Class("Sparring",
                "Sparring",
                "Point-fighting class for students purple belt and above",
                "7:00pm",
                "8:00pm",
                true)
            classList.add(sparring)
        }
        else if(dayString == "Fri"){
            val leadership = Class("Leadership",
                "Leadership",
                "Inspiring leaders of TMA",
                "4:30pm",
                "5:15pm",
                true)
            classList.add(leadership)

            val demo = Class("Demo",
                "Demo",
                "Selected students part of TMA's demonstration team",
                "5:15pm",
                "6:00pm",
                true)
            classList.add(demo)

            val prep = Class("Prep",
                "BBE Prep",
                "Knowledge-based class for students in the current flight",
                "6:00pm",
                "7:00pm",
                true)
            classList.add(prep)

            val sparring = Class("Sparring",
                "Sparring",
                "Point-fighting class for students purple belt and above",
                "7:00pm",
                "8:30pm",
                true)
            classList.add(sparring)
        }
        else if (dayString == "Sat"){
            teenAdult.startTime = "9:00am"
            teenAdult.endTime = "9:45am"
            classList.add(teenAdult)

            champions.startTime = "10:00am"
            champions.endTime = "10:30am"
            classList.add(champions)

            beginners.startTime = "10:30am"
            beginners.endTime = "11:00am"
            classList.add(beginners)

            intermediates.startTime = "11:00am"
            intermediates.endTime = "11:30am"
            classList.add(intermediates)

            val sparring = Class("Sparring",
                "Sparring",
                "Point-fighting class for students purple belt and above",
                "11:30am",
                "12:30pm",
                true)
            classList.add(sparring)

        }
        else if (dayString == "Sun") {
            teenAdult.startTime = "11:00am"
            teenAdult.endTime = "11:45am"
            classList.add(teenAdult)

            beginners.startTime = "12:00pm"
            beginners.endTime = "12:30pm"
            classList.add(beginners)

            intermediates.startTime = "12:00pm"
            intermediates.endTime = "12:30pm"
            classList.add(intermediates)
        }



        return classList
    }
}