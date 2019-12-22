package com.example.groupcal.data

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
            members = mutableListOf<User>()
        )

        groups += Group (
            id= 2,
            name = "Teambuilding",
            color = "#4afc03",
            members = mutableListOf<User>()
        )

        groups += Group (
            id= 1,
            name = "Paris 2019",
            color = "#fcd303",
            members = mutableListOf<User>()
        )

        groups += Group (
            id= 1,
            name = "Spain 2019",
            color = "#fc0303",
            members = mutableListOf<User>()
        )

        groups += Group (
            id= 1,
            name = "Germany 2018",
            color = "#03fce7",
            members = mutableListOf<User>()
        )

        groups += Group (
            id= 1,
            name = "Italy 2018",
            color = "#0345fc",
            members = mutableListOf<User>()
        )

        groups += Group (
            id= 1,
            name = "Barcelona 2018",
            color = "#f0fc03",
            members = mutableListOf<User>()
        )

        groups += Group (
            id= 1,
            name = "Peru 2018",
            color = "#8c03fc",
            members = mutableListOf<User>()
        )
    }
}