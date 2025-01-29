package com.artemissoftware.rheashop.controller
import com.artemissoftware.rheashop.data.network.request.LoginRequest
import com.artemissoftware.rheashop.data.network.response.ApiResponse
import com.artemissoftware.rheashop.data.network.response.JwtResponse
import com.artemissoftware.rheashop.security.jwt.JwtUtils
import com.artemissoftware.rheashop.security.user.ShopUserDetails
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${api.prefix}/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<ApiResponse> {
        try {
            val authentication = authenticationManager
                .authenticate(
                    UsernamePasswordAuthenticationToken(
                        request.email,
                        request.password
                    )
                )
            SecurityContextHolder.getContext().authentication = authentication
            val jwt = jwtUtils.generateTokenForUser(authentication)
            val userDetails = authentication.principal as ShopUserDetails
            val jwtResponse = JwtResponse(userDetails.id, jwt)

            return ResponseEntity
                .ok(ApiResponse("Login Successful", jwtResponse))
        } catch (e: AuthenticationException) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse("Login failed", e.message)
            )
        }
    }
}