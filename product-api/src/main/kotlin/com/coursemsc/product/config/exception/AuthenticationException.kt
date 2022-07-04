package com.coursemsc.product.config.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class AuthenticationException(message: String) : RuntimeException(message)

class ValidationException(message: String) : RuntimeException(message)
