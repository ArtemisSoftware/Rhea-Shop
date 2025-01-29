package com.artemissoftware.rheashop.data

import com.artemissoftware.rheashop.data.database.entities.RoleEntity
import com.artemissoftware.rheashop.data.database.entities.UserEntity
import com.artemissoftware.rheashop.repository.RoleRepository
import com.artemissoftware.rheashop.repository.UserRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
class DataInitializer(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) : ApplicationListener<ApplicationReadyEvent> {


    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        createDefaultRoleIfNotExits()
        createDefaultUserIfNotExits()
        createDefaultAdminIfNotExits()
    }

    private fun createDefaultUserIfNotExits() {
        val userRole = roleRepository.findByName(ROLE_USER)

        for (i in 1..5) {
            val defaultEmail = "user$i@email.com"
            if (userRepository.existsByEmail(defaultEmail)) {
                continue
            }
            userRole?.let {
                val user = UserEntity()
                user.firstName = "The User"
                user.lastName = "User$i"
                user.email = defaultEmail
                user.password = passwordEncoder.encode("123456")
                user.roles = hashSetOf(it)
                userRepository.save(user)
                println("Default user $i created successfully.")
            }
        }
    }

    private fun createDefaultRoleIfNotExits() {
        DEFAULT_ROLES
            .stream()
            .filter { role -> roleRepository.findByName(role) == null }
            .map { RoleEntity(it) }
            .forEach { entity -> roleRepository.save(entity) }
    }

    private fun createDefaultAdminIfNotExits() {
        val adminRole = roleRepository.findByName(ROLE_ADMIN)

        for (i in 1..2) {
            val defaultEmail = "admin$i@email.com"
            if (userRepository.existsByEmail(defaultEmail)) {
                continue
            }
            adminRole?.let {
                val user = UserEntity()
                user.firstName = "Admin"
                user.lastName = "Admin$i"
                user.email = defaultEmail
                user.password = passwordEncoder.encode("123456")
                user.roles = hashSetOf(it)
                userRepository.save<UserEntity>(user)
                println("Default admin user $i created successfully.")
            }

        }
    }

    private companion object {
        const val ROLE_ADMIN = "ROLE_ADMIN"
        const val ROLE_USER = "ROLE_USER"

        val DEFAULT_ROLES = setOf(ROLE_ADMIN, ROLE_USER)
    }
}