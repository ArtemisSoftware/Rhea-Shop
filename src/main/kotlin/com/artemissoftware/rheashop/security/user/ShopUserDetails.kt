package com.artemissoftware.rheashop.security.user

import com.artemissoftware.rheashop.data.database.entities.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors


class ShopUserDetails() : UserDetails {
    var id: Long = 0
    var email: String = ""
    var userPassword: String = ""
    var userAuthorities: Collection<GrantedAuthority> = emptyList()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return userAuthorities.toMutableList()
    }

    override fun getPassword(): String {
        return userPassword
    }

    override fun getUsername(): String {
        return email
    }

    constructor(
        email: String,
        password: String,
        authorities: Collection<GrantedAuthority>,
        id: Long
    ): this(){
        this.email = email
        this.userPassword = password
        this.userAuthorities = authorities
        this.id = id
    }

    companion object {
        fun buildUserDetails(user: UserEntity): ShopUserDetails {
            val authorities = user.roles
                .stream()
                .map { role -> SimpleGrantedAuthority(role.name) }
                .collect(Collectors.toList<GrantedAuthority>())

            return ShopUserDetails(
                id = user.id,
                email = user.email,
                password = user.password,
                authorities = authorities
            )
        }
    }
}