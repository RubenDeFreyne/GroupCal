package com.example.groupcal.data

import com.example.groupcal.models.Event
import com.example.groupcal.models.Group
import com.example.groupcal.models.User

class GroupRepository {
    val groups= mutableListOf<Group>()

    //TODO: Get groups from DAO

    fun initializeGroups(){

        groups += Group (
            id= 1,
            name = "Italy 2019",
            color = "#fc03f4",
            members = mutableListOf<User>(),
            events = mutableListOf<Event>()
        )

        groups += Group (
            id= 2,
            name = "Teambuilding",
            color = "#4afc03",
            members = mutableListOf<User>(),
            events = mutableListOf<Event>()

        )

        groups += Group (
            id= 3,
            name = "Paris 2019",
            color = "#fcd303",
            members = mutableListOf<User>(),
            events = mutableListOf<Event>()
        )

        groups += Group (
            id= 4,
            name = "Spain 2019",
            color = "#fc0303",
            members = mutableListOf<User>(),
            events = mutableListOf<Event>()
        )

        groups += Group (
            id= 5,
            name = "Germany 2018",
            color = "#03fce7",
            members = mutableListOf<User>(),
            events = mutableListOf<Event>()
        )

        groups += Group (
            id= 6,
            name = "Italy 2018",
            color = "#0345fc",
            members = mutableListOf<User>(),
            events = mutableListOf<Event>()
        )

        groups += Group (
            id= 7,
            name = "Barcelona 2018",
            color = "#f0fc03",
            members = mutableListOf<User>(),
            events = mutableListOf<Event>()
        )

        groups += Group (
            id= 8,
            name = "Peru 2018",
            color = "#8c03fc",
            members = mutableListOf<User>(),
            events = mutableListOf<Event>()
        )
    }
}