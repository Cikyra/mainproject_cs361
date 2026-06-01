package com.example.mainproject_cs361.utils

import com.example.mainproject_cs361.data.repo.features.schedule.ClassRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.mainproject_cs361.data.model.domain.Class

//TODO: outsource check in and registration to schedule-service
class MockClassRepository : ClassRepository{
    var classRegistry = mutableMapOf<String, MutableList<String>>()
    var classCheckIns = mutableMapOf<String, MutableList<String>>()
    fun register(name : String, reg : String) : Boolean{
        classRegistry.getOrPut(name) {mutableListOf()}
        if(!classRegistry.getValue(name).contains(reg)){
            classRegistry.getValue(name).add(reg)
            return true
        }
        else{
            return false //already registered
        }
    }

    fun checkIn(name : String, check : String) : Boolean{
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
                    1, "Beginners",
                    "Youth (ages 7-11) students yellow and orange belts",
                    "4:00pm",
                    "4:30pm",
                )
                val champions = Class(
                    2,
                    "Champions",
                    "Students age 3-6 all belt ranks",
                    "4:30pm",
                    "5:00pm"
                )
                val whiteBelts = Class(
                    3,
                    "White Belts",
                    "Youth (age 7-11) students new to the program and graduating Champions",
                    "5:00pm",
                    "5:30pm",
                )
                val intermediates = Class(
                    4,
                    "Intermediates",
                    "Youth (age 7-11) students purple belt and above",
                    "5:30pm",
                    "6:00pm",
                )
                val teenAdult = Class(
                    5,
                    "Teen & Adults",
                    "Teen & Adult (ages 12+) students all belt ranks",
                    "6:00pm",
                    "6:45pm",
                )
                classList.add(beginners)
                classList.add(champions)
                classList.add(whiteBelts)
                classList.add(intermediates)
                classList.add(teenAdult)
            }
            "Tue" -> {
                val champions = Class(
                    6,
                    "Champions",
                    "Students age 3-6 all belt ranks",
                    "4:00pm",
                    "4:30pm",
                )
                val intermediates = Class(
                    7,
                    "Intermediates",
                    "Youth (age 7-11) students purple belt and above",
                    "4:30pm",
                    "5:00pm",
                )
                val beginners = Class(
                    8, "Beginners",
                    "Youth (ages 7-11) students yellow and orange belts",
                    "5:00pm",
                    "5:30pm",
                )
                val whiteBelts = Class(
                    9,
                    "White Belts",
                    "Youth (age 7-11) students new to the program and graduating Champions",
                    "5:30pm",
                    "6:00pm",
                )
                val teenAdult = Class(
                    10,
                    "Teen & Adults",
                    "Teen & Adult (ages 12+) students all belt ranks",
                    "6:00pm",
                    "6:45pm",
                )
                val sparring = Class(
                    11,
                    "Sparring",
                    "Point-fighting class for students purple belt and above",
                    "7:00pm",
                    "8:00pm",
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
                    12, "Beginners",
                    "Youth (ages 7-11) students yellow and orange belts",
                    "4:00pm",
                    "4:30pm",
                )
                val champions = Class(
                    13,
                    "Champions",
                    "Students age 3-6 all belt ranks",
                    "4:30pm",
                    "5:00pm",
                )
                val whiteBelts = Class(
                    14,
                    "White Belts",
                    "Youth (age 7-11) students new to the program and graduating Champions",
                    "5:00pm",
                    "5:30pm",
                )
                val intermediates = Class(
                    15,
                    "Intermediates",
                    "Youth (age 7-11) students purple belt and above",
                    "5:30pm",
                    "6:00pm",
                )
                val teenAdult = Class(
                    16,
                    "Teen & Adults",
                    "Teen & Adult (ages 12+) students all belt ranks",
                    "6:00pm",
                    "6:45pm",
                )
                classList.add(beginners)
                classList.add(champions)
                classList.add(whiteBelts)
                classList.add(intermediates)
                classList.add(teenAdult)
            }
            "Thu" -> {
                val champions = Class(
                    17,
                    "Champions",
                    "Students age 3-6 all belt ranks",
                    "4:00pm",
                    "4:30pm",
                )
                val intermediates = Class(
                    18,
                    "Intermediates",
                    "Youth (age 7-11) students purple belt and above",
                    "4:30pm",
                    "5:00pm",
                )
                val beginners = Class(
                    19, "Beginners",
                    "Youth (ages 7-11) students yellow and orange belts",
                    "5:00pm",
                    "5:30pm",
                )
                val whiteBelts = Class(
                    20,
                    "White Belts",
                    "Youth (age 7-11) students new to the program and graduating Champions",
                    "5:30pm",
                    "6:00pm",
                )
                val teenAdult = Class(
                    21,
                    "Teen & Adults",
                    "Teen & Adult (ages 12+) students all belt ranks",
                    "6:00pm",
                    "6:45pm",
                )
                classList.add(champions)
                classList.add(intermediates)
                classList.add(beginners)
                classList.add(whiteBelts)
                classList.add(teenAdult)
            }
            "Fri" -> {
                val leadership = Class(
                    22,
                    "Leadership",
                    "Inspiring leaders of TMA",
                    "4:30pm",
                    "5:15pm",
                )
                classList.add(leadership)

                val demo = Class(
                    23,
                    "Demo",
                    "Selected students part of TMA's demonstration team",
                    "5:15pm",
                    "6:00pm",
                )
                classList.add(demo)

                val prep = Class(
                    24,
                    "BBE Prep",
                    "Knowledge-based class for students in the current flight",
                    "6:00pm",
                    "7:00pm",
                )
                classList.add(prep)

                val sparring = Class(
                    25,
                    "Sparring",
                    "Point-fighting class for students purple belt and above",
                    "7:00pm",
                    "8:30pm",
                )
                classList.add(sparring)
            }
            "Sat" -> {
                val teenAdult = Class(
                    26,
                    "Teen & Adults",
                    "Teen & Adult (ages 12+) students all belt ranks",
                    "9:00am",
                    "9:45am",
                )
                val champions = Class(
                    27,
                    "Champions",
                    "Students age 3-6 all belt ranks",
                    "10:00am",
                    "10:30am",
                )
                val beginners = Class(
                    28, "Beginners",
                    "Youth (ages 7-11) students yellow and orange belts",
                    "10:30am",
                    "11:00am",
                )
                val intermediates = Class(
                    29,
                    "Intermediates",
                    "Youth (age 7-11) students purple belt and above",
                    "11:00am",
                    "11:30am",
                )
                classList.add(teenAdult)
                classList.add(champions)
                classList.add(beginners)
                classList.add(intermediates)

                val sparring = Class(
                    30,
                    "Sparring",
                    "Point-fighting class for students purple belt and above",
                    "11:30am",
                    "12:30pm",
                )
                classList.add(sparring)

            }
            "Sun" -> {
                val teenAdult = Class(
                    31,
                    "Teen & Adults",
                    "Teen & Adult (ages 12+) students all belt ranks",
                    "11:00am",
                    "11:45am",
                )
                val beginners = Class(
                    32, "Beginners",
                    "Youth (ages 7-11) students yellow and orange belts",
                    "12:00pm",
                    "12:30pm",
                )
                val intermediates = Class(
                    33,
                    "Intermediates",
                    "Youth (age 7-11) students purple belt and above",
                    "12:00pm",
                    "12:30pm",
                )

                classList.add(teenAdult)
                classList.add(beginners)
                classList.add(intermediates)
            }
        }



        return classList
    }
}