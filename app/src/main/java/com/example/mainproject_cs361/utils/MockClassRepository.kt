package com.example.mainproject_cs361.utils

import com.example.mainproject_cs361.data.repo.features.schedule.ClassRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.mainproject_cs361.data.model.domain.Class

class MockClassRepository : ClassRepository{
    var classRegistry = mutableMapOf<String, MutableList<Class>>()
    var classCheckIns = mutableMapOf<String, MutableList<Class>>()
    fun register(name : String, reg : Class) : Boolean{
        classRegistry.getOrPut(name) {mutableListOf()}
        if(!classRegistry.getValue(name).contains(reg)){
            classRegistry.getValue(name).add(reg)
            return true
        }
        else{
            return false //already registered
        }
    }

    fun checkIn(name : String, check : Class) : Boolean{
        classCheckIns.getOrPut(name) {mutableListOf()}
        if(!classCheckIns.getValue(name).contains(check)){
            classCheckIns.getValue(name).add(check)
            return true
        }
        else{
            return false //already listed as check in for that class
        }
    }

    override fun getClasses(day: Date?): MutableList<Class> {
        val formatter = SimpleDateFormat("EEE", Locale.US)
        val dayString = formatter.format(day?.time)

        val classList : MutableList<Class> = mutableListOf()

        //fleshed out class schedule
        when (dayString) {
            "Mon" -> {
                val beginners = Class(
                    "MonBeginners", "Beginners",
                    "Youth (ages 7-11) students yellow and orange belts",
                    "4:00pm",
                    "4:30pm",
                    true
                )
                val champions = Class(
                    "MonChampions",
                    "Champions",
                    "Students age 3-6 all belt ranks",
                    "4:30pm",
                    "5:00pm",
                    true
                )
                val whiteBelts = Class(
                    "MonWhiteBelts",
                    "White Belts",
                    "Youth (age 7-11) students new to the program and graduating Champions",
                    "5:00pm",
                    "5:30pm",
                    true
                )
                val intermediates = Class(
                    "MonIntermediates",
                    "Intermediates",
                    "Youth (age 7-11) students purple belt and above",
                    "5:30pm",
                    "6:00pm",
                    true
                )
                val teenAdult = Class(
                    "MonTeenAdults",
                    "Teen & Adults",
                    "Teen & Adult (ages 12+) students all belt ranks",
                    "6:00pm",
                    "6:45pm",
                    true
                )
                classList.add(beginners)
                classList.add(champions)
                classList.add(whiteBelts)
                classList.add(intermediates)
                classList.add(teenAdult)
            }
            "Tue" -> {
                val champions = Class(
                    "TueChampions",
                    "Champions",
                    "Students age 3-6 all belt ranks",
                    "4:00pm",
                    "4:30pm",
                    true
                )
                val intermediates = Class(
                    "TueIntermediates",
                    "Intermediates",
                    "Youth (age 7-11) students purple belt and above",
                    "4:30pm",
                    "5:00pm",
                    true
                )
                val beginners = Class(
                    "TueBeginners", "Beginners",
                    "Youth (ages 7-11) students yellow and orange belts",
                    "5:00pm",
                    "5:30pm",
                    true
                )
                val whiteBelts = Class(
                    "TueWhiteBelts",
                    "White Belts",
                    "Youth (age 7-11) students new to the program and graduating Champions",
                    "5:30pm",
                    "6:00pm",
                    true
                )
                val teenAdult = Class(
                    "TueTeenAdults",
                    "Teen & Adults",
                    "Teen & Adult (ages 12+) students all belt ranks",
                    "6:00pm",
                    "6:45pm",
                    true
                )
                val sparring = Class(
                    "Sparring",
                    "Sparring",
                    "Point-fighting class for students purple belt and above",
                    "7:00pm",
                    "8:00pm",
                    true
                )
                classList.add(champions)
                classList.add(intermediates)
                classList.add(beginners)
                classList.add(whiteBelts)
                classList.add(teenAdult)
                classList.add(sparring)
            }
            "Wed" -> {
                val beginners = Class(
                    "WedBeginners", "Beginners",
                    "Youth (ages 7-11) students yellow and orange belts",
                    "4:00pm",
                    "4:30pm",
                    true
                )
                val champions = Class(
                    "WedChampions",
                    "Champions",
                    "Students age 3-6 all belt ranks",
                    "4:30pm",
                    "5:00pm",
                    true
                )
                val whiteBelts = Class(
                    "WedWhiteBelts",
                    "White Belts",
                    "Youth (age 7-11) students new to the program and graduating Champions",
                    "5:00pm",
                    "5:30pm",
                    true
                )
                val intermediates = Class(
                    "WedIntermediates",
                    "Intermediates",
                    "Youth (age 7-11) students purple belt and above",
                    "5:30pm",
                    "6:00pm",
                    true
                )
                val teenAdult = Class(
                    "WedTeenAdults",
                    "Teen & Adults",
                    "Teen & Adult (ages 12+) students all belt ranks",
                    "6:00pm",
                    "6:45pm",
                    true
                )
                classList.add(beginners)
                classList.add(champions)
                classList.add(whiteBelts)
                classList.add(intermediates)
                classList.add(teenAdult)
            }
            "Thu" -> {
                val champions = Class(
                    "ThuChampions",
                    "Champions",
                    "Students age 3-6 all belt ranks",
                    "4:00pm",
                    "4:30pm",
                    true
                )
                val intermediates = Class(
                    "ThuIntermediates",
                    "Intermediates",
                    "Youth (age 7-11) students purple belt and above",
                    "4:30pm",
                    "5:00pm",
                    true
                )
                val beginners = Class(
                    "ThuBeginners", "Beginners",
                    "Youth (ages 7-11) students yellow and orange belts",
                    "5:00pm",
                    "5:30pm",
                    true
                )
                val whiteBelts = Class(
                    "ThuWhiteBelts",
                    "White Belts",
                    "Youth (age 7-11) students new to the program and graduating Champions",
                    "5:30pm",
                    "6:00pm",
                    true
                )
                val teenAdult = Class(
                    "ThuTeenAdults",
                    "Teen & Adults",
                    "Teen & Adult (ages 12+) students all belt ranks",
                    "6:00pm",
                    "6:45pm",
                    true
                )
                classList.add(champions)
                classList.add(intermediates)
                classList.add(beginners)
                classList.add(whiteBelts)
                classList.add(teenAdult)
            }
            "Fri" -> {
                val leadership = Class(
                    "FriLeadership",
                    "Leadership",
                    "Inspiring leaders of TMA",
                    "4:30pm",
                    "5:15pm",
                    true
                )
                classList.add(leadership)

                val demo = Class(
                    "FriDemo",
                    "Demo",
                    "Selected students part of TMA's demonstration team",
                    "5:15pm",
                    "6:00pm",
                    true
                )
                classList.add(demo)

                val prep = Class(
                    "FriPrep",
                    "BBE Prep",
                    "Knowledge-based class for students in the current flight",
                    "6:00pm",
                    "7:00pm",
                    true
                )
                classList.add(prep)

                val sparring = Class(
                    "FriSparring",
                    "Sparring",
                    "Point-fighting class for students purple belt and above",
                    "7:00pm",
                    "8:30pm",
                    true
                )
                classList.add(sparring)
            }
            "Sat" -> {
                val teenAdult = Class(
                    "SatTeenAdults",
                    "Teen & Adults",
                    "Teen & Adult (ages 12+) students all belt ranks",
                    "9:00am",
                    "9:45am",
                    true
                )
                val champions = Class(
                    "SatChampions",
                    "Champions",
                    "Students age 3-6 all belt ranks",
                    "10:00am",
                    "10:30am",
                    true
                )
                val beginners = Class(
                    "SatBeginners", "Beginners",
                    "Youth (ages 7-11) students yellow and orange belts",
                    "10:30am",
                    "11:00am",
                    true
                )
                val intermediates = Class(
                    "SatIntermediates",
                    "Intermediates",
                    "Youth (age 7-11) students purple belt and above",
                    "11:00am",
                    "11:30am",
                    true
                )
                classList.add(teenAdult)
                classList.add(champions)
                classList.add(beginners)
                classList.add(intermediates)

                val sparring = Class(
                    "SatSparring",
                    "Sparring",
                    "Point-fighting class for students purple belt and above",
                    "11:30am",
                    "12:30pm",
                    true
                )
                classList.add(sparring)

            }
            "Sun" -> {
                val teenAdult = Class(
                    "SunTeenAdults",
                    "Teen & Adults",
                    "Teen & Adult (ages 12+) students all belt ranks",
                    "11:00am",
                    "11:45am",
                    true
                )
                val beginners = Class(
                    "SunBeginners", "Beginners",
                    "Youth (ages 7-11) students yellow and orange belts",
                    "12:00pm",
                    "12:30pm",
                    true
                )
                val intermediates = Class(
                    "SunIntermediates",
                    "Intermediates",
                    "Youth (age 7-11) students purple belt and above",
                    "12:00pm",
                    "12:30pm",
                    true
                )

                classList.add(teenAdult)
                classList.add(beginners)
                classList.add(intermediates)
            }
        }



        return classList
    }
}