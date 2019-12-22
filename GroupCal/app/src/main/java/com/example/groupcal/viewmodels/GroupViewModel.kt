package com.example.groupcal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupcal.data.GroupRepository
import com.example.groupcal.models.Group

class GroupViewModel : ViewModel() {

    private val _groups: MutableLiveData<MutableList<Group>> = MutableLiveData()
    val groups: LiveData<MutableList<Group>> = _groups


    private val groupRepository = GroupRepository()

    fun getGroups(){
        groupRepository.initializeGroups()
        _groups.value = groupRepository.groups
    }

}