package com.example.groupcal.viewmodels

import androidx.lifecycle.ViewModel
import com.example.groupcal.data.repositories.GroupRepository
import com.example.groupcal.models.Group

/**
 * ViewModel for [AddGroupFragment]
 *
 * @param repo The Repository for Groups
 */
class AddGroupViewModel(val repo: GroupRepository) : ViewModel() {

    var name: String = ""
    var color: String = ""

    /**
     * Add new group with properties filled in by user
     */
    fun addGroup(): Boolean {
        if (name == "Group Name" || color == "Pick a Color") {
            return false
        } else {
            val group = Group(
                name = name,
                color = color,
                members = mutableListOf()
            )
            repo.addGroup(group)
            return true
        }
    }
}