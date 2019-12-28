package com.example.groupcal.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.groupcal.data.database.dao.GroupDAO
import com.example.groupcal.data.network.GroupApi
import com.example.groupcal.models.Group
import kotlinx.coroutines.*

/**
 * Repository used for managing all operations related to [Group]
 * This class implements [BaseRepo]
 *
 * @param dao class used to communicate with [CalDatabase]
 * @param api class used to communicate with backend
 * @param context used to check internet connectivity
 */
class GroupRepository (val dao : GroupDAO, val api : GroupApi, context: Context) : BaseRepo(context){

    /**
     * Defines the Coroutine Job used by the repo
     */
    private var repoJob = Job()

    /**
     * Defines the Coroutine Scope used by the repo
     */
    private val coroutineScope = CoroutineScope(repoJob + Dispatchers.Main )

    /**
     * get a specific [Group] object in the [CalDatabase]
     *
     * @param id The id of the group
     *
     * @return group from database
     */
    fun getById(id: String): Group{
        return dao.get(id).blockingGet()!!.toGroup()
    }

    /**
     * Create a new [Group] in the backend through [GroupApi] and save it in the database through [GroupDAO].
     * This also converts the response from the backend to an [Group] object
     *
     * @param group The group that has to be created
     */
    fun addGroup(group : Group) {
        coroutineScope.launch {
            var getGroup = api.addGroup(group)
            try {
                var listResult = getGroup.await()
                dao.insert(group.toDatabaseGroup())
            }catch (t : Throwable){

            }
        }
    }

    /**
     * Get the [Group] objects from [GroupApi] if there is an internet connection, otherwise from [GroupDAO]
     * the groups are requested from the backend stored in the database.
     *
     * @return observable list of [Group] objects
     */
    fun getAllGroups() : LiveData<List<Group>> {
        var _groups: MutableLiveData<List<Group>> = MutableLiveData()
        var groups: LiveData<List<Group>> = _groups
        if( dao.getRowCount() <= 0 && isConnected()){
            coroutineScope.launch {
                var getGroups = api.getGroups()
                try {
                    var listResult = getGroups.await()
                    _groups.value = listResult
                    listResult.forEach { r -> dao.insert(r.toDatabaseGroup()) }
                }catch (t : Throwable){

                }
            }
        } else {
            return Transformations.map(dao.getAllGroups(), {l -> l.map { g -> g.toGroup() }})
        }
        return groups
    }



}