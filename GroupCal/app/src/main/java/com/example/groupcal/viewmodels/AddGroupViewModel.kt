package com.example.groupcal.viewmodels

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.groupcal.data.GroupRepository
import com.example.groupcal.models.Group

class AddGroupViewModel(val repo : GroupRepository) : ViewModel() {

    var name : String = ""
    var color : String = ""

    fun addGroup() : Boolean{

        if(name == "Group Name" || color == "Pick a Color"){
            return false
        }else {
            val group = Group(
                id = 0L,
                name = name,
                color = color,
                members = mutableListOf()
            )
            repo.addGroup(group)
            return true
        }


    }
}