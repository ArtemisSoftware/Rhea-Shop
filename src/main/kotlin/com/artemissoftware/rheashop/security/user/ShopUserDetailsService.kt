package com.artemissoftware.rheashop.security.user

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


//@Service
//class ShopUserDetailsService : UserDetailsService {
//    private val userRepository: UserRepository? = null
//
//    @Throws(UsernameNotFoundException::class)
//    override fun loadUserByUsername(email: String?): UserDetails {
//        val user: User = Optional.ofNullable(userRepository.findByEmail(email))
//            .orElseThrow { UsernameNotFoundException("User not found!") }
//        return ShopUserDetails.buildUserDetails(user)
//    }
//}