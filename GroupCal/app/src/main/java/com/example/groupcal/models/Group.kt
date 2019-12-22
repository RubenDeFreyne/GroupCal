package com.example.groupcal.models

data class Group(
    val id: Long,
    val name: String,
    val color: String?,
    val members: MutableList<User>,
    val events: MutableList<Event>
)