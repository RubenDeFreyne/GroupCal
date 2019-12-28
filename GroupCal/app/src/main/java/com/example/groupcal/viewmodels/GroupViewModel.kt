package com.example.groupcal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.groupcal.data.repositories.GroupRepository
import com.example.groupcal.models.Group

/**
 * ViewModel for [GroupFragment]
 *
 * @param repo The Repository for Events
 */
class GroupViewModel(val repo : GroupRepository) : ViewModel() {

    var groups: LiveData<List<Group>> = getGroupsFromRepo()

    /**
     * Get groups from repo
     */
    fun getGroupsFromRepo() : LiveData<List<Group>>{
        return repo.getAllGroups()
    }

}