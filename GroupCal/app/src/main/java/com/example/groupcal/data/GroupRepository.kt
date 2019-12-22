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
    }
}