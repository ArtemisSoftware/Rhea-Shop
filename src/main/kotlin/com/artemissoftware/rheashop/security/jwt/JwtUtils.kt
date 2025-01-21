package com.artemissoftware.rheashop.security.jwt

import com.artemissoftware.rheashop.security.user.ShopUserDetails
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils {
    @Value("\${auth.token.jwtSecret}")
    private val jwtSecret: String? = null

    @Value("\${auth.token.expirationInMils}")
    private val expirationTime = 0

    fun generateTokenForUser(authentication: Authentication): String {
        val userPrincipal = authentication.principal as ShopUserDetails

        val roles = userPrincipal.authorities
            .stream()
            .map { obj -> obj.authority }
            .toList()

        return Jwts.builder()
            .setSubject(userPrincipal.email)
            .claim("id", userPrincipal.id)
            .claim("roles", roles)
            .setIssuedAt(Date())
            .setExpiration(Date((Date()).time + expirationTime))
            .signWith(key(), SignatureAlgorithm.HS256).compact()
    }

    private fun key() = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))

    fun getUsernameFromToken(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(key())
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
            return true
        } catch (e: ExpiredJwtException) {
            throw JwtException(e.message)
        } catch (e: UnsupportedJwtException) {
            throw JwtException(e.message)
        } catch (e: MalformedJwtException) {
            throw JwtException(e.message)
        } catch (e: SignatureException) {
            throw JwtException(e.message)
        } catch (e: IllegalArgumentException) {
            throw JwtException(e.message)
        }
    }
}