package com.example.groupcal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupcal.data.GroupRepository
import com.example.groupcal.models.Group

class GroupViewModel : ViewModel() {

    private val groupRepository = GroupRepository()

    private val _groups: MutableLiveData<MutableList<Group>> = getGroups()
    val groups: LiveData<MutableList<Group>> = _groups




    fun getGroups() : MutableLiveData<MutableList<Group>>{
        val list : MutableLiveData<MutableList<Group>> = MutableLiveData()
        groupRepository.initializeGroups()
        list.value = groupRepository.groups
        return list
    }

}