package com.coursemsc.product.config.interceptor

import com.coursemsc.product.config.exception.ValidationException
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

class FeignClientAuthInterceptor : RequestInterceptor {
    override fun apply(template: RequestTemplate?) {
        template!!.header("Authorization",getCurrentRequest().getHeader("Authorization"))
    }

    private fun getCurrentRequest() : HttpServletRequest {
        try {
            return (RequestContextHolder::class as ServletRequestAttributes).request
        }catch (e: Exception) {
            e.printStackTrace()
            throw ValidationException("The current request could not be processed")
        }
    }
}