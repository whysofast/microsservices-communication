package com.coursemsc.product.jwt.service

import com.coursemsc.product.config.exception.AuthenticationException
import com.coursemsc.product.jwt.dto.toJwtResponseDTO
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils.isEmpty

@Component
class JwtService(
    @Value("\${app-config.secrets.api-secret}") val apiSecret: String
) {

    fun isAuthenticated(token: String?) {
        val accessToken = token?.extractBearer() ?: throw AuthenticationException("authorization must not be null")

        try {
            val claims = Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(apiSecret.toByteArray()))
                .build()
                .parseClaimsJws(accessToken)
                .body

            val user = claims.toJwtResponseDTO()

            if (isEmpty(user) || isEmpty(user.id)) throw AuthenticationException("user not valid")
        } catch (e: MalformedJwtException) {
            throw AuthenticationException("invalid token format bearer")
        } catch (e: Exception) {
            e.printStackTrace()
            throw AuthenticationException("Error while trying to process the Access Token")
        }
    }

    private fun String.extractBearer(): String {
        if (this.isEmpty()) {
            throw AuthenticationException("token must not be empty")
        }

        if (this.lowercase().contains("bearer "))
            return this.replace("bearer ", "")

        throw AuthenticationException("invalid token format")
    }
}