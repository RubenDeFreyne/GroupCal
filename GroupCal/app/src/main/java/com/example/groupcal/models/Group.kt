package com.example.groupcal.models

data class Group(
    val id: Long,
    val name: String,
    val color: String?,
    val members: MutableList<User>
){
    fun toDatabaseGroup(): com.example.groupcal.database.databaseModels.Group {
        return com.example.groupcal.database.databaseModels.Group(
            name = this.name,
            color = this.color!!,
            users = this.members
        )
    }
}