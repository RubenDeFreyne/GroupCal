package com.example.groupcal.viewmodels

import androidx.lifecycle.ViewModel
import com.example.groupcal.data.GroupRepository
import com.example.groupcal.models.Group

class GroupViewModel : ViewModel() {
    /* After Merging Room
    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    val categories: LiveData<List<Category>> = _categories
     */

    private val groupRepository = GroupRepository()
    var groups: List<Group> = ArrayList<Group>()

    fun getGroups(){
        groupRepository.initializeGroups()
        groups = groupRepository.groups
    }

}