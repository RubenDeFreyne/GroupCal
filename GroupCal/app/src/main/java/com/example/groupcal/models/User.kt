package com.example.groupcal.models

data class User (
    val id: Long,
    val email: String,
    val username: String,
    val phone: String,
    val groups: MutableList<Group>
)