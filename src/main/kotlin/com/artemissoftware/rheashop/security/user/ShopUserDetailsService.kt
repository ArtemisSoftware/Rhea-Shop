package com.artemissoftware.rheashop.security.user

import com.artemissoftware.rheashop.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class ShopUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String?): UserDetails {
        email?.let {
            userRepository.findByEmail(it)?.let { user ->
                return ShopUserDetails.buildUserDetails(user)
            } ?: run {
                throw UsernameNotFoundException("User not found!")
            }
        } ?:run {
            throw UsernameNotFoundException("Email is not valid")
        }
    }
}