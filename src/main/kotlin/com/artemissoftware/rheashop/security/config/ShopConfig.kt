package com.artemissoftware.rheashop.security.config

import com.artemissoftware.rheashop.security.jwt.AuthTokenFilter
import com.artemissoftware.rheashop.security.jwt.JwtAuthEntryPoint
import com.artemissoftware.rheashop.security.user.ShopUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class ShopConfig(
    private val userDetailsService: ShopUserDetailsService,
    private val authEntryPoint: JwtAuthEntryPoint
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authTokenFilter(): AuthTokenFilter {
        return AuthTokenFilter()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .exceptionHandling { exception ->
                exception.authenticationEntryPoint(
                    authEntryPoint
                )
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(*SECURED_URLS.toTypedArray()).authenticated()
                    .anyRequest().permitAll()
            }
        http.authenticationProvider(daoAuthenticationProvider())
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    companion object {
        private val SECURED_URLS = listOf("/api/v1/carts/**", "/api/v1/cartItems/**")
    }
}