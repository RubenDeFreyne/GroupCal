package com.example.groupcal.data

import com.example.groupcal.database.dao.GroupDAO
import com.example.groupcal.models.Event
import com.example.groupcal.models.Group
import com.example.groupcal.models.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GroupRepository (val dao : GroupDAO) {
    val groups = getGroupsFromDb()
    val dbgroups= mutableListOf<com.example.groupcal.database.databaseModels.Group>()
    var mem = mutableListOf<User>()

    //TODO: Get groups from DAO

    /*fun initializeGroups(){

        mem.plusAssign(User(id = 1))

        dbgroups += Group (
            id= 1,
            name = "Italy 2019",
            color = "#fc03f4",
            members = mem
        ).toDatabaseGroup()

        dbgroups += Group (
            id= 2,
            name = "Teambuilding",
            color = "#4afc03",
            members = mem

        ).toDatabaseGroup()

        dbgroups += Group (
            id= 3,
            name = "Paris 2019",
            color = "#fcd303",
            members = mem
        ).toDatabaseGroup()

        dbgroups += Group (
            id= 4,
            name = "Spain 2019",
            color = "#fc0303",
            members = mem
        ).toDatabaseGroup()

        dbgroups += Group (
            id= 5,
            name = "Germany 2018",
            color = "#03fce7",
            members = mem
        ).toDatabaseGroup()

        dbgroups += Group (
            id= 6,
            name = "Italy 2018",
            color = "#0345fc",
            members = mem
        ).toDatabaseGroup()

        dbgroups += Group (
            id= 7,
            name = "Barcelona 2018",
            color = "#f0fc03",
            members = mem
        ).toDatabaseGroup()

        dbgroups += Group (
            id= 8,
            name = "Peru 2018",
            color = "#8c03fc",
            members = mem
        ).toDatabaseGroup()

        dao.insertMany(dbgroups)
    }*/

    fun getGroupsFromDb(): Single<List<com.example.groupcal.database.databaseModels.Group>> {
        return dao.getAllGroups()

    }

    fun addGroup(group : Group) {
        dao.insert(group.toDatabaseGroup())
    }
}