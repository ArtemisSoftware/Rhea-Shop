package com.artemissoftware.rheashop.data.network.request

data class CreateUserRequest (
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
