package com.example.groupcal.data

import com.example.groupcal.models.Group
import com.example.groupcal.models.User

class GroupRepository {
    val groups= mutableListOf<Group>()

    //TODO: Get groups from DAO

    fun initializeGroups(){

        groups += Group (
            id= 1,
            name = "Group1",
            color = "#OOOOOO",
            members = mutableListOf<User>()
        )

        groups += Group (
            id= 2,
            name = "Group2",
            color = "#OOOOOO",
            members = mutableListOf<User>()
        )
    }
}