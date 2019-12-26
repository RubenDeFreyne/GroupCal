package com.example.groupcal.data

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.groupcal.data.database.dao.GroupDAO
import com.example.groupcal.data.network.GroupApi
import com.example.groupcal.models.Group
import com.example.groupcal.models.User
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*

class GroupRepository (val dao : GroupDAO, val api : GroupApi, val context: Context) {
    val groups = getGroupsFromDb()
    val dbgroups= mutableListOf<com.example.groupcal.data.database.databaseModels.Group>()
    var mem = mutableListOf<User>()

    private var repoJob = Job()
    private val coroutineScope = CoroutineScope(repoJob + Dispatchers.Main )

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

    fun getGroupsFromDb(): Single<List<com.example.groupcal.data.database.databaseModels.Group>> {
        return dao.getAllGroups()

    }

    fun addGroup(group : Group) {
        coroutineScope.launch {
            var getGroup = api.addGroup(group)
            try {
                dao.insert(getGroup.await().toDatabaseGroup())

            }catch (t : Throwable){

            }
        }
    }

    fun getGroupsfromApi() : Deferred<List<Group>> {
        return api.getGroups()
    }

    fun getGroups() : LiveData<List<Group>> {
        var _groups: MutableLiveData<List<Group>> = MutableLiveData()
        val groups: LiveData<List<Group>> = _groups
        val groupList : List<Group>
        if( dao.getRowCount() <= 0 && isConnected()){
            coroutineScope.launch {
                var getGroups = getGroupsfromApi()
                try {
                    var listResult = getGroups.await()
                    _groups.value = listResult
                    listResult.forEach { r -> addGroup(r) }
                }catch (t : Throwable){

                }
            }
        } else {
            _groups.value = getGroupsFromDb().blockingGet().map { g -> g.toGroup() }
        }
        return groups
    }

    protected fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }
}