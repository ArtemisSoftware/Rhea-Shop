package com.artemissoftware.rheashop.controller

import com.artemissoftware.rheashop.data.network.request.CreateUserRequest
import com.artemissoftware.rheashop.data.network.request.UserUpdateRequest
import com.artemissoftware.rheashop.data.network.response.ApiResponse
import com.artemissoftware.rheashop.exception.AlreadyExistsException
import com.artemissoftware.rheashop.exception.ResourceNotFoundException
import com.artemissoftware.rheashop.service.UserService
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.prefix}/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{userId}/user")
    fun getUserById(@PathVariable userId: Long): ResponseEntity<ApiResponse> {
        try {
            val user = userService.getUserById(userId)
            return ResponseEntity
                .ok(ApiResponse("Success", user))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Unable to get user with id $userId", e.message))
        }
    }

    @PostMapping("/add")
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<ApiResponse> {
        try {
            val user = userService.createUser(request)
            return ResponseEntity
                .ok(ApiResponse("Create User Success!", user))
        } catch (e: AlreadyExistsException) {
            return ResponseEntity
                .status(CONFLICT)
                .body(ApiResponse("Unable to create user", e.message))
        }
    }

    @PutMapping("/{userId}/update")
    fun updateUser(@RequestBody request: UserUpdateRequest, @PathVariable userId: Long): ResponseEntity<ApiResponse> {
        try {
            val user = userService.updateUser(request, userId)
            return ResponseEntity
                .ok(ApiResponse("Update User Success!", user))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Unable to update user data with id $userId", e.message))
        }
    }

    @DeleteMapping("/{userId}/delete")
    fun deleteUser(@PathVariable userId: Long): ResponseEntity<ApiResponse> {
        try {
            userService.deleteUser(userId)
            return ResponseEntity
                .ok(ApiResponse("Delete User Success!"))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Unable to delete user with id $userId",e.message))
        }
    }
}