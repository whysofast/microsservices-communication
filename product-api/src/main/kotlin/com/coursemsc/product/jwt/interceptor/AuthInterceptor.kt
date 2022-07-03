package com.coursemsc.product.jwt.interceptor

import com.coursemsc.product.jwt.service.JwtService
import feign.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthInterceptor : HandlerInterceptor {

    @Autowired
    private lateinit var jwtService: JwtService

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        print("Requisição interceptada: " + request.getHeader("Authorization"))
        if (request.isOptions()) return true

        val authorization = request.getHeader("Authorization")

        jwtService.isAuthenticated(authorization)
        return true
    }

    fun HttpServletRequest.isOptions() = method == Request.HttpMethod.OPTIONS.name
}