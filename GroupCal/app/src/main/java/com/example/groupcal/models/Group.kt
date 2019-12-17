package com.example.groupcal.models

data class Group(
    val id: Long,
    val name: String,
    val color: Int,
    val members: MutableList<User>
)