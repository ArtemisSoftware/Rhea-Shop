package com.artemissoftware.rheashop.security.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors


//class ShopUserDetails : UserDetails {
//    var id: Long = 0
//    var email: String = ""
//    var password: String = ""
//
//    private val authorities: Collection<GrantedAuthority>? = null
//
//    override fun getAuthorities(): Collection<GrantedAuthority> {
//        return authorities!!
//    }
//
//
//    override fun isAccountNonExpired(): Boolean {
//        return super.isAccountNonExpired()
//    }
//
//    override fun isAccountNonLocked(): Boolean {
//        return super.isAccountNonLocked()
//    }
//
//    override fun isCredentialsNonExpired(): Boolean {
//        return super.isCredentialsNonExpired()
//    }
//
//    override fun isEnabled(): Boolean {
//        return super.isEnabled()
//    }
//
//    companion object {
//        fun buildUserDetails(user: User): ShopUserDetails {
//            val authorities: List<GrantedAuthority> = user.getRoles()
//                .stream()
//                .map { role -> SimpleGrantedAuthority(role.getName()) }
//                .collect(Collectors.toList<T>())
//
//            return ShopUserDetails(
//                user.getId(),
//                user.getEmail(),
//                user.getPassword(),
//                authorities
//            )
//        }
//    }
//}