package com.coursemsc.product.config.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [AuthenticationException::class])
    fun handleAuthenticationException(ex: AuthenticationException, request: WebRequest): ResponseEntity<Any> {
        logger.warn("handleAuthenticationException - ${ex.message}")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.message)
    }
}