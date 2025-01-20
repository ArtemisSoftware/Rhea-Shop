package com.artemissoftware.rheashop.data.network.request

data class CreateUserRequest (
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String
)
