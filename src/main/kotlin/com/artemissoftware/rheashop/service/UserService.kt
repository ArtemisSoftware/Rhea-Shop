package com.artemissoftware.rheashop.service

import com.artemissoftware.rheashop.data.database.entities.UserEntity
import com.artemissoftware.rheashop.data.mapper.toDto
import com.artemissoftware.rheashop.data.network.dto.UserDto
import com.artemissoftware.rheashop.data.network.request.CreateUserRequest
import com.artemissoftware.rheashop.data.network.request.UserUpdateRequest
import com.artemissoftware.rheashop.exception.AlreadyExistsException
import com.artemissoftware.rheashop.exception.ResourceNotFoundException
import com.artemissoftware.rheashop.repository.UserRepository
import org.springframework.stereotype.Service


@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun getUserById(userId: Long): UserDto {
        val user = userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("User not found!") }

        return user.toDto()
    }

    fun createUser(request: CreateUserRequest): UserDto {

        if(userRepository.existsByEmail(request.email)){
            throw AlreadyExistsException("Oops!" + request.email + " already exists!")
        }
        else {
            val user = UserEntity()
            user.email = request.email
            user.password = "1234"//passwordEncoder.encode(request.password)
            user.firstName = request.firstName
            user.lastName = request.lastName
            return userRepository.save(user).toDto()
        }
    }

    fun updateUser(request: UserUpdateRequest, userId: Long): UserDto {
        return userRepository.findById(userId)
            .map {
                it.firstName = request.firstName
                it.lastName = request.lastName
                userRepository.save(it).toDto()
            }
            .orElseThrow { ResourceNotFoundException("User not found!") }
    }

    fun deleteUser(userId: Long) {
        userRepository.findById(userId)
            .ifPresentOrElse(
                { userRepository.delete(it) },
                { throw ResourceNotFoundException("User not found!") }
            )
    }

//    override fun getAuthenticatedUser(): User {
//        val authentication: Authentication = SecurityContextHolder.getContext().getAuthentication()
//        val email: String = authentication.getName()
//        return userRepository!!.findByEmail(email)
//    }
}